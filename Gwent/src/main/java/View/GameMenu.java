package View;

import Controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;

public class GameMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
       ApplicationController.setMedia("/music/along-the-wayside-medieval-folk-music-128697.mp3");
        URL url = PreGameMenu.class.getResource("/FXML/GameMenu.fxml");
        Pane root = FXMLLoader.load(url);
        ApplicationController.setRootSize(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ApplicationController.setStage(stage);
        root.setBackground(new Background(ApplicationController.setBackGround("/someImages/board.jpg")));
        stage.show();



    }
}
