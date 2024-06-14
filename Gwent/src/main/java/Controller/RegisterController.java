package Controller;

import Model.User;
import View.LoginMenu;
import View.RegisterMenu;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class RegisterController {
    //    public static String Register(String username,String password,String passwordConfirm,String nickName,String email){
//
//        return null;
//    }
    private static void pickQuestion(int questionNumber, String answer, String answerConfirm) {

    }

    public static String login(String username, String password) {
        return null;
    }

    public static String forgetPassword(String username) {
        return null;
    }

    private static boolean checkAnswer(int questionNumber, String answer) {
        return true;
    }

    public static void register(TextField usernameField, TextField passwordField, TextField emailField, TextField nickNameField, TextField repeatedPasswordField) {
        if (usernameField.getText().isEmpty()) {
            ApplicationController.alert("invalid username", "username section is empty!");

        } else if (passwordField.getText().isEmpty()) {
            ApplicationController.alert("invalid password", "password section is empty!");

        } else if (emailField.getText().isEmpty()) {
            ApplicationController.alert("invalid email", "email section is empty!");

        } else if (nickNameField.getText().isEmpty()) {
            ApplicationController.alert("invalid nickname", "nickname section is empty!");

        } else if (User.giveUserByUsername(usernameField.getText()) != null) {
            ApplicationController.alert("invalid username", "this username already given");

        } else if (!usernameField.getText().matches("[-a-zA-Z0-9]+")) {
            ApplicationController.alert("invalid username", "username should contains a-z,A-Z,numbers and -");

        } else if (!emailField.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            ApplicationController.alert("invalid email", "enter a valid email");

        } else if (!passwordField.getText().matches("\\S+")) {
            ApplicationController.alert("invalid password", "password should contains a-z,A-Z,numbers and special characters");

        } else if (!passwordField.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9\\s]).+$")) {
            ApplicationController.alert("invalid password", "password should contains a-z,A-Z,numbers and special characters");

        } else if (!passwordField.getText().equals(repeatedPasswordField.getText())) {
            ApplicationController.alert("repeated password is not similar with password", "enter a password or back to register menu");
            passwordField.setText("");
            repeatedPasswordField.setText("");

        } else {
            addANewUser(usernameField, passwordField, emailField, nickNameField);
        }
    }

    public static void addANewUser(TextField usernameField, TextField passwordField, TextField emailField, TextField nickNameField) {
        User.getAllUsers().add(new User(usernameField.getText()
                , passwordField.getText(), nickNameField.getText(), emailField.getText()));
        User.setLoggedUser(User.giveUserByUsername(usernameField.getText()));
        LoginMenu loginMenu = new LoginMenu();
        try {
            loginMenu.start(RegisterMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
