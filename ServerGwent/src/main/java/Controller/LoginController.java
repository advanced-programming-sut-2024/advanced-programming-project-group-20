package Controller;

import Model.User;

import WebConnection.SendingPacket;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class LoginController {
    public static SendingPacket login(ArrayList<Object> objects) {
        String username = (String) objects.get(0);
        String password = (String) objects.get(1);
        String respond = "";
        if (username.isEmpty()) {
            respond = ("Enter a username");

        } else if (password.isEmpty()) {
            respond = ("Enter a password");

        } else if (User.giveUserByUsername(username) == null) {

            respond = ("No such user exist!");

        } else if (User.giveUserByUsername(username) != null
                && !User.giveUserByUsername(username).getPassword().equals(password)) {
            respond = ("enter the password correctly");

        }

        if (!respond.isEmpty()) {
            ArrayList<Object> respondObjects = new ArrayList<>();
            respondObjects.add(respond);
            respondObjects.add("error!!");
            String className = "ApplicationController";
            String methodeName = "alert2";
            return new SendingPacket(className, methodeName, respondObjects.toArray());
        } else {
            User.setLoggedUser(User.getUserByName(username));
            ArrayList<Object> objects1 = new ArrayList<>();
            objects1.add(username);
            return new SendingPacket("LoginMenu", "loginToMainMenu", objects1.toArray());
        }
    }


}
