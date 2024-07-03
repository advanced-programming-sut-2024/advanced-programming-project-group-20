package Controller;

import Model.User;

import WebConnection.SendingPacket;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Random;
public class RegisterController {

    public static SendingPacket register(ArrayList<Object> objects) {
        String usernameField = (String) (objects.get(0));
        String passwordField = (String) (objects.get(1));
        String emailField = (String) (objects.get(2));
        String nickNameField = (String) (objects.get(3));
        String repeatedPasswordField = (String) (objects.get(4));
        String respond = "";
        if (usernameField.isEmpty()) {
            respond = ("username section is empty!");

        } else if (passwordField.isEmpty()) {
            respond = ("password section is empty!");

        } else if (emailField.isEmpty()) {
            respond = ("email section is empty!");

        } else if (nickNameField.isEmpty()) {
            respond = ("nickname section is empty!");

        } else if (User.giveUserByUsername(usernameField) != null) {
            respond = ("this username already given");

        } else if (!usernameField.matches("[-a-zA-Z0-9]+")) {
            respond = ("username should contains a-z,A-Z,numbers and -");

        } else if (!emailField.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            respond = ("enter a valid email");

        } else if (!passwordField.matches("\\S+")) {
            respond = ("password should contains a-z,A-Z,numbers and special characters");

        } else if (!passwordField.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9\\s]).+$")) {
            respond = ("password should contains a-z,A-Z,numbers and special characters");

        } else if (!passwordField.equals(repeatedPasswordField)) {
            respond = ("repeated password is not similar with password. enter a password or back to register menu");
        }
        if (!respond.isEmpty()) {
            ArrayList<Object> respondObjects = new ArrayList<>();
            respondObjects.add(respond);
            respondObjects.add("error!!");
            String className = "ApplicationController";
            String methodeName = "alert2";
            return new SendingPacket(className,methodeName,respondObjects.toArray());
        }
//        showSecurityQuestions(usernameField, passwordField, nickNameField, emailField);
        return new SendingPacket("RegisterMenu","showSecurityQuestions",objects.toArray());
    }

    public static void addUserToServerModel(ArrayList<Object> objects){

        User user = new User((String) objects.get(0), (String) objects.get(1), (String) objects.get(3)
                , (String) objects.get(2), (String) objects.get(4), (String) objects.get(5));

        for (User user1: User.getAllUsers()){
            System.out.println(user1.getUsername());
        }
    }

    public static SendingPacket randomPassword(ArrayList<Object> objects) {
        Random random = new Random();
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{},.<>?";
        StringBuilder randomPassword = new StringBuilder(8);

        randomPassword.append(uppercase.charAt(random.nextInt(uppercase.length())));
        randomPassword.append(digits.charAt(random.nextInt(digits.length())));
        randomPassword.append(lowercase.charAt(random.nextInt(lowercase.length())));
        randomPassword.append(specialChars.charAt(random.nextInt(specialChars.length())));
        String validChars = uppercase + lowercase + digits + specialChars;
        for (int i = 4; i < 8; i++) {
            randomPassword.append(validChars.charAt(random.nextInt(validChars.length())));
        }
        //showing confirmation alert

//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation Dialog");
//        alert.setHeaderText("your password: " + randomPassword);
//        alert.setContentText("do you want to set this for your password");
//
//        alert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.OK) {
//                objects.set(0, randomPassword);
//                objects.set(1, randomPassword);
//            }
//        });
        ArrayList<Object> objects1 = new ArrayList<>();
        objects1.add(randomPassword.toString());
        return new SendingPacket("RegisterMenu", "showConfirmAlertOfRandomPassword",objects1.toArray());
    }

    public static SendingPacket parseFile(ArrayList<Object> objects) {
        ArrayList<User> arr = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JsonArray a = gson.fromJson(new FileReader("users.json"), JsonArray.class);
            if (a == null) return null;
            a.forEach(e -> {
                try {
                    JsonReader reader = new JsonReader(new StringReader(e.toString()));
                    reader.setLenient(true);
                    User obj = gson.fromJson(reader, User.class);
                    arr.add(obj);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
//            saveToFile(arr, file); //if file is not present, make it for first time
        }
        User.getAllUsers().addAll(arr);

        ArrayList<Object> objects1 = new ArrayList<>();
        for (User user: arr){
            objects1.add(user);
//            System.out.println(user.getUsername());
        }
        return new SendingPacket("RegisterMenu", "loadAllUsersFromServer", objects1.toArray());
    }
}
