package View;

import Controller.ApplicationController;
import Controller.LoginController;
import Controller.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

import Model.User;

public class LoginMenu extends Application {
    public static Pane root;
    public Button confirmButton;
    @FXML
    private Button forgetButton;
    @FXML
    private Label secureQuestionField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setHeight(720);
        stage.setWidth(900);
        URL url = RegisterMenu.class.getResource("/FXML/LoginMenu.fxml");
        root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        root.setBackground(new Background(ApplicationController.createBackGroundImage("/backgrounds/hh.jpg", stage.getHeight(), stage.getWidth())));
        stage.show();
    }

    public void login(MouseEvent mouseEvent) {
        LoginController.login(usernameField, passwordField);
    }

    public void goRegisterMenu(MouseEvent mouseEvent) {
        try {
            new RegisterMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void forgetPassword(MouseEvent mouseEvent) {
        LoginController.showSecurityQuestion(secureQuestionField, usernameField, passwordField, forgetButton, confirmButton);
    }

    public void confirmSecurity(MouseEvent mouseEvent) {
        LoginController.checkSecurityQuestion(secureQuestionField, usernameField, passwordField, forgetButton, confirmButton);
    }
}
