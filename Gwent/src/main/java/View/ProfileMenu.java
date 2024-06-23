package View;

import Controller.ApplicationController;
import Controller.ProfileController;
import Model.GameHistory;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;

public class ProfileMenu extends Application {
    public static Pane root;
    @FXML
    private ScrollPane ScrollOfHistory;
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
    contentsOfProfileMenu();
    setHistoryContents();
    }

    private void setHistoryContents() {
        TilePane collectionContent = new TilePane(5, 5);
        collectionContent.setPrefWidth(418);
        collectionContent.setMinHeight(600);
        TableView<GameHistory> tableView = new TableView<>();

        TableColumn<GameHistory, String> nameOpponent = new TableColumn<>("Opponent");
        nameOpponent.setCellValueFactory(new PropertyValueFactory<>("opponentName"));

        TableColumn<GameHistory, Date> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<GameHistory, Double> round1Me = new TableColumn<>("R1 Me");
        round1Me.setCellValueFactory(new PropertyValueFactory<>("firstRoundPointMe"));

        TableColumn<GameHistory, Double> round1Opp = new TableColumn<>("R1 Opp");
        round1Opp.setCellValueFactory(new PropertyValueFactory<>("firstRoundPointOpponent"));

        TableColumn<GameHistory, Double> round2Me = new TableColumn<>("R2 Me");
        round2Me.setCellValueFactory(new PropertyValueFactory<>("secondRoundPointMe"));

        TableColumn<GameHistory, Double> round2Opp = new TableColumn<>("R2 Opp");
        round2Opp.setCellValueFactory(new PropertyValueFactory<>("secondRoundPointOpponent"));

        TableColumn<GameHistory, Double> round3Me = new TableColumn<>("R3 Me");
        round3Me.setCellValueFactory(new PropertyValueFactory<>("thirdRoundPointMe"));

        TableColumn<GameHistory, Double> round3Opp = new TableColumn<>("R3 Opp");
        round3Opp.setCellValueFactory(new PropertyValueFactory<>("thirdRoundPointOpponent"));

        TableColumn<GameHistory, Double> totalMe = new TableColumn<>("Total Me");
        totalMe.setCellValueFactory(new PropertyValueFactory<>("totalPointsMe"));

        TableColumn<GameHistory, Double> totalOpp = new TableColumn<>("Total Opp");
        totalOpp.setCellValueFactory(new PropertyValueFactory<>("totalPointsOpponent"));

        TableColumn<GameHistory, String> winner = new TableColumn<>("Winner");
        winner.setCellValueFactory(new PropertyValueFactory<>("winner"));

        for (GameHistory gameHistory: GameHistory.getGameHistories()){
            tableView.setItems();
        }
        collectionContent.getChildren().add(tableView);

    }

    private void contentsOfProfileMenu() {
        username.setText(User.getLoggedUser().getUsername());
        password.setText(User.getLoggedUser().getPassword());
        email.setText(User.getLoggedUser().getEmail());
        nickname.setText(User.getLoggedUser().getNickName());
        //Todo what is top score?
//        topScoreLabel.setText();

        totalGamesLabel.setText(totalGamesLabel.getText() + User.getLoggedUser().getNumberOfGames());
        rankLabel.setText(rankLabel.getText() + ProfileController.rankCounter());
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
    }

    private void setHeightAndWidth(Stage stage, double height, double width) {
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth() + 500) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight() - 300) / 2);
        stage.setHeight(height);
        stage.setWidth(width);
    }


    public void saveChanges(MouseEvent mouseEvent) {
        ProfileController.saveChanges(username, password, email, nickname);
    }

    public void BackToMainMenu(MouseEvent mouseEvent) {
        try {
            new MainMenu().start(ApplicationController.getStage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
