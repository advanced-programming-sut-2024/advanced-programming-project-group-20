package View;

import Controller.ApplicationController;
import Controller.GameController;
import Model.Board;
import Model.Card;
import Model.CardBuilder;
import Model.Factions.Monsters;
import Model.Factions.Nilfgaard;
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
        User player;
        User opponent;
        User.setLoggedUser(player = new User("ali", "123", "reza", "a.@1"));
        player.setOpponentUser(opponent = new User("ali", "123", "reza", "a.@1"));
        User.setTurnUser(player);
        player.setFaction(new Nilfgaard());
        opponent.setFaction(new Monsters());
        player.setBoard(new Board());
        opponent.setBoard(new Board());

        ApplicationController.setStage(stage);
        ApplicationController.setIcon();
       ApplicationController.setMedia("/music/along-the-wayside-medieval-folk-music-128697.mp3");
        URL url = PreGameMenu.class.getResource("/FXML/GameMenu.fxml");
        Pane root = FXMLLoader.load(url);
        ApplicationController.setRoot(root);
        ApplicationController.setRootSize(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ApplicationController.setStage(stage);
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
        hBoxes.add(opponentRanged);
        hBoxes.add(opponentSiege);
        GameController.putCardInDeck(pane,hBoxes,passedLabel,biggerCardImage,deckHbox);
        GameController.placeCard(pane,hBoxes,deckHbox);


    }

    public void passTurn(MouseEvent mouseEvent) {
        ApplicationController.getRoot().getChildren().add(passedLabel);
    }
}
