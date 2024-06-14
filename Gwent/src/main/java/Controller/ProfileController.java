package Controller;

import Model.User;
import View.RegisterMenu;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ProfileController {

    public static void changeUserName(String username) {
        User.getLoggedUser().setUsername(username);
    }

    public static void changePassword(String password) {
        User.getLoggedUser().setPassword(password);
    }

    public static void changeNickName(String nickname) {
        User.getLoggedUser().setNickName(nickname);
    }

    public static void changeEmail(String email) {
        User.getLoggedUser().setEmail(email);
    }

    public static String showUserInfo() {
        return null;
    }

    public static void saveChanges(TextField username, TextField password, TextField email, TextField nickname) {
        if (User.getLoggedUser().getUsername().equals(username.getText())
                && User.getLoggedUser().getPassword().equals(password.getText())
                && User.getLoggedUser().getPassword().equals(email.getText())
                && User.getLoggedUser().getNickName().equals(nickname.getText())) {
            ApplicationController.alert("no changes!", "change at least one parameter");
        } else {
            changeInformation(username, password, email, nickname);
        }
    }
    public static void changeInformation(TextField usernameField, TextField passwordField, TextField emailField, TextField nickNameField) {
        if (usernameField.getText().isEmpty()) {
            ApplicationController.alert("invalid username", "username section is empty!");

        } else if (passwordField.getText().isEmpty()) {
            ApplicationController.alert("invalid password", "password section is empty!");

        } else if (emailField.getText().isEmpty()) {
            ApplicationController.alert("invalid email", "email section is empty!");

        } else if (nickNameField.getText().isEmpty()) {
            ApplicationController.alert("invalid nickname", "nickname section is empty!");

        } else if (User.giveUserByUsername(usernameField.getText()) != null
                && !User.getLoggedUser().getUsername().equals(usernameField.getText())) {
            ApplicationController.alert("invalid username", "this username already given");

        } else if (!usernameField.getText().matches("[-a-zA-Z0-9]+")) {
            ApplicationController.alert("invalid username", "username should contains a-z,A-Z,numbers and -");

        } else if (!emailField.getText().matches("[-a-zA-Z0-9]+")) {
            ApplicationController.alert("invalid email", "");
//Todo
        } else if (!passwordField.getText().matches("\\S+")) {
            ApplicationController.alert("invalid password", "password should contains a-z,A-Z,numbers and special characters");

        } else if (!passwordField.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9\\s]).+$")) {
            ApplicationController.alert("invalid password", "password should contains a-z,A-Z,numbers and special characters");
        } else {
            // set the new information
            changeUserName(usernameField.getText());
            changePassword(passwordField.getText());
            changeEmail(emailField.getText());
            changeNickName(nickNameField.getText());
            confirmAlert();
        }
    }

    private static void confirmAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("your information changed successfully");
        alert.show();
    }
}
