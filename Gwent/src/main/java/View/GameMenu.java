package View;

import Controller.ApplicationController;
import Controller.GameController;
import Model.Board;
import Model.Card;
import Model.GameHistory;
import Model.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;

public class GameMenu extends Application {
    @FXML
    private ImageView activeLeader;
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
    public HBox spellHbox;
    public ImageView highScoreImage;
    public Label turnLabel;

    public ArrayList<HBox> hBoxes = new ArrayList<>();
    // I add this GameHistory(Hamid)
    private GameHistory gameHistory;


    @Override
    public void start(Stage stage) throws Exception {

        User.getTurnUser().setBoard(new Board());
        User.getTurnUser().getOpponentUser().setBoard(new Board());
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
        ApplicationController.setGameMenu(this);
        GameController.setActiveLeader(User.getTurnUser());

    }

    @FXML
    public void initialize() {
        //todo remove
//        Card card=User.getTurnUser().getDeck().getFirst();
//        card.setLayoutX(1000);
//        card.setLayoutY(500);
//        pane.getChildren().add(card);
        //todo remove
        pane.getChildren().remove(passedLabel);
        pane.getChildren().remove(turnLabel);
        passedLabel.setId("no");
        turnLabel.setId("no");
        setHboxes();
        ApplicationController.setRoot(pane);
        GameController.setImagesOfBoard(User.getTurnUser(), hBoxes, highScoreImage);
        GameController.setRandomHand(User.getTurnUser());
        GameController.setRandomHand(User.getTurnUser().getOpponentUser());
        putCardInDeck();
        placeCard();
    }

    private void setHboxes() {
        hBoxes.add(turnSiege);
        hBoxes.add(turnRanged);
        hBoxes.add(turnCombat);
        hBoxes.add(opponentCombat);
        hBoxes.add(opponentRanged);
        hBoxes.add(opponentSiege);
        hBoxes.add(spellHbox);
        User.getTurnUser().getBoard().setSiege(turnSiege);
        User.getTurnUser().getBoard().setRanged(turnRanged);
        User.getTurnUser().getBoard().setCloseCombat(turnCombat);
        User.getTurnUser().getOpponentUser().getBoard().setSiege(opponentSiege);
        User.getTurnUser().getOpponentUser().getBoard().setRanged(opponentRanged);
        User.getTurnUser().getOpponentUser().getBoard().setCloseCombat(opponentCombat);
    }

    public void putCardInDeck() {
        ApplicationController.getRoot().getChildren().remove(turnLabel);
        ArrayList<Card> hand = User.getTurnUser().getBoard().getHand();
        for (Card card : User.getTurnUser().getBoard().getHand()) {
            deckHbox.getChildren().add(card);
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
            card.setOnMouseClicked(event -> {
                GameController.putCardInDeck(hBoxes, deckHbox, card, hand);
            });

        }
    }


    public void placeCard() {
        for (HBox hBox : hBoxes) {
            hBox.setOnMouseClicked(event -> {

                if (!User.getTurnUser().getOpponentUser().isPassed()&& GameController.placeCard(hBoxes, deckHbox, hBox, highScoreImage)) {
                    GameController.changeTurn(deckHbox, hBoxes, highScoreImage, turnLabel);
                    Timeline waitForChangeTurn = new Timeline(new KeyFrame(Duration.seconds(2),actionEvent->putCardInDeck()));
                    waitForChangeTurn.setCycleCount(1);
                    waitForChangeTurn.play();

                }
            });
        }
    }


    public void passTurn() {
        if (!User.getTurnUser().getOpponentUser().isPassed()) {
            User.getTurnUser().setPassed(true);
            GameController.changeTurn(deckHbox, hBoxes, highScoreImage, turnLabel);
            ApplicationController.getRoot().getChildren().add(passedLabel);
            Timeline waitForChangeTurn = new Timeline(new KeyFrame(Duration.seconds(2)));
            waitForChangeTurn.setCycleCount(1);
            waitForChangeTurn.play();

            if (waitForChangeTurn.getCurrentRate() == 0)
                putCardInDeck();
        } else {
            ApplicationController.getRoot().getChildren().remove(passedLabel);
            User.getTurnUser().getOpponentUser().setPassed(false);
            User.getTurnUser().setPassed(false);
            GameController.nextRound(hBoxes, highScoreImage);
        }
    }

    public GameHistory getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(GameHistory gameHistory) {
        this.gameHistory = gameHistory;
    }
}
