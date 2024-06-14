package View;

import Controller.ApplicationController;
import Controller.LoginController;
import Controller.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

import Model.User;

public class LoginMenu extends Application {
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = RegisterMenu.class.getResource("/FXML/LoginMenu.fxml");
        Pane root = FXMLLoader.load(url);
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
}