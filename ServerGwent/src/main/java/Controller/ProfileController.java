package Controller;

import Model.User;
import WebConnection.Connection;
import WebConnection.SendingPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

    public static void saveChangesInServer(ArrayList<Object> objects) {
        String username = (String) objects.get(0);
        String password = (String) objects.get(1);
        String email = (String) objects.get(2);
        String nickname = (String) objects.get(3);

//        String respond
        if (User.getLoggedUser().getUsername().equals(username)
                && User.getLoggedUser().getPassword().equals(password)
                && User.getLoggedUser().getPassword().equals(email)
                && User.getLoggedUser().getNickName().equals(nickname)) {
            ApplicationController.alert("no changes!", "change at least one parameter");
        } else {
//            System.out.println("po");
//            changeInformation(username, password, email, nickname, objects);
            saveTheUsersInGson(User.getAllUsers());
        }
    }

    public static void saveTheUsersInGson(ArrayList<User> users) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);
        try (PrintWriter pw = new PrintWriter("users.json")) {
            pw.write(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static SendingPacket changeInformation(ArrayList<Object> objects) {
        String usernameField = (String) objects.get(0);
        String passwordField = (String) objects.get(1);
        String emailField = (String) objects.get(2);
        String nickNameField = (String) objects.get(3);

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
                && !User.getLoggedUser().getUsername().equals(usernameField)) {
            respond = ("this username already given");

        } else if (!usernameField.matches("[-a-zA-Z0-9]+")) {
            respond = ("username should contains a-z,A-Z,numbers and -");

        } else if (!emailField.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            respond = ("enter a valid email!");

        } else if (!passwordField.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9\\s]).+$")) {
            respond = ("password should contains a-z,A-Z,numbers and special characters");
        } else if (User.getLoggedUser().getUsername().equals(usernameField)
                && User.getLoggedUser().getPassword().equals(passwordField)
                && User.getLoggedUser().getPassword().equals(emailField)
                && User.getLoggedUser().getNickName().equals(nickNameField)) {
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
            // set the new information
            changeUserName(usernameField);
            changePassword(passwordField);
            changeEmail(emailField);
            changeNickName(nickNameField);
            saveTheUsersInGson(User.getAllUsers());
//            System.out.println("emsafan");
            return new SendingPacket("ProfileMenu", "changeInformationInClientModel", objects.toArray());
        }
    }

    public static SendingPacket sendRequest(ArrayList<Object> objects) {
        String friend = (String) objects.get(0);
        Connection connection = Connection.getConnectionByUserName(friend);
        User user = (User) objects.get(1);
        System.out.println(objects.get(1).toString());
        if (User.getUserByName(friend) == null)
            return new SendingPacket("ApplicationController", "alert2", "this user doesnt exist", "erorre!!");
        else
            user.getFriendRequests().add(friend);
        return new SendingPacket("ProfileMenu", "setRequest", connection, user.getUsername());
    }
    public static SendingPacket updateRequests(ArrayList<Object> objects){
        System.out.println(objects.get(1).toString());
        System.out.println(objects.get(1).toString());
        User user = (User)objects.get(1);
        ArrayList<String> strings = new ArrayList<>();
        if (user.getFriendRequests()!=null&&!user.getFriendRequests().isEmpty()){
            strings.addAll(user.getFriendRequests());
        }
        user.getFriendRequests().clear();
        return  new SendingPacket("ProfileMenu", "updateRequestInMenu",strings.toArray());
    }

    private static void confirmAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("your information changed successfully");
        alert.show();
    }

    public static SendingPacket beFriend(ArrayList<Object> objects) {
        // the second is connection owner
        User.getUserByName((String) objects.get(0)).getFriends().add((String) objects.get(1));
        User.getUserByName((String) objects.get(1)).getFriends().add((String) objects.get(0));
        return new SendingPacket("ProfileMenu", "comeFriend", objects.get(0), objects.get(1));
    }
}
