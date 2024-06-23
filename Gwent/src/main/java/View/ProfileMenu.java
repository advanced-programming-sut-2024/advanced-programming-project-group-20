package View;

import Controller.ApplicationController;
import Controller.ProfileController;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class ProfileMenu extends Application {
    public static Pane root;
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
        Rectangle whiteOverlay = new Rectangle(ApplicationController.getStage().getWidth()
                , ApplicationController.getStage().getWidth(), Color.rgb(37, 40, 37, 0.88));
        root.getChildren().add(whiteOverlay);
        root.setBackground(new Background(ApplicationController.createBackGroundImage("/backgrounds/Gwent_1.jpg"
                , stage.getHeight(), stage.getWidth())));
        stage.show();
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
