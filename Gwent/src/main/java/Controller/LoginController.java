package Controller;

import Model.User;
import View.MainMenu;
import View.RegisterMenu;
import javafx.scene.control.TextField;

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

}
