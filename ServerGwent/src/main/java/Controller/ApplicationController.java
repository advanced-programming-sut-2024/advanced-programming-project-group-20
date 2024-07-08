package Controller;

import Model.User;

import WebConnection.SendingPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class ApplicationController {
    private static Stage stage;
    private static Pane root;
    private static Scene scene;

    public static void alert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }
//<<<<<<< HEAD

    public static void logout(ArrayList<Object> objects) {
        ArrayList<Object> objects1 = new ArrayList<>();
        for (User user : User.getAllUsers()) {
            objects1.add(user);
        }
        ApplicationController.saveTheUsersInGson(objects1);
    }

    // if you want to have all users of Server you should call this method in your client
    public static SendingPacket deliverUsersOfServerToClient(ArrayList<Object> objects) {
        ArrayList<Object> objects1 = new ArrayList<>();
        for (User user : User.getAllUsers()) {
            objects1.add(user);
        }
        ApplicationController.saveTheUsersInGson(objects1);
        return new SendingPacket("ApplicationController"
                , "receiveUsersOfServerSent", User.getAllUsers().toArray());
    }

    public static void saveTheUsersInGson(ArrayList<Object> objects) {
        Gson gson = new Gson();
        ArrayList<User> usersToSave = new ArrayList<>();

        for (User user : User.getAllUsers()) {
            System.out.println("Userrr: " + user.getUsername());
        }

        for (Object object : objects) {
            User user = gson.fromJson(gson.toJson(object), User.class);
            System.out.println(user.getUsername() + "ajab");
            usersToSave.add(user);
        }

        for (User user : usersToSave) {
            System.out.println("User: " + user.getUsername());
        }

        for (User user: User.getAllUsers()){
            System.out.println("("+user.getUsername()+")");
        }

        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        String json = gson1.toJson(User.getAllUsers());


//========================================================================================================================
//         store gson in data base
        ResultSet rs = null;
        try {
            // Establishing the database connection
            Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement stmt = conn.createStatement();

            // Delete the last inserted data
            // stmt.execute("DELETE FROM users WHERE id = (SELECT MAX(id) FROM your_table)");

            String dropQuery = "DROP TABLE IF EXISTS users";

            // Execute the delete statement
            stmt.executeUpdate(dropQuery);
            // Create table
            String sql = "CREATE TABLE IF NOT EXISTS users (names TEXT)";
            stmt.execute(sql);

            // Insert data
            stmt.execute("INSERT INTO users (names) VALUES ('" + json + "')");

            // Query data
            rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.println(rs.getString("names") + "   h");
            }
            // Executing the insert query

            // Closing the resources
            stmt.close();
            conn.close();

            System.out.println("Gson data saved successfully in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//=================================================================================================================

    }

}
