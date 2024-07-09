package Controller;

import Model.GameHistory;
import Model.User;

import WebConnection.SendingPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            return new SendingPacket(className, methodeName, respondObjects.toArray());
        }
        return new SendingPacket("RegisterMenu", "showSecurityQuestions", objects.toArray());
    }

    public static void addUserToServerModel(ArrayList<Object> objects) {
        Gson gson = new Gson();
        User user = gson.fromJson(gson.toJson(objects.get(0)), User.class);

        if (user != null)
            User.getAllUsers().add(user);

        ArrayList<Object> objects1 = new ArrayList<>(User.getAllUsers());
        ApplicationController.saveTheUsersInGson(objects1);
    }


    public static SendingPacket randomPassword(ArrayList<Object> objects) {
        Random random = new Random();
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{},.<>?";
        StringBuilder randomPassword = new StringBuilder(8);

        String validChars = uppercase + lowercase + digits + specialChars;
        for (int i = 0; i < 4; i++) {
            randomPassword.append(validChars.charAt(random.nextInt(validChars.length())));
        }
        randomPassword.append(uppercase.charAt(random.nextInt(uppercase.length())));
        randomPassword.append(digits.charAt(random.nextInt(digits.length())));
        randomPassword.append(lowercase.charAt(random.nextInt(lowercase.length())));
        randomPassword.append(specialChars.charAt(random.nextInt(specialChars.length())));


        ArrayList<Object> objects1 = new ArrayList<>();
        objects1.add(randomPassword.toString());
        return new SendingPacket("RegisterMenu", "showConfirmAlertOfRandomPassword", objects1.toArray());
    }


    ///this method just is called first of program and don't use it for giving users from it
    public static SendingPacket parseFile(ArrayList<Object> objects) {

        ResultSet rs = null;
        String gsonString = "";
        File file = new File("sample.db");
        if (!file.exists()) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        } else {
            try {
                // Establishing the database connection
                Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
                Statement stmt = conn.createStatement();

                ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) AS count FROM users");
                int rowCount = resultSet.getInt("count");
                if (rowCount == 0) {
                    System.out.println("Nothing in the table");
                    return null;
                }
                // Query data
                rs = stmt.executeQuery("SELECT * FROM users");
                while (rs.next()) {
                    gsonString = rs.getString("names");
                }

                // Closing the resources
                stmt.close();
                conn.close();
                ArrayList<Object> objects1;
                Gson gson = new Gson();
                Type listType = new TypeToken<List<User>>() {
                }.getType();
                objects1 = gson.fromJson(gsonString, listType);

                if (objects1 != null) {
                    for (Object object : objects1) {
                        User user = (User) object;
                        if (User.getUserByName(user.getUsername()) != null) continue;
                        User.getAllUsers().add((User) object);
                        if (user.getGameHistories() == null) user.setGameHistories(new ArrayList<>());
                    }

                } else {
                    objects1 = new ArrayList<>();
                }
                return new SendingPacket("RegisterMenu", "loadAllUsersFromServer", objects1.toArray());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
