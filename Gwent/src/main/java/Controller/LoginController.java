package Controller;

import Model.User;
import View.ApplicationController;
import View.LoginMenu;
import View.MainMenu;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LoginController {
    public static void login(TextField usernameField, TextField passwordField) {
        if (usernameField.getText().isEmpty()) {
            ApplicationController.alert("invalid username", "Enter a username");

        } else if (passwordField.getText().isEmpty()) {
            ApplicationController.alert("invalid password", "Enter a password");

        } else if (User.giveUserByUsername(usernameField.getText()) == null) {
            ApplicationController.alert("wrong username", "No such user exist!");

        } else if (User.giveUserByUsername(usernameField.getText()) != null
                && !User.giveUserByUsername(usernameField.getText()).getPassword().equals(passwordField.getText())) {
            ApplicationController.alert("wrong password", "enter the password correctly");

        } else {
            User.setLoggedUser(User.giveUserByUsername(usernameField.getText()));
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void showSecurityQuestion(Label secureQuestionField, TextField usernameField, TextField passwordField, Button forgetButton, Button confirmButton) {
        if (!confirmButton.isVisible()) {
            if (!usernameField.getText().equals("")) {
                forgetButton.setVisible(false);
                confirmButton.setVisible(true);
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

    private static void securityAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Answer the security question!");
        alert.setHeaderText("security check");
        alert.show();
    }

    public static void checkSecurityQuestion(Label secureQuestionField, TextField usernameField, TextField passwordField, Button forgetButton, Button confirmButton) {
        if (!forgetButton.isVisible()) {
            if (usernameField.getText().equals("")) {
                ApplicationController.alert("Empty username", "Enter the username");
            } else if (User.getUserByName(usernameField.getText()) == null) {
                ApplicationController.alert("Wrong Username", "No Such Username");
            } else if (!passwordField.getText().equals(User.getUserByName(usernameField.getText()).getSecureAnswer())) {
                ApplicationController.alert("Wrong answer", "Enter the right answer!");
            } else {
                changePasswordOfUser(usernameField, secureQuestionField, confirmButton, forgetButton, passwordField);
            }
        }
    }

    private static void changePasswordOfUser(TextField usernameField, Label secureQuestionField, Button confirmButton, Button forgetButton, TextField passwordField) {
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
                }
            } else {
                ApplicationController.alert("Unsimilarity wrong", "Repeated password is different with password!");
            }
        });
    }

}
