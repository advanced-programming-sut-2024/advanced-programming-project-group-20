package View;

import Controller.ApplicationController;
//import Controller.RegisterController;
import Controller.RegisterController;
import Model.Factions.Nilfgaard;
import Model.GameHistory;
import Model.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import webConnection.Client;
import webConnection.Packet;
import webConnection.Receiver;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegisterMenu extends Application {
    public static Pane root;
    @FXML
    private TextField repeatedPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nickNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (User.getLoggedUser() != null) System.out.println(User.getLoggedUser().getUsername());
        //setting title and Icon
        ApplicationController.setStage(stage);
        stage.setTitle("Gwent");
        //======================
        stage.setHeight(720);
        stage.setWidth(900);
        SetHeightAndWidth(stage);
        URL url = RegisterMenu.class.getResource("/FXML/RegisterMenu.fxml");
        root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.centerOnScreen();
        stage.setScene(scene);
        ApplicationController.setIcon();
        User.setAllUsers(parseFile(new File("users.json"), User.class));

        root.setBackground(new Background(ApplicationController.createBackGroundImage("/backgrounds/hh.jpg", stage.getHeight(), stage.getWidth())));
        stage.show();
    }

    public static ArrayList<User> parseFile(File file, Class<User> eClass) {
        ArrayList<User> arr = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JsonArray a = gson.fromJson(new FileReader(file), JsonArray.class);
            if (a == null) return arr;
            a.forEach(e -> {
                try {
                    JsonReader reader = new JsonReader(new StringReader(e.toString()));
                    reader.setLenient(true);
                    User obj = gson.fromJson(reader, eClass);
                    arr.add(obj);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
//            saveToFile(arr, file); //if file is not present, make it for first time
        }
        return arr;
    }

    public static void printRespond(String respond) {
        System.out.println(respond);
    }

    private void SetHeightAndWidth(Stage stage) {
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth() - 400) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight() - 300) / 2);
    }

    public void goToLoginMenu(MouseEvent mouseEvent) {
        try {
            new LoginMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void register(MouseEvent mouseEvent) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(usernameField.getText());
        objects.add(passwordField.getText());
        objects.add(emailField.getText());
        objects.add(nickNameField.getText());
        objects.add(repeatedPasswordField.getText());
        Client.getConnection().doInServer("RegisterController", "register", objects);
    }

    //TODO delete this later
    public void gotoMainMenu(MouseEvent mouseEvent) throws Exception {
        Object[] objects = new Object[2];
        objects[0] = "hamid";
        objects[1] = "ali";
        Client.getConnection().doInServer("PreGameController", "getUserByName", objects);
    }
    public static void setLogin(ArrayList<Object> objects) throws Exception {
        Gson gson = new Gson();
        User user = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User.setLoggedUser(user);
        User opponent = gson.fromJson(gson.toJson(objects.get(1)), User.class);
        user.setOpponentUser(opponent);
        opponent.setOpponentUser(user);
        User.getLoggedUser().readyForGame();
        User.getLoggedUser().getOpponentUser().readyForGame();
        Platform.runLater(() -> {
            PreGameMenu preGameMenu = new PreGameMenu();
            try {
                preGameMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

//    public void makeRandomPassword(MouseEvent mouseEvent) {
//        RegisterController.randomPassword(passwordField, repeatedPasswordField);
//    }

}