package Controller;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import View.RegisterMenu;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class ApplicationController {
    private static Stage stage;
    private static Pane root;
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
            mediaPlayer.setVolume(0.5);
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

    public static void setEnable(Pane root) {
        for (Node node: root.getChildren()) {
            node.setDisable(false);
        }
    }
    public static void setDisable(Pane root) {
        for (Node node: root.getChildren()) {
            node.setDisable(true);
        }
    }
}
