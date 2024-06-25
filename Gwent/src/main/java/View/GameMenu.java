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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class GameMenu extends Application {
    public Button nextTurn;
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
    public ImageView turnBurnt;
    public ImageView opponentBurnt;
    private final CountDownLatch latch = new CountDownLatch(1);

    public ArrayList<HBox> hBoxes = new ArrayList<>();
    // I add this GameHistory(Hamid)
    private GameHistory gameHistory;


    @Override
    public void start(Stage stage) throws Exception {

        User.getTurnUser().setBoard(new Board());
        User.getTurnUser().getOpponentUser().setBoard(new Board());
        ApplicationController.setStage(stage);
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
        System.out.println(turnSiege);
    }

    @FXML
    public void initialize() {
        pane.getChildren().remove(passedLabel);
        pane.getChildren().remove(turnLabel);
        passedLabel.setId("no");
        turnLabel.setId("no");
        setHboxes();
        GameController.sethBoxes(hBoxes);
        GameController.setDeckHbox(deckHbox);
        GameController.setHighScoreIcon(highScoreImage);
        ApplicationController.setRoot(pane);
        for (Card card: User.getLoggedUser().getDeck()){
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
        }
        GameController.setImagesOfBoard(User.getTurnUser());
        GameController.setRandomHand(User.getTurnUser());
        GameController.setRandomHand(User.getTurnUser().getOpponentUser());
        putCardInDeck();
        //ee
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

    }

    public void putCardInDeck() {
        ApplicationController.getRoot().getChildren().remove(turnLabel);
        ArrayList<Card> hand = User.getTurnUser().getBoard().getHand();
        deckHbox.getChildren().clear();
        for (Card card : hand) {
            deckHbox.getChildren().add(card);
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
            card.setOnMouseClicked(event -> {
//                eee
                GameController.putCardInDeck(card, hand);
            });

        }
        //ee
            GameController.setBurntCard(turnBurnt, opponentBurnt);
    }


    public void placeCard() {
        for (HBox hBox : hBoxes) {
            hBox.setOnMouseClicked(event -> {
                if (!User.getTurnUser().getBoard().isHasPlayedOne()||User.getTurnUser().getOpponentUser().isPassed()) {
                    if (GameController.placeCard(hBox, highScoreImage, latch)) {
                        putCardInDeck();
                        User.getTurnUser().getBoard().setHasPlayedOne(true);
                        for (Card card : User.getTurnUser().getBoard().getHand()) {
                            card.setOnMouseClicked(null);
                        }
                        nextTurn.setOnMouseClicked(event2 -> {
                            if (User.getTurnUser().getBoard().isHasPlayedOne()) {
                                if (!User.getTurnUser().getOpponentUser().isPassed())
                                    GameController.changeTurn(highScoreImage, turnLabel);
                                Timeline waitForChangeTurn = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> putCardInDeck()));
                                waitForChangeTurn.setCycleCount(1);
                                waitForChangeTurn.play();
                                GameController.updateBorder();
                                User.getTurnUser().getBoard().setHasPlayedOne(false);
                            }
                        });
                    }
                }
            });
        }
    }


    public void passTurn() {
        if (User.getTurnUser().getBoard().isHasPlayedOne())
            return;
        if (!User.getTurnUser().getOpponentUser().isPassed()) {
            putCardInDeck();
            User.getTurnUser().setPassed(true);
            GameController.changeTurn(highScoreImage, turnLabel);
            ApplicationController.getRoot().getChildren().add(passedLabel);
            Timeline waitForChangeTurn = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> putCardInDeck()));
            waitForChangeTurn.setCycleCount(1);
            waitForChangeTurn.play();

        } else {
            ApplicationController.getRoot().getChildren().remove(passedLabel);
            User.getTurnUser().getOpponentUser().setPassed(false);
            User.getTurnUser().setPassed(false);
            GameController.nextRound(highScoreImage);
            putCardInDeck();
        }
    }

    public GameHistory getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(GameHistory gameHistory) {
        this.gameHistory = gameHistory;
    }
}
