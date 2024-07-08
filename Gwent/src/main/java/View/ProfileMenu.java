package View;

import Model.*;
import com.google.gson.Gson;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import webConnection.Client;
import webConnection.Connection;

import java.net.URL;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProfileMenu extends Application {
    public static Pane root;
    @FXML
    public Button buttonFriend;
    public VBox friendRequest;
    public ScrollPane scrollOfFriends;
    public HBox sendRequestHbox;
    public AnchorPane pane;
    public ScrollPane scrollOfRequests;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Pane informationPane;
    @FXML
    private ScrollPane scrollOfPointsTable;
    @FXML
    private ScrollPane scrollOfHistory;
    @FXML
    private Label topScoreLabel;
    @FXML
    private Label totalGamesLabel;
    @FXML
    private Label rankLabel;
    @FXML
    private Label winLabel;
    @FXML
    private Label drawLabel;
    @FXML
    private Label loseLabel;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private TextField nickname;
    private static ProfileMenu profileMenu;

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void initialize() {
        root = pane;
        setRankOfUsers();
        contentsOfProfileMenu();
        setTablePointsContent();
        setFriendsTable();
        profileMenu = this;
    }

    public static void setGameHistories(ArrayList<Object> objects) {
        Gson gson = new Gson();
        User.getLoggedUser().setGameHistories(new ArrayList<>());
        GameHistory gameHistory;
        for (Object object : objects) {
            gameHistory = gson.fromJson(gson.toJson(object), GameHistory.class);
            System.out.println(gameHistory.getWinner());
            User.getLoggedUser().getGameHistories().add(gameHistory);
        }
        for (GameHistory gameHistory1 : User.getLoggedUser().getGameHistories()) {
            System.out.println(gameHistory1.getWinner());
            System.out.println(gameHistory1.getLeaderName());
        }
        Platform.runLater(() -> {
            profileMenu.setHistoryContents();
        });
    }

    private void setFriendsTable() {
        Client.getConnection().doInServer("ApplicationController", "deliverUsersOfServerToClient", new Object());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        TilePane collectionContent = new TilePane(2, 3);
        collectionContent.setPrefWidth(200);
        collectionContent.setMinHeight(300);
        TableView<User> tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: transparent");
        tableView.setPrefWidth(500);
        tableView.setPrefHeight(600);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<User, String> rank = new TableColumn<>("Name");
        rank.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableView.getColumns().add(rank);

        TableColumn<User, Date> name = new TableColumn<>("Last seen");
        name.setCellValueFactory(new PropertyValueFactory<>("lastSeen"));
        tableView.getColumns().add(name);
        Gson gson = new Gson();
        for (User user : User.getAllUsers())
            System.out.println(gson.toJson(user));
        System.out.println("his name" + User.getLoggedUser().getUsername());
        for (String s : User.getLoggedUser().getFriends())
            tableView.getItems().add(User.getUserByName(s));
        System.out.println("his name" + User.getLoggedUser().getFriends().size());
        ArrayList<Object> objects = new ArrayList<>();
        for (String requesterName : User.getLoggedUser().getFriendRequests()) {
            System.out.println("requ hast");
            objects.add(new Object());
            objects.add(requesterName);
            setRequest(objects);
        }
        tableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                User user = tableView.getSelectionModel().getSelectedItem();
                if (user != null && user.getNumberOfGames() > 0) {
                    Client.getConnection().doInServer("MainController", "playWithFriend",
                            User.getLoggedUser().getUsername(), user.getUsername(),true);
                }
            }
        });
        collectionContent.getChildren().add(tableView);
        scrollOfFriends.setContent(collectionContent);

        sendRequestHbox.getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            if (User.getUserByName(((TextField) sendRequestHbox.getChildren().get(0)).getText()) == null)
                ApplicationController.alert("this user doesnt exist", "erorre!!");
            else {
                showFriendInfo(((TextField) sendRequestHbox.getChildren().get(0)).getText());
                ((TextField) sendRequestHbox.getChildren().get(0)).setText("");
            }
        });

        for (Request request : User.getLoggedUser().getRequests()) {
            if (User.getLoggedUser().getFriends().contains(request.getFriendName()) && !request.getResult().equals("rejected"))
                request.setResult("accepted");
            if (!User.getUserByName(request.getFriendName()).getFriendRequests().contains(User.getLoggedUser().getUsername()) && !request.getResult().equals("accepted"))
                request.setResult("rejected");
        }
            TilePane collectionContentRequest = new TilePane(2, 3);
            collectionContentRequest.setPrefWidth(200);
            collectionContentRequest.setMinHeight(300);
            TableView<Request> tableViewRequest = new TableView<>();
            tableViewRequest.setStyle("-fx-background-color: transparent");
            tableViewRequest.setPrefWidth(500);
            tableViewRequest.setPrefHeight(600);
            tableViewRequest.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn<Request, String> requestTo = new TableColumn<>("request to");
            requestTo.setCellValueFactory(new PropertyValueFactory<>("friendName"));
            tableViewRequest.getColumns().add(requestTo);
            TableColumn<Request, String> result = new TableColumn<>("result");
            result.setCellValueFactory(new PropertyValueFactory<>("result"));
            tableViewRequest.getColumns().add(result);

            collectionContentRequest.getChildren().add(tableViewRequest);
            scrollOfRequests.setContent(collectionContentRequest);
   for (Request request :User.getLoggedUser().getRequests())
       tableViewRequest.getItems().add(request);

    }

    private void showFriendInfo(String text) {

        User user = User.getUserByName(text);
        Label label = new Label();
        label.setText(user.getUsername() + "\nEmail: " + user.getEmail() + "\nWins Number:" + user.getNumberOfWins());
        Button button = new Button("send");
        button.setOnMouseClicked(mouseEvent -> {
            Client.getConnection().doInServer("ProfileController", "sendRequest", text);
            ((TextField) sendRequestHbox.getChildren().get(0)).setText("");
            friendRequest.getChildren().remove(1);
        });

        HBox sendHbox = new HBox(label, button);
        friendRequest.getChildren().add(sendHbox);
    }

    private void setRankOfUsers() {
        User.getAllUsers().sort(Comparator.comparing(User::getNumberOfWins).reversed());
        for (int i = 0; i < User.getAllUsers().size(); i++) {
            User.getAllUsers().get(i).setRank(i + 1);
        }
    }

    private void setTablePointsContent() {
//        User.getAllUsers().sort(Comparator.comparing(User::getNumberOfWins).reversed());

        TilePane collectionContent = new TilePane(5, 5);
        collectionContent.setPrefWidth(500);
        collectionContent.setMinHeight(600);
        TableView<User> tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: transparent");
        tableView.setPrefWidth(500);
        tableView.setPrefHeight(600);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<User, String> rank = new TableColumn<>("rank");
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableView.getColumns().add(rank);

        TableColumn<User, Date> name = new TableColumn<>("name");
        name.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableView.getColumns().add(name);

        TableColumn<User, Double> numberOfWins = new TableColumn<>("number of wins");
        numberOfWins.setCellValueFactory(new PropertyValueFactory<>("numberOfWins"));
        tableView.getColumns().add(numberOfWins);

        TableColumn<User, Date> lastSeen = new TableColumn<>("Last seen");
        lastSeen.setCellValueFactory(new PropertyValueFactory<>("lastSeen"));
        tableView.getColumns().add(lastSeen);

        for (User user : User.getAllUsers()) {
            tableView.getItems().add(user);
        }
        tableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                User user = tableView.getSelectionModel().getSelectedItem();
                if (user != null && user.getNumberOfGames() > 0) {
                    Client.getConnection().doInServer("ProfileController", "showLastGame", user.getUsername());
                }
            }
        });
        collectionContent.getChildren().add(tableView);
        scrollOfPointsTable.setContent(collectionContent);
    }

    public static void showLastGame(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Gson gson = new Gson();
            User user = User.getUserByName((String) objects.get(0));
            GameHistory gameHistory = gson.fromJson(gson.toJson(objects.get(1)), GameHistory.class);
            GameHistoryShower gameHistoryShower = new GameHistoryShower();
            GameHistoryShower.setGameUser(user);
            GameHistoryShower.setGameHistory(gameHistory);
            user.setOpponentUser(User.getUserByName(gameHistory.getOpponentName()));
            user.readyForGame();
            user.getOpponentUser().readyForGame();
            user.setFaction(Faction.giveFactionByName(gameHistory.getFactionName()));
            user.setLeader(Leader.giveLeaderByNameAndFaction(gameHistory.getLeaderName(), user.getFaction()));
            user.getOpponentUser().setFaction(Faction.giveFactionByName(gameHistory.getOppFactionName()));
            user.getOpponentUser().setLeader(Leader.giveLeaderByNameAndFaction(gameHistory.getOppLeaderName(),
                    user.getOpponentUser().getFaction()));
            user.getOpponentUser().setOpponentUser(user);
            try {
                gameHistoryShower.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setHistoryContents() {
        if (User.getLoggedUser().getGameHistories() == null) {
            User.getLoggedUser().setGameHistories(new ArrayList<>());
        }
        double topTotalScore = 0;
        for (GameHistory gameHistory : User.getLoggedUser().getGameHistories()) {
            if (gameHistory.getTotalPointsMe() > topTotalScore) {
                topTotalScore = gameHistory.getTotalPointsMe();
            }
        }
        topScoreLabel.setText(topScoreLabel.getText() + topTotalScore);
        TilePane collectionContent = new TilePane(5, 5);
        collectionContent.setPrefWidth(1230);
        collectionContent.setMinHeight(600);
        TableView<GameHistory> tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: transparent");
        tableView.setPrefWidth(1230);
        tableView.setPrefHeight(600);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<GameHistory, String> nameOpponent = new TableColumn<>("Opponent");
        nameOpponent.setCellValueFactory(new PropertyValueFactory<>("opponentName"));
        tableView.getColumns().add(nameOpponent);

        TableColumn<GameHistory, Date> date = new TableColumn<>("Date");
        date.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1)); // 30%

        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.getColumns().add(date);

        TableColumn<GameHistory, Double> round1Me = new TableColumn<>("R1 Me");
        round1Me.setCellValueFactory(new PropertyValueFactory<>("firstRoundPointMe"));
        tableView.getColumns().add(round1Me);

        TableColumn<GameHistory, Double> round1Opp = new TableColumn<>("R1 Opp");
        round1Opp.setCellValueFactory(new PropertyValueFactory<>("firstRoundPointOpponent"));
        tableView.getColumns().add(round1Opp);

        TableColumn<GameHistory, Double> round2Me = new TableColumn<>("R2 Me");
        round2Me.setCellValueFactory(new PropertyValueFactory<>("secondRoundPointMe"));
        tableView.getColumns().add(round2Me);

        TableColumn<GameHistory, Double> round2Opp = new TableColumn<>("R2 Opp");
        round2Opp.setCellValueFactory(new PropertyValueFactory<>("secondRoundPointOpponent"));
        tableView.getColumns().add(round2Opp);

        TableColumn<GameHistory, Double> round3Me = new TableColumn<>("R3 Me");
        round3Me.setCellValueFactory(new PropertyValueFactory<>("thirdRoundPointMe"));
        tableView.getColumns().add(round3Me);

        TableColumn<GameHistory, Double> round3Opp = new TableColumn<>("R3 Opp");
        round3Opp.setCellValueFactory(new PropertyValueFactory<>("thirdRoundPointOpponent"));
        tableView.getColumns().add(round3Opp);

        TableColumn<GameHistory, Double> totalMe = new TableColumn<>("Total Me");
        totalMe.setCellValueFactory(new PropertyValueFactory<>("totalPointsMe"));
        tableView.getColumns().add(totalMe);

        TableColumn<GameHistory, Double> totalOpp = new TableColumn<>("Total Opp");
        totalOpp.setCellValueFactory(new PropertyValueFactory<>("totalPointsOpponent"));
        tableView.getColumns().add(totalOpp);

        TableColumn<GameHistory, String> winner = new TableColumn<>("Winner");
        winner.setCellValueFactory(new PropertyValueFactory<>("winner"));
        tableView.getColumns().add(winner);
        for (GameHistory gameHistory : User.getLoggedUser().getGameHistories()) {
            tableView.getItems().add(gameHistory);
        }
        tableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                GameHistory gameHistory = tableView.getSelectionModel().getSelectedItem();
                if (gameHistory != null) {
                    GameHistoryShower gameHistoryShower = new GameHistoryShower();
                    GameHistoryShower.setGameUser(User.getLoggedUser());
                    GameHistoryShower.setGameHistory(gameHistory);
                    User.getLoggedUser().setOpponentUser(User.getUserByName(gameHistory.getOpponentName()));
                    User.getLoggedUser().readyForGame();
                    User.getLoggedUser().getOpponentUser().readyForGame();
                    User.getLoggedUser().setFaction(Faction.giveFactionByName(gameHistory.getFactionName()));
                    User.getLoggedUser().setLeader(Leader.giveLeaderByNameAndFaction(gameHistory.getLeaderName(), User.getLoggedUser().getFaction()));
                    User.getLoggedUser().getOpponentUser().setFaction(Faction.giveFactionByName(gameHistory.getOppFactionName()));
                    User.getLoggedUser().getOpponentUser().setLeader(Leader.giveLeaderByNameAndFaction(gameHistory.getOppLeaderName(),
                            User.getLoggedUser().getOpponentUser().getFaction()));
                    User.getLoggedUser().getOpponentUser().setOpponentUser(User.getLoggedUser());
                    try {
                        gameHistoryShower.start(ApplicationController.getStage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        collectionContent.getChildren().add(tableView);
        scrollOfHistory.setContent(collectionContent);
    }

    private void contentsOfProfileMenu() {
        username.setText(User.getLoggedUser().getUsername());
        password.setText(User.getLoggedUser().getPassword());
        email.setText(User.getLoggedUser().getEmail());
        nickname.setText(User.getLoggedUser().getNickName());
        totalGamesLabel.setText(totalGamesLabel.getText() + User.getLoggedUser().getNumberOfGames());
        rankLabel.setText(rankLabel.getText() + User.getLoggedUser().getRank());
        winLabel.setText(winLabel.getText() + User.getLoggedUser().getNumberOfWins());
        drawLabel.setText(drawLabel.getText() + User.getLoggedUser().getNumberOfDraws());
        loseLabel.setText(loseLabel.getText() + User.getLoggedUser().getNumberOfLose());
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = RegisterMenu.class.getResource("/FXML/ProfileMenu.fxml");
        root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(720);
        stage.setWidth(1280);
        root.setBackground(new Background(ApplicationController.createBackGroundImage("/backgrounds/Gwent_1.jpg"
                , stage.getHeight(), stage.getWidth())));
        stage.show();
        Rectangle blackOverview = new Rectangle(ApplicationController.getStage().getWidth()
                , ApplicationController.getStage().getWidth(), Color.rgb(37, 40, 37, 0.88));
        root.getChildren().add(blackOverview);
        blackOverview.toBack();
    }

    private void setHeightAndWidth(Stage stage, double height, double width) {
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth() + 500) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight() - 300) / 2);
        stage.setHeight(height);
        stage.setWidth(width);
    }

    public void saveChangesInServer(MouseEvent mouseEvent) {
        ArrayList<Object> objects = new ArrayList<>();

        objects.add(User.getLoggedUser().getUsername());
        objects.add(username.getText());
        objects.add(password.getText());
        objects.add(email.getText());
        objects.add(nickname.getText());

        Client.getConnection().doInServer("ProfileController"
                , "changeInformationUsingButtonSaveChanges", objects.toArray());
    }

    public void BackToMainMenu(MouseEvent mouseEvent) {
        try {
            new MainMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeInformationInClientModel(ArrayList<Object> objects) {
        User.getLoggedUser().setUsername((String) objects.get(1));
        User.getLoggedUser().setPassword((String) objects.get(2));
        User.getLoggedUser().setEmail((String) objects.get(3));
        User.getLoggedUser().setNickName((String) objects.get(4));
        confirmAlert();
    }

    private static void confirmAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("your information changed successfully");
            alert.show();
        });
    }

    public void button1Clicked(ActionEvent actionEvent) {
        setTablePointsContent();
        button1.setScaleX(1.2);
        button1.setScaleY(1.2);
        button2.setScaleX(1.0);
        button2.setScaleY(1.0);
        button3.setScaleX(1.0);
        button3.setScaleY(1.0);
        buttonFriend.setScaleX(1);
        buttonFriend.setScaleY(1);
        scrollOfPointsTable.setVisible(true);
        scrollOfHistory.setVisible(false);
        friendRequest.setVisible(false);
        scrollOfRequests.setVisible(false);
        scrollOfFriends.setVisible(false);
        for (Node node : informationPane.getChildren()) {
            node.setVisible(false);
        }
    }

    public void button2Clicked(ActionEvent actionEvent) {
        button1.setScaleX(1.0);
        button1.setScaleY(1.0);
        button2.setScaleX(1.2);
        button2.setScaleY(1.2);
        button3.setScaleX(1.0);
        button3.setScaleY(1.0);
        buttonFriend.setScaleX(1);
        buttonFriend.setScaleY(1);
        scrollOfPointsTable.setVisible(false);
        scrollOfFriends.setVisible(false);
        friendRequest.setVisible(false);
        scrollOfRequests.setVisible(false);
        scrollOfHistory.setVisible(true);
        for (Node node : informationPane.getChildren())
            node.setVisible(false);
    }

    public void button3Clicked(ActionEvent actionEvent) {
        button1.setScaleX(1.0);
        button1.setScaleY(1.0);
        button2.setScaleX(1.0);
        button2.setScaleY(1.0);
        button3.setScaleX(1.2);
        button3.setScaleY(1.2);
        buttonFriend.setScaleX(1);
        buttonFriend.setScaleY(1);
        scrollOfPointsTable.setVisible(false);
        scrollOfHistory.setVisible(false);
        friendRequest.setVisible(false);
        scrollOfFriends.setVisible(false);
        scrollOfRequests.setVisible(false);
        for (Node node : informationPane.getChildren()) {
            node.setVisible(true);
        }
    }

    public void friendButtonClick(ActionEvent actionEvent) {
        setFriendsTable();
        button1.setScaleX(1);
        button1.setScaleY(1);
        button2.setScaleX(1.0);
        button2.setScaleY(1.0);
        button3.setScaleX(1.0);
        button3.setScaleY(1.0);
        buttonFriend.setScaleX(1.2);
        buttonFriend.setScaleY(1.2);
        scrollOfPointsTable.setVisible(false);
        friendRequest.setVisible(true);
        scrollOfHistory.setVisible(false);
        scrollOfFriends.setVisible(true);
        scrollOfRequests.setVisible(true);
        for (Node node : informationPane.getChildren()) {
            node.setVisible(false);
        }
    }

    public static void setRequest(ArrayList<Object> objects) {
        String friendName = (String) objects.get(1);
        if (root == null) {
            System.out.println("root nulle");
            return;
        }
        Platform.runLater(() -> {
            Button noButton;
            Button yesButton;
            HBox hBox;

            for (Node node : root.getChildren()) {
                // todo no hardcode
                if (node.getId() != null && node.getId().equals("request")) {
                    for (Node node1 : ((VBox) node).getChildren()) {
                        if (node1.getId() != null && node1.getId().equals(friendName))
                            return;
                    }

                    ((VBox) node).getChildren().add(hBox = new HBox(new Label("Friend request from " + friendName),
                            yesButton = new Button("ok"), noButton = new Button("no")));
                    hBox.setId(friendName);
                    noButton.setOnMouseClicked(event -> {
                        ((VBox) node).getChildren().removeIf(node1 -> node1.getId().equals(friendName));
                        Client.getConnection().doInServer("ProfileController", "updateRequests", friendName);

                    });
                    yesButton.setOnMouseClicked(mouseEvent -> {
                        ((VBox) node).getChildren().removeIf(node1 -> node1.getId().equals(friendName));
                        System.out.println("dost add shode " + friendName);
                        System.out.println("User in client" + User.getLoggedUser().getUsername());
                        Client.getConnection().doInServer("ProfileController", "beFriend", friendName, User.getLoggedUser().getUsername());
                    });
                }
            }
        });

    }

    public void updateGameHistories(MouseEvent mouseEvent) {
        Client.getConnection().doInServer("ProfileController","getGameHistories",User.getLoggedUser().getUsername());
    }
}


