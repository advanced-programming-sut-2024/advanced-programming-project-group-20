package Controller;

import javafx.scene.layout.Pane;
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
}
