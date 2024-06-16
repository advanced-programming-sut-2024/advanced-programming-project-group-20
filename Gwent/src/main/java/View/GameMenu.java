package View;

import Controller.ApplicationController;
import Controller.GameController;
import Model.Card;
import Model.CardBuilder;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;

public class GameMenu extends Application {
    @FXML
    public ImageView activeLeader;
    public Label passedLabel;
    public AnchorPane pane;
    public HBox deckHbox;
    public ImageView biggerCardImage;
    public HBox turnRanged;
    public HBox turnSiege;
    public HBox opponentCombat;
    public HBox opponentSiege;
    public HBox opponentRanged;
    public HBox turnCombat;

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationController.setStage(stage);
        ApplicationController.setIcon();
       ApplicationController.setMedia("/music/along-the-wayside-medieval-folk-music-128697.mp3");
        URL url = PreGameMenu.class.getResource("/FXML/GameMenu.fxml");
        Pane root = FXMLLoader.load(url);
        ApplicationController.setRoot(root);
        ApplicationController.setRootSize(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

        // Set the stage in the center of the screen
         ApplicationController.getStage().setX((primScreenBounds.getWidth() -  ApplicationController.getStage().getWidth()) / 2);
         ApplicationController.getStage().setY((primScreenBounds.getHeight() -  ApplicationController.getStage().getHeight()) / 2);
        ApplicationController.setStage(stage);
//        root.setBackground(new Background(ApplicationController.setBackGround("/someImages/board.jpg")));
        stage.show();
        stage.centerOnScreen();
        stage.setHeight(900);
        stage.setWidth(1600);
        GameController.initializeFromMenu();
        GameController.setActiveLeader(User.getTurnUser());


    }
    @FXML
    public  void initialize(){
        ArrayList<HBox> hBoxes =new ArrayList<>();
        hBoxes.add(turnSiege);
        hBoxes.add(turnRanged);
        hBoxes.add(turnCombat);
        hBoxes.add(opponentCombat);
        hBoxes.add(opponentSiege);
        hBoxes.add(opponentRanged);
        GameController.putCardInDeck(pane,hBoxes,passedLabel,biggerCardImage,deckHbox);


    }

    public void passTurn(MouseEvent mouseEvent) {
        ApplicationController.getRoot().getChildren().add(passedLabel);
    }
}
