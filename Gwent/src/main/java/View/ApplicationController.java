package View;

import Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;


public class ApplicationController {
    private static Stage stage;
    private static Pane root;
    private static Scene scene;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private static MediaPlayer mediaPlayer;


    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ApplicationController.stage = stage;
    }

    public static Pane getRoot() {
        return root;
    }

    public static void setRoot(Pane root) {
        ApplicationController.root = root;
    }

    public static void setRootSize(Pane pane) {
        pane.setPrefHeight(HEIGHT);
        pane.setPrefWidth(WIDTH);
    }

    public static void setIcon() {
        stage.getIcons().add(new Image(ApplicationController.class.getResourceAsStream("/someImages/icon.png")));
    }

    public static void setMedia(String mediaAddress) {
        if (ApplicationController.mediaPlayer == null) {
            Media media = new Media(ApplicationController.class.getResource(mediaAddress).toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            //todo 1
            mediaPlayer.setVolume(0.0);
            ApplicationController.mediaPlayer = mediaPlayer;
        }
    }

    public static BackgroundImage createBackGroundImage(String imageAddress, double height, double width) {
        Image image = new Image(RegisterMenu.class.getResource(imageAddress).toExternalForm(), width, height, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    public static void alert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }

    public static void alert2(ArrayList<Object> objects) {
        System.out.println(objects.get(0));
        System.out.println(objects.get(1));
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText((String) objects.get(1));
            alert.setContentText((String) objects.get(0));
            alert.show();
        });

    }


//server call this method to set all users of client like its own
    public static void receiveUsersOfServerSent(ArrayList<Object> objects) {
        User.getAllUsers().clear();
        Gson gson = new Gson();
        int i = 0;
        for (Object object : objects) {
            User user = gson.fromJson(gson.toJson(objects.get(i)), User.class);
            if (user != null) {
                User.getAllUsers().add(user);
            }
            i++;
        }
        User.setLoggedUser((User.getUserByName(User.getLoggedUser().getUsername())));
    }

    public static void alert2(String a) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(a);
        alert.setContentText(a);
        alert.show();
    }

    public static void setEnable(Pane root) {
        for (Node node : root.getChildren()) {
            node.setDisable(false);
        }
    }

    public static void setDisable(Pane root) {
        for (Node node : root.getChildren()) {
if (node.getId()==null||!node.getId().equals("chat"))
            node.setDisable(true);
        }
    }

//    public static void saveTheUsersInGson(ArrayList<User> users) {
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(users);
//        try (PrintWriter pw = new PrintWriter("users.json")) {
//            pw.write(json);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        ApplicationController.scene = scene;
    }

}
