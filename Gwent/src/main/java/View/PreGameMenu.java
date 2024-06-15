package View;

import Controller.ApplicationController;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class PreGameMenu extends Application {
    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setHeight(740);
        stage.setWidth(1280);
        URL url = PreGameMenu.class.getResource("/FXML/PreGameMenu.fxml");
        Pane root = FXMLLoader.load(url);
        ApplicationController.setRootSize(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
