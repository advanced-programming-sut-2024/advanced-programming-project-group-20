package Controller;

import Model.GameHistory;
import Model.Request;
import Model.User;
import WebConnection.Connection;
import WebConnection.SendingPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ProfileController {

    public static void changeUserName(String lastUsername, String usernameField) {
        User.getUserByName(lastUsername).setUsername(usernameField);
    }

    public static void changePassword(User user, String passwordField) {
        user.setPassword(passwordField);
    }

    public static void changeNickName(User user, String nickNameField) {
        user.setNickName(nickNameField);
    }

    public static void changeEmail(User user, String emailField) {
        user.setEmail(emailField);
    }

    public static SendingPacket getGameHistories(ArrayList<Object> objects) {
        User user = User.getUserByName((String) objects.get(0));
        ArrayList<Object> objects1 = new ArrayList<>();
        if (user.getGameHistories() != null) {
            for (GameHistory gameHistory : user.getGameHistories()) {
                objects1.add(gameHistory);
            }
        } else {
            user.setGameHistories(new ArrayList<>());
        }

        return new SendingPacket("ProfileMenu", "setGameHistories", objects1.toArray());
    }

    public static SendingPacket changeInformationUsingButtonSaveChanges(ArrayList<Object> objects) {
        String lastUsername = (String) objects.get(0);
        String usernameField = (String) objects.get(1);
        String passwordField = (String) objects.get(2);
        String emailField = (String) objects.get(3);
        String nickNameField = (String) objects.get(4);

        String respond = "";

        if (usernameField.isEmpty()) {
            respond = ("username section is empty!");

        } else if (passwordField.isEmpty()) {
            respond = ("password section is empty!");

        } else if (emailField.isEmpty()) {
            respond = ("email section is empty!");

        } else if (nickNameField.isEmpty()) {
            respond = ("nickname section is empty!");

        } else if (User.giveUserByUsername(usernameField) != null
                && !lastUsername.equals(usernameField)) {
            respond = ("this username already given");

        } else if (!usernameField.matches("[-a-zA-Z0-9]+")) {
            respond = ("username should contains a-z,A-Z,numbers and -");

        } else if (!emailField.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            respond = ("enter a valid email!");

        } else if (!passwordField.matches("\\S{8,}")) {
            respond = "password should contains at least 8 characters";

        } else if (!passwordField.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9\\s]).+$")) {
            respond = ("password should contains a-z,A-Z,numbers and special characters");
        } else if (User.getUserByName(lastUsername).getUsername().equals(usernameField)
                && User.getUserByName(lastUsername).getPassword().equals(passwordField)
                && User.getUserByName(lastUsername).getPassword().equals(emailField)
                && User.getUserByName(lastUsername).getNickName().equals(nickNameField)) {
            respond = ("change at least one parameter");
        }
//        ========================================
        if (!respond.isEmpty()) {
            ArrayList<Object> respondObjects = new ArrayList<>();
            respondObjects.add(respond);
            respondObjects.add("error!!");
            String className = "ApplicationController";
            String methodeName = "alert2";
            return new SendingPacket(className, methodeName, respondObjects.toArray());
        } else {

            changeUserName(lastUsername, usernameField);
            changePassword(User.getUserByName(usernameField), passwordField);
            changeEmail(User.getUserByName(usernameField), emailField);
            changeNickName(User.getUserByName(usernameField), nickNameField);
//            saveTheUsersInGson(User.getAllUsers());
            ArrayList<Object> objects1 = new ArrayList<>();
            for (User user: User.getAllUsers()){
                objects1.add(user);
            }
            ApplicationController.saveTheUsersInGson(objects1);
            return new SendingPacket("ProfileMenu", "changeInformationInClientModel", objects.toArray());
        }
    }

    public static SendingPacket sendRequest(ArrayList<Object> objects) {
        String friend = (String) objects.get(0);
        Connection connection = Connection.getConnectionByUserName(friend);
        User user = (User) objects.get(1);
        System.out.println("into send request" + ((User) objects.get(1)).getUsername());
        if (User.getUserByName(friend) == null)
            return new SendingPacket("ApplicationController", "alert2", "this user doesnt exist", "erorre!!");
        else if (!User.getUserByName(friend).getFriendRequests().contains(user.getUsername())) {
            User.getUserByName(friend).getFriendRequests().add(user.getUsername());
            user.getRequests().add(new Request(friend));
        }
//todo age request kar nakard inon comment nakon
//        if (connection == null)
            return null;
//        return new SendingPacket("ProfileMenu", "setRequest", connection, user.getUsername());
    }

    public static SendingPacket updateRequests(ArrayList<Object> objects) {
        String friendName =(String) objects.get(0);
        String username = ((User)objects.get(1)).getUsername();
        User.getUserByName(friendName).getFriendRequests().removeIf(string -> string.equals(username));
        User.getUserByName(username).getFriendRequests().removeIf(string -> string.equals(friendName));
        setRequestHistory(friendName);
        return null;

    }

    private static void setRequestHistory(String friendName) {
        for (Request request : User.getUserByName(friendName).getRequests()) {
            if (User.getUserByName(friendName).getFriends().contains(request.getFriendName()) && !request.getResult().equals("rejected")) {
                request.setResult("accepted");
            }
            if (!User.getUserByName(request.getFriendName()).getFriendRequests().contains(User.getUserByName(friendName)) && !request.getResult().equals("accepted")) {
                request.setResult("rejected");
            }
        }
    }

    private static void confirmAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("your information changed successfully");
        alert.show();
    }

    public static SendingPacket beFriend(ArrayList<Object> objects) {
        String friendName = (String) objects.get(0);
        String username = (String) objects.get(1);
        // the second is connection owner
        if (!User.getUserByName(friendName).getFriends().contains(username))
            User.getUserByName(friendName).getFriends().add(username);
        if (!User.getUserByName(username).getFriends().contains(friendName))
            User.getUserByName(username).getFriends().add(friendName);
        User.getUserByName(friendName).getFriendRequests().removeIf(string -> string.equals(username));
        User.getUserByName(username).getFriendRequests().removeIf(string -> string.equals(friendName));

        setRequestHistory(friendName);
        ArrayList<Object> objects1 = new ArrayList<>();
        for (User user: User.getAllUsers()){
            objects1.add(user);
        }

        ApplicationController.saveTheUsersInGson(objects1);
        return null;

    }

    public static SendingPacket showLastGame(ArrayList<Object> objects) {
        User user = User.getUserByName((String) objects.get(0));
        GameHistory gameHistory = user.getGameHistories().getLast();
        Object[] objects1 = new Object[2];
        objects1[0] = user.getUsername();
        objects1[1] = gameHistory;
        return new SendingPacket("ProfileMenu", "showLastGame", objects1);
    }
}
