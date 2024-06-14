package Controller;

import View.RegisterMenu;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ApplicationController {
   private static Stage stage ;
   public static final int WIDTH =1280;
   public static final int HEIGHT =720;


    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ApplicationController.stage = stage;
    }
     public static void setRootSize(Pane pane){
        pane.setPrefHeight(HEIGHT);
        pane.setPrefWidth(WIDTH);
     }
    public static BackgroundImage createBackGroundImage(String imageAddress, double height, double width) {
        //Todo hardcode
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
}
