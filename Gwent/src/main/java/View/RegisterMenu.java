package View;

import Controller.ApplicationController;
import Controller.RegisterController;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class RegisterMenu extends Application {
    public static Stage stage;
    @FXML
    private TextField repeatedPasswordField;
    @FXML
    private CheckBox checkLogin;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nickNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;


    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        RegisterMenu.stage = stage;
        stage.setHeight(720);
        stage.setWidth(900);
//        SetHeightAndWidth(stage);
        URL url = RegisterMenu.class.getResource("/FXML/RegisterMenu.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        root.setBackground(new Background(ApplicationController.createBackGroundImage("/backgrounds/hh.jpg",stage.getHeight(), stage.getWidth())));
        stage.show();
    }

    private void SetHeightAndWidth(Stage stage) {
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth() - 400) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight() - 300) / 2);
    }

    public void goToLoginMenu(MouseEvent mouseEvent) {
        try {
            new LoginMenu().start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void register(MouseEvent mouseEvent) {
        RegisterController.register(usernameField, passwordField, emailField, nickNameField, repeatedPasswordField);
    }
}