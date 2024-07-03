package View;

import Controller.GameController;
import Model.*;
import Model.chat.Message;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import webConnection.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GameMenu extends Application {
    public Button nextTurn;
    public HBox turnSiegeNext;
    public HBox turnRangedNext;
    public HBox turnCloseNext;
    public HBox opponentCloseNext;
    public HBox opponentRangedNext;
    public HBox opponentSiegeNext;
    public TextField cheatText;
    public Label cheatLabel;
    public Button chatButton;
    public ScrollPane chatScroll;
    public Button sendButton;
    public TextField sendField;
    public AnchorPane chatPane;
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

    public ArrayList<HBox> hBoxes = new ArrayList<>();
    private GameHistory gameHistory;
    public static Chat chat;



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
        Scale scale = new Scale();
        scale.xProperty().bind(Bindings.divide(root.widthProperty(), 1540));
        scale.yProperty().bind(Bindings.divide(root.heightProperty(), 890));
        root.getTransforms().add(scale);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
        stage.setScene(scene);
        ApplicationController.setStage(stage);
        stage.show();
        stage.setHeight(740);
        stage.setWidth(1280);
        stage.setHeight(741);
        stage.centerOnScreen();
        GameController.setActiveLeader(User.getTurnUser());
    }

    @FXML
    public void initialize() {
        //chat
        //todo real name
        chat = new Chat("ali");
       chat.setvBox(new VBox());
        chat.getvBox().setPrefWidth(chatScroll.getPrefWidth()-30);
        chat.getvBox().getChildren().add(new Label("salam"));
        chat.getvBox().setSpacing(10);
        chatScroll.setContent(chat.getvBox());
        chatPane.setVisible(false);
        chatButton.setOnMouseClicked(mouseEvent -> {
            if (!chatPane.isVisible()) {
                chatPane.setVisible(true);
                chatButton.setLayoutX(chatButton.getLayoutX() - 235);
            }
            else {
                chatPane.setVisible(false);
                chatButton.setLayoutX(chatButton.getLayoutX() + 235);
            }
        });
        sendButton.setOnMouseClicked(mouseEvent -> {
            Message message =new Message(sendField.getText(), chat.getName(),Chat.getTime());
            ArrayList<Object> objects = new ArrayList<>();
            objects.add(message);
            //todo real username
            objects.add("ali");
            chat.getMessages().clear();
            Client.getConnection().doInServer("ChatController","getMessages",message,"ali");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
                System.out.println(chat.getMessages().size());
            chat.getvBox().getChildren().clear();
            for (Message message1 :chat.getMessages()){
            chat.getvBox().getChildren().add(message1.toVBox());
            }
            sendField.setText("");
        });
        //chat
        pane.getChildren().remove(cheatText);
        pane.getChildren().remove(cheatLabel);
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
        GameController.setBiggerCardImage(biggerCardImage);
        GameController.setTurnLabel(turnLabel);
        GameController.setTurnBurnt(turnBurnt);
        GameController.setOpponentBurnt(opponentBurnt);
        GameController.setRandomHand(User.getTurnUser());
        GameController.setRandomHand(User.getTurnUser().getOpponentUser());
        GameController.setImagesOfBoard(User.getTurnUser());
        GameController.updateCardEvent();
        placeCard();
        startCheatMenu();
    }

    private void startCheatMenu() {
        pane.setOnKeyPressed(event -> {
            if (event.isShiftDown() && event.getCode() == KeyCode.CAPS) {
                if (!ApplicationController.getRoot().getChildren().contains(cheatLabel)) {
                    GaussianBlur gaussianBlur = new GaussianBlur(10);
                    for (Node node : ApplicationController.getRoot().getChildren()) {
                        node.setEffect(gaussianBlur);
                    }
                    ApplicationController.getRoot().getChildren().add(cheatLabel);
                    ApplicationController.getRoot().getChildren().add(cheatText);
                }
                cheatText.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                        doCheat(cheatText.getText());
                        pane.getChildren().remove(cheatText);
                        pane.getChildren().remove(cheatLabel);
                        for (Node node : ApplicationController.getRoot().getChildren()) {
                            node.setEffect(null);
                        }
                    }
                });
            }
        });

    }

    public void doCheat(String text) {
        switch (text) {
            case "1":
                int num = User.getTurnUser().getDeck().size() - 1;
                if (num < 1)
                    break;
                User.getTurnUser().getBoard().getHand().add(User.getTurnUser().getDeck().get(num));
                User.getTurnUser().getDeck().remove(num);
                break;
            case "2":
                AbilityActions.medic();
                break;
            case "3":
                User.getTurnUser().setFullHealth(true);
                break;
            case "4":
                User.getTurnUser().getLeader().setUsed(false);
                break;
            case "5":
                User.getTurnUser().getOpponentUser().setPassed(true);
                if (!ApplicationController.getRoot().getChildren().contains(passedLabel))
                    ApplicationController.getRoot().getChildren().add(passedLabel);
                break;
            case "6":
                GameController.showOpponentCards(User.getTurnUser().getOpponentUser().getBoard().getHand());
                break;
            case "7":
                GameController.showOpponentCards(User.getTurnUser().getOpponentUser().getDeck());

                break;
        }
        cheatText.setText("");
        GameController.setOnClickBoard();
        GameController.setImagesOfBoard(User.getTurnUser());

    }
    private void setHboxes() {
        hBoxes.add(turnSiege);
        hBoxes.add(turnRanged);
        hBoxes.add(turnCombat);
        hBoxes.add(opponentCombat);
        hBoxes.add(opponentRanged);
        hBoxes.add(opponentSiege);
        hBoxes.add(spellHbox);
        hBoxes.add(turnSiegeNext);
        hBoxes.add(turnRangedNext);
        hBoxes.add(turnCloseNext);
        hBoxes.add(opponentCloseNext);
        hBoxes.add(opponentRangedNext);
        hBoxes.add(opponentSiegeNext);
    }

    public void placeCard() {
        for (HBox hBox : hBoxes) {
            hBox.setOnMouseClicked(event -> {
                if (!User.getTurnUser().getBoard().isHasPlayedOne()||User.getTurnUser().getOpponentUser().isPassed()) {
                    if (GameController.placeCard(hBox, highScoreImage)) {
                        GameController.updateCardEvent();
                        User.getTurnUser().getBoard().setHasPlayedOne(true);
                        for (Card card : User.getTurnUser().getBoard().getHand()) {
                            card.setOnMouseClicked(null);
                        }
                        nextTurn.setOnMouseClicked(event2 -> {
                            if (User.getTurnUser().getBoard().isHasPlayedOne()) {
                                if (!User.getTurnUser().getOpponentUser().isPassed())
                                    GameController.changeTurn(highScoreImage, turnLabel);
                                Timeline waitForChangeTurn = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> GameController.updateCardEvent()));
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


    public void passTurn() throws IOException {
        if (User.getTurnUser().getBoard().isHasPlayedOne())
            return;
        if (!User.getTurnUser().getOpponentUser().isPassed()) {
            GameController.updateCardEvent();
            User.getTurnUser().setPassed(true);
            GameController.changeTurn(highScoreImage, turnLabel);
            Timeline waitForChangeTurn = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> {
                GameController.updateCardEvent();
                ApplicationController.getRoot().getChildren().add(passedLabel);
            }));
            waitForChangeTurn.setCycleCount(1);
            waitForChangeTurn.play();
        } else {
            ApplicationController.getRoot().getChildren().remove(passedLabel);
            User.getTurnUser().getOpponentUser().setPassed(false);
            User.getTurnUser().setPassed(false);
            GameController.nextRound(highScoreImage);
            GameController.updateCardEvent();
        }
    }

    public GameHistory getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(GameHistory gameHistory) {
        this.gameHistory = gameHistory;
    }
}
