package View;

import Controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;

import Model.User;
import webConnection.Client;

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
//        LoginController.login(usernameField, passwordField);
        Client.getConnection().doInServer("LoginController", "login", usernameField.getText(), passwordField.getText());
    }

    public void goRegisterMenu(MouseEvent mouseEvent) {
        try {
            new RegisterMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void forgetPasswordAndShowQuestion() {
        if (!confirmButton.isVisible()) {
            if (!usernameField.getText().equals("")) {
                forgetButton.setVisible(false);
                confirmButton.setVisible(true);
//                Client.getConnection().doInServer("LoginController", "securityAlert", new ArrayList<Object>().toArray());
                securityAlert();
                passwordField.setText("");
                secureQuestionField.setText(User.getUserByName(usernameField.getText()).getSecureQuestion());
                secureQuestionField.setVisible(true);
                passwordField.setPromptText("security answer");
            } else {
                ApplicationController.alert("Empty Username", "Enter Your Username");
            }
        }
    }
    public void securityAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Answer the security question!");
        alert.setHeaderText("security check");
        alert.show();
    }
    public void confirmSecurity(MouseEvent mouseEvent) {
        checkSecurityQuestion();
    }

    public static void loginToMainMenu(ArrayList<Object> objects) {
        System.out.println("omadi");
        User.setLoggedUser(User.giveUserByUsername((String) objects.getFirst()));
        MainMenu mainMenu = new MainMenu();
            Platform.runLater(() -> {
                try {
                    mainMenu.start(ApplicationController.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

    }

    private void changePasswordOfUser() {
        Rectangle whiteOverlay = new Rectangle(ApplicationController.getStage().getWidth()
                , ApplicationController.getStage().getWidth(), Color.rgb(255, 255, 255, 0.9));
        LoginMenu.root.getChildren().add(whiteOverlay);
        Label label4 = new Label("Enter Your New Password And Repeat.");
        label4.setLayoutX(160);
        label4.setLayoutY(100);
        label4.setPrefHeight(100);
        label4.setPrefWidth(600);
        label4.setStyle("-fx-font-size: 30");
        LoginMenu.root.getChildren().add(label4);
        Label label = new Label("Password");
        label.setLayoutX(170);
        label.setLayoutY(230);
        label.setPrefHeight(100);
        label.setPrefWidth(400);
        label.setStyle("-fx-font-size: 30");
        LoginMenu.root.getChildren().add(label);
        Label label1 = new Label("Repeat Password");
        label1.setLayoutX(170);
        label1.setLayoutY(330);
        label1.setPrefHeight(100);
        label1.setPrefWidth(400);
        label1.setStyle("-fx-font-size: 30");
        LoginMenu.root.getChildren().add(label1);
        TextField textField = new TextField();
        textField.setLayoutX(490);
        textField.setLayoutY(255);
        textField.setPrefHeight(50);
        textField.setPrefWidth(200);
        textField.setStyle("-fx-background-color: #c3e0a9");
        TextField textField1 = new TextField();
        textField1.setLayoutX(490);
        textField1.setLayoutY(355);
        textField1.setPrefHeight(50);
        textField1.setPrefWidth(200);
        textField1.setStyle("-fx-background-color: #c3e0a9");
        LoginMenu.root.getChildren().add(textField);
        LoginMenu.root.getChildren().add(textField1);
        //
        Button button = new Button("continue");
        button.setLayoutY(460);
        button.setLayoutX(320);
        button.setPrefHeight(50);
        button.setPrefWidth(200);
        LoginMenu.root.getChildren().add(button);
        String username = User.getUserByName(usernameField.getText()).getUsername();
        button.setOnAction(e -> {
            //check valid password
            if (textField.getText().equals(textField1.getText())) {
//                ProfileController.changeInformation(username, String.valueOf(textField), email, nickname);
//                LoginMenu.root.getChildren().removeAll(whiteOverlay, button, label1, label, textField1, textField);
                if (textField.getText().equals("")) {
                    ApplicationController.alert("invalid password", "password section is empty!");
                } else if (!textField.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9\\s]).+$")) {
                    ApplicationController.alert("invalid password", "password should contains a-z,A-Z,numbers and special characters");
                } else {
                    User.getUserByName(username).setPassword(textField.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Congratulations New Password:)");
                    alert.setContentText("Your Password Changed Successfully");
                    alert.show();
                    LoginMenu.root.getChildren().removeAll(whiteOverlay, button, label1, label, textField1, textField, label4);
                    confirmButton.setVisible(false);
                    forgetButton.setVisible(true);
                    secureQuestionField.setVisible(false);
                    usernameField.setText("");
                    passwordField.setText("");
                    ArrayList<Object> objects = new ArrayList<>();
//                    for (User){
//
//                    }
                    objects.addAll(User.getAllUsers());
                    Client.getConnection().doInServer("ApplicationController", "saveTheUsersInGson", objects.toArray());
                }
            } else {
                ApplicationController.alert("Unsimilarity wrong", "Repeated password is different with password!");
            }
        });
    }

    public void checkSecurityQuestion() {

        if (!forgetButton.isVisible()) {
            if (usernameField.getText().equals("")) {
                ApplicationController.alert("Empty username", "Enter the username");
            } else if (User.getUserByName(usernameField.getText()) == null) {
                ApplicationController.alert("Wrong Username", "No Such Username");
            } else if (!passwordField.getText().equals(User.getUserByName(usernameField.getText()).getSecureAnswer())) {
                ApplicationController.alert("Wrong answer", "Enter the right answer!");
            } else {
                changePasswordOfUser();
            }
        }
    }
}
