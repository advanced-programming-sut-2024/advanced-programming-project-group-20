package Controller;

import Model.User;
import WebConnection.Connection;
import WebConnection.SendingPacket;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;

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

    public static void saveChanges(TextField username, TextField password, TextField email, TextField nickname) {
        if (User.getLoggedUser().getUsername().equals(username.getText())
                && User.getLoggedUser().getPassword().equals(password.getText())
                && User.getLoggedUser().getPassword().equals(email.getText())
                && User.getLoggedUser().getNickName().equals(nickname.getText())) {
            ApplicationController.alert("no changes!", "change at least one parameter");
        } else {
            changeInformation(username.getText(), password.getText(), email.getText(), nickname.getText());
            ApplicationController.saveTheUsersInGson(User.getAllUsers());

        }
    }
    public static void changeInformation(String usernameField, String passwordField, String emailField, String nickNameField) {
        if (usernameField.isEmpty()) {
            ApplicationController.alert("invalid username", "username section is empty!");

        } else if (passwordField.isEmpty()) {
            ApplicationController.alert("invalid password", "password section is empty!");

        } else if (emailField.isEmpty()) {
            ApplicationController.alert("invalid email", "email section is empty!");

        } else if (nickNameField.isEmpty()) {
            ApplicationController.alert("invalid nickname", "nickname section is empty!");

        } else if (User.giveUserByUsername(usernameField) != null
                && !User.getLoggedUser().getUsername().equals(usernameField)) {
            ApplicationController.alert("invalid username", "this username already given");

        } else if (!usernameField.matches("[-a-zA-Z0-9]+")) {
            ApplicationController.alert("invalid username", "username should contains a-z,A-Z,numbers and -");

        } else if (!emailField.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            ApplicationController.alert("invalid email", "");

        } else if (!passwordField.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9\\s]).+$")) {
            ApplicationController.alert("invalid password", "password should contains a-z,A-Z,numbers and special characters");

        } else {
            // set the new information
            changeUserName(usernameField);
            changePassword(passwordField);
            changeEmail(emailField);
            changeNickName(nickNameField);
            confirmAlert();
        }
    }

    public static SendingPacket sendRequest(ArrayList<Object> objects){
        String friend = (String) objects.get(0);
        Connection connection = Connection.getConnectionByUserName(friend);
        User user = (User) objects.get(1);
        System.out.println(objects.get(1).toString());
        if (connection==null)
            return new SendingPacket("ApplicationController","alert2","this user doesnt exist","erorre!!");
        else
            return new SendingPacket("ProfileMenu", "setRequest", connection,user.getUsername());
    }
    private static void confirmAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("your information changed successfully");
        alert.show();
    }
    public static SendingPacket beFriend(ArrayList<Object> objects){
        // the second is connection owner
        User.getUserByName((String) objects.get(0)).getFriends().add(User.getUserByName((String) objects.get(1)));
        User.getUserByName((String) objects.get(1)).getFriends().add(User.getUserByName((String) objects.get(0)));
        return new SendingPacket("ProfileMenu","comeFriend",objects.get(0), objects.get(1));
    }
}
