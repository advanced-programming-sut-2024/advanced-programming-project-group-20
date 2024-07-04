package View;

import Model.GameHistory;
import Model.User;
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

public class ProfileMenu extends Application {
    public static Pane root;
    @FXML
    public Button buttonFriend;
    public VBox friendRequest;
    public ScrollPane scrollOfFriends;
    public HBox sendRequestHbox;
    public AnchorPane pane;
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

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void initialize() {

//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        root = pane;
        setRankOfUsers();
        contentsOfProfileMenu();
        setHistoryContents();
        setTablePointsContent();
        setFriendsTable();
    }

    private void setFriendsTable() {
        Client.getConnection().doInServer("ProfileController", "updateRequests", new Object());
        Client.getConnection().doInServer("RegisterController", "parseFile", new ArrayList<Object>());
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


        // todo handel friends
        for (User user : User.getAllUsers()) {
            System.out.println(user.getUsername());
        }
        System.out.println("his name" + User.getLoggedUser().getUsername());
        for (String s : User.getLoggedUser().getFriends())
            tableView.getItems().add(User.getUserByName(s));
        System.out.println("his name" + User.getLoggedUser().getFriends().size());


        collectionContent.getChildren().add(tableView);
        scrollOfFriends.setContent(collectionContent);
        sendRequestHbox.getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            Client.getConnection().doInServer("ProfileController", "sendRequest", ((TextField) sendRequestHbox.getChildren().get(0)).getText());
        });

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

        for (User user : User.getAllUsers()) {
            tableView.getItems().add(user);
        }
        collectionContent.getChildren().add(tableView);
        scrollOfPointsTable.setContent(collectionContent);
    }

    private void setHistoryContents() {

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
        collectionContent.getChildren().add(tableView);
        scrollOfHistory.setContent(collectionContent);
    }

    private void contentsOfProfileMenu() {
        username.setText(User.getLoggedUser().getUsername());
        password.setText(User.getLoggedUser().getPassword());
        email.setText(User.getLoggedUser().getEmail());
        nickname.setText(User.getLoggedUser().getNickName());

        double topTotalScore = 0;
        for (GameHistory gameHistory : User.getLoggedUser().getGameHistories()) {
            if (gameHistory.getTotalPointsMe() > topTotalScore) {
                topTotalScore = gameHistory.getTotalPointsMe();
            }
        }
        topScoreLabel.setText(topScoreLabel.getText() + topTotalScore);
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
        objects.add(username.getText());
        objects.add(password.getText());
        objects.add(email.getText());
        objects.add(nickname.getText());
//        objects.add(User.getLoggedUser());
        Client.getConnection().doInServer("ProfileController", "changeInformation", objects.toArray());
    }

    public void BackToMainMenu(MouseEvent mouseEvent) {
        try {
            new MainMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeInformationInClientModel(ArrayList<Object> objects) {
        System.out.println("bia");
        User.getLoggedUser().setUsername((String) objects.get(0));
        User.getLoggedUser().setPassword((String) objects.get(1));
        User.getLoggedUser().setEmail((String) objects.get(2));
        User.getLoggedUser().setNickName((String) objects.get(3));
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
        for (Node node : informationPane.getChildren()) {
            node.setVisible(true);
        }
    }

    public void friendButtonClick(ActionEvent actionEvent) {
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
        for (Node node : informationPane.getChildren()) {
            node.setVisible(false);
        }
    }



    public static void setRequest(ArrayList<Object> objects) {
        String friendName = (String) objects.get(1);
        Platform.runLater(() -> {
            Button noButton;
            Button yesButton;
            HBox hBox;

            for (Node node : root.getChildren()) {
                // todo no hardcode
                if (node.getId() != null && node.getId().equals("request")) {
                    ((VBox) node).getChildren().add(hBox = new HBox(new Label("Friend request from " + friendName),
                            yesButton = new Button("ok"), noButton = new Button("no")));
                    hBox.setId(friendName);
                    noButton.setOnMouseClicked(event -> ((VBox) node).getChildren().removeIf(node1 -> node1.getId().equals(friendName)));
                    yesButton.setOnMouseClicked(mouseEvent -> {
                        ((VBox) node).getChildren().removeIf(node1 -> node1.getId().equals(friendName));
                        if (User.getLoggedUser().getFriends() == null)
                            User.getLoggedUser().setFriends(new ArrayList<>());
                        if (!User.getLoggedUser().getFriends().contains(friendName))
                            User.getLoggedUser().getFriends().add(friendName);
                        System.out.println("dost add shode " + friendName);
                        System.out.println("User in client" + User.getLoggedUser().getUsername());
                        Client.getConnection().doInServer("ProfileController", "beFriend", friendName, User.getLoggedUser().getUsername());

                    });
                }
            }
        });

    }

    public static void updateRequestInMenu(ArrayList<Object> objects) {
        System.out.println("sefre?" + objects.size());
        for (Object object : objects) {
            System.out.println(object);
            ArrayList<Object> objectArrayList = new ArrayList<>();
            objectArrayList.add(new Object());
            objectArrayList.add(object);
            setRequest(objectArrayList);
        }

    }
}
