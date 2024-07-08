package View;

import Model.*;
import Model.chat.Message;
import com.google.gson.Gson;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableNumberValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import webConnection.Client;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    public Label yourTurnLabel;
    public VBox emoji;
    public Label lostConnectionLabel;
    public Label timeLabel;
    @FXML
    private ImageView activeLeader;
    public Label passed;
    public Label passedOpponent;
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
    private Timeline lostConnectionTimeLine;
    private int time = 120;

    public ArrayList<HBox> hBoxes = new ArrayList<>();
    public static Chat chat;
    private static GameMenu gameMenu;

    public static GameMenu getGameMenu() {
        return gameMenu;
    }


    @Override
    public void start(Stage stage) throws Exception {

        ApplicationController.setStage(stage);
        ApplicationController.setMedia("/music/along-the-wayside-medieval-folk-music-128697.mp3");
        URL url = PreGameMenu.class.getResource("/FXML/GameMenu.fxml");
        Pane root = new Pane();
        root = FXMLLoader.load(url);
        ApplicationController.setRoot(root);
        ApplicationController.setRootSize(root);
        Scale scale = new Scale();
        scale.xProperty().bind(Bindings.divide(ApplicationController.getRoot().widthProperty(), 1540));
        scale.yProperty().bind(Bindings.divide(ApplicationController.getRoot().heightProperty(), 890));
        ApplicationController.getRoot().getTransforms().add(scale);
        Scene scene = new Scene(ApplicationController.getRoot());
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
        stage.setScene(scene);
        ApplicationController.setStage(stage);
        stage.setOnCloseRequest(windowEvent -> {
            Client.getConnection().doInServer("GameController","lostConnection",User.getLoggedUser());
            System.exit(0);
        });
        stage.show();
        stage.setHeight(740);
        stage.setWidth(1280);
        stage.setHeight(741);
        stage.centerOnScreen();

    }

    @FXML
    public void initialize() {
        //chat
        //todo real name
        chat = new Chat(User.getLoggedUser().getUsername());
        chat.setvBox(new VBox());
        chat.getvBox().setPrefWidth(chatScroll.getPrefWidth() - 30);
        chat.getvBox().getChildren().add(new Label("salam"));
        chat.getvBox().setSpacing(10);
        chatScroll.setContent(chat.getvBox());
        chatPane.setVisible(false);
        chatButton.setOnMouseClicked(mouseEvent -> {
            if (!chatPane.isVisible()) {
                chatPane.setVisible(true);
                chatButton.setLayoutX(chatButton.getLayoutX() - 235);
            } else {
                chatPane.setVisible(false);
                chatButton.setLayoutX(chatButton.getLayoutX() + 235);
            }
        });
        sendButton.setOnMouseClicked(mouseEvent -> {
            Message message = new Message(sendField.getText(), chat.getName(), Chat.getTime());
            ArrayList<Object> objects = new ArrayList<>();
            objects.add(message);
            objects.add(User.getLoggedUser().getUsername());
            chat.getMessages().clear();
            Client.getConnection().doInServer("ChatController", "getMessages", message, User.getLoggedUser().getUsername());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(chat.getMessages().size());
            chat.getvBox().getChildren().clear();
            for (Message message1 : chat.getMessages()) {
                chat.getvBox().getChildren().add(message1.toVBox());
            }
            sendField.setText("");
        });
        //chat
        //emoji
        for (Node node : emoji.getChildren()) {
            node.setOnMouseClicked(mouseEvent -> {
                GameMenu.showEmoji(node,450);
                Client.getConnection().doInServer("GameController","setEmoji", node.getId());
            });
        }
        //emoji
        ApplicationController.setRoot(pane);
        pane.getChildren().remove(cheatText);
        pane.getChildren().remove(cheatLabel);
        pane.getChildren().remove(passed);
        pane.getChildren().remove(passedOpponent);
        pane.getChildren().remove(turnLabel);
        pane.getChildren().remove(yourTurnLabel);
        pane.getChildren().remove(timeLabel);
        pane.getChildren().remove(lostConnectionLabel);
        turnLabel.setId("no");
        yourTurnLabel.setId("no");
        setHboxes();
        GameMenu.gameMenu = this;
        for (Card card : User.getLoggedUser().getDeck()) {
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
        }
        if (User.getLoggedUser().getBoard().getHand().isEmpty()) setRandomHand();
        System.out.println(User.getLoggedUser().getOpponentUser().getUsername());
        setImagesOfBoard();
        setHighScoreIcon();
        updateCardEvent();
        placeCard();
        startCheatMenu();
    }
    public static synchronized   void giveEmojiId(ArrayList<Object> objects ){
        Platform.runLater(()->{
            if (ApplicationController.getRoot()==null)
                return;
            for (Node node: ApplicationController.getRoot().getChildren()){
            if (node.getId()!=null&&node.getId().equals("emojiVbox")){
                for (Node node1: ((VBox)node).getChildren()){
                    if (node1.getId().equals(objects.get(0))) {
                        showEmoji(node1,200);
                        break;
                    }
                }
                break;
            }
        }
        });
    }

    public static synchronized void showEmoji(Node node, int height) {
        ImageView imageView = new ImageView(((ImageView) node).getImage());
        imageView.setX(735);
        imageView.setY(height);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        if (!ApplicationController.getRoot().getChildren().contains(imageView))
            ApplicationController.getRoot().getChildren().add(imageView);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), imageView);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setOnFinished(actionEvent -> ApplicationController.getRoot().getChildren().remove(imageView));
        fadeTransition.play();
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
                int num = User.getLoggedUser().getDeck().size() - 1;
                if (num < 1)
                    break;
                User.getLoggedUser().getBoard().getHand().add(User.getLoggedUser().getDeck().get(num));
                User.getLoggedUser().getDeck().remove(num);
                break;
            case "2":
                AbilityActions.medic(User.getLoggedUser());
                break;
            case "3":
                User.getLoggedUser().setFullHealth(true);
                break;
            case "4":
                User.getLoggedUser().getLeader().setUsed(false);
                break;
            case "5":
                User.getLoggedUser().getBoard().getHand().add(Card.giveCardByName2("Commander’shorn"));
                break;
            case "6":
                showOpponentCards(User.getLoggedUser().getOpponentUser().getBoard().getHand());
                break;
            case "7":
                showOpponentCards(User.getLoggedUser().getOpponentUser().getDeck());

                break;
        }
        cheatText.setText("");
        setOnClickBoard();
        setImagesOfBoard();

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
                if (!User.getLoggedUser().getBoard().isHasPlayedOne() || User.getLoggedUser().getOpponentUser().isPassed()) {
                    if (placeCard(hBox, highScoreImage)) {
                        updateCardEvent();
                        if (User.getLoggedUser().getOpponentUser().isPassed()) {
                            Object[] objects = new Object[1];
                            objects[0] = User.getLoggedUser();
                            User.getLoggedUser().hashMapMaker();
                            Client.getConnection().doInServer("GameController", "update", objects);
                            return;
                        }
                        User.getLoggedUser().getBoard().setHasPlayedOne(true);
                        for (Card card : User.getLoggedUser().getBoard().getHand()) {
                            card.setOnMouseClicked(null);
                        }
                        nextTurn.setOnMouseClicked(event2 -> {
                            if (User.getLoggedUser().getBoard().isHasPlayedOne()) {
                                if (!User.getLoggedUser().getOpponentUser().isPassed()) {
                                    changeTurn(turnLabel);
                                    ApplicationController.setDisable(ApplicationController.getRoot());
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public void passTurn() throws IOException {
        if (User.getLoggedUser().getBoard().isHasPlayedOne())
            return;
        if (!User.getLoggedUser().getOpponentUser().isPassed()) {
            User.getLoggedUser().setPassed(true);
            pass();
            ApplicationController.getRoot().getChildren().add(passed);
            ApplicationController.setDisable(ApplicationController.getRoot());
            ApplicationController.setEnable(ApplicationController.getRoot());
        } else {
            User.getLoggedUser().getOpponentUser().setPassed(false);
            User.getLoggedUser().setPassed(false);
            for (Card card : User.getLoggedUser().getBoard().getHand()) {
                card.setOnMouseClicked(null);
            }
            nextRound(highScoreImage);
        }
    }

    public void pass() {
        User.getLoggedUser().hashMapMaker();
        Object[] objects = new Object[1];
        objects[0] = User.getLoggedUser();
        Client.getConnection().doInServer("GameController", "pass", objects);
    }

    public void setImagesOfBoard() {
        if (ApplicationController.getRoot().getChildren().contains(passed))
            ApplicationController.getRoot().getChildren().remove(passed);
        if (ApplicationController.getRoot().getChildren().contains(passedOpponent))
            ApplicationController.getRoot().getChildren().remove(passedOpponent);
        if (User.getLoggedUser().isFirstTurn()) {
            vetoCard();
            return;
        }
        updateBorder();
        ApplicationController.getRoot().getChildren().removeIf(node -> (node instanceof Label) && ((!Objects.equals(node.getId(), "no")) || (!Objects.equals(node.getId(), "no"))));
        setImageOfDeckBack(User.getLoggedUser(), 686);
        setImageOfDeckBack(User.getLoggedUser().getOpponentUser(), 60);
        setImageCartNumber(420, 50, getTotalHboxPower(hBoxes.get(5)));
        setImageCartNumber(420, 157, getTotalHboxPower(hBoxes.get(4)));
        setImageCartNumber(420, 272, getTotalHboxPower(hBoxes.get(3)));
        setImageCartNumber(420, 391, getTotalHboxPower(hBoxes.get(2)));
        setImageCartNumber(420, 500, getTotalHboxPower(hBoxes.get(1)));
        setImageCartNumber(420, 612, getTotalHboxPower(hBoxes.get(0)));
        setImageCartNumber(358, 260, getTotalHboxPower(hBoxes.get(5)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(3)));
        setImageCartNumber(358, 590, getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0)));
        setProfileImages(User.getLoggedUser().getOpponentUser(), 200);
        setProfileImages(User.getLoggedUser(), 520);
        setHighScoreIcon();
        if (!ApplicationController.getRoot().getChildren().contains(passed) &&
                User.getLoggedUser().isPassed()) ApplicationController.getRoot().getChildren().add(passed);
        if (!ApplicationController.getRoot().getChildren().contains(passedOpponent) &&
                User.getLoggedUser().getOpponentUser().isPassed())
            ApplicationController.getRoot().getChildren().add(passedOpponent);
        deckCardNumber(User.getLoggedUser().getOpponentUser(), 295);
        deckCardNumber(User.getLoggedUser(), 615);
        setLeaderImage(User.getLoggedUser(), 687);
        setLeaderImage(User.getLoggedUser().getOpponentUser(), 64);
    }

    private void vetoCard() {
        for (Node node : ApplicationController.getRoot().getChildren()) {
            node.setDisable(true);
        }
        Button button = new Button("SKIP");
        button.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 36px;");
        button.setAlignment(Pos.CENTER);
        button.setLayoutY(600);
        button.setLayoutX((1520 - button.getWidth()) / 2);
        ApplicationController.getRoot().getChildren().add(button);
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.setMaxWidth(1540);
        hbox.setLayoutY(300);
        hbox.setLayoutX(20);
        for (Card card : User.getLoggedUser().getBoard().getHand()) {
            ImageView imageView = new ImageView(card.getImage());
            imageView.setFitWidth(140);
            imageView.setPreserveRatio(true);
            hbox.getChildren().add(imageView);
            imageView.setOnMouseClicked(mouseEvent -> {
                Random random = new Random();
                Card newCard = User.getLoggedUser().getDeck().get(random.nextInt(0, User.getLoggedUser().getDeck().size()));
                User.getLoggedUser().getDeck().remove(newCard);
                User.getLoggedUser().getBoard().getHand().add(newCard);
                User.getLoggedUser().getBoard().getHand().remove(card);
                User.getLoggedUser().getDeck().add(card);
                hbox.getChildren().add(newCard);
                hbox.getChildren().clear();
                secondVeto(hbox, button);
            });
        }
        button.setOnMouseClicked(mouseEvent -> {
            ApplicationController.getRoot().getChildren().remove(hbox);
            for (Node node1 : ApplicationController.getRoot().getChildren()) {
                node1.setDisable(false);
            }
            User.getLoggedUser().setFirstTurn(false);
            setImagesOfBoard();
            ApplicationController.getRoot().getChildren().remove(button);
        });
        ApplicationController.getRoot().getChildren().add(hbox);
    }

    private void secondVeto(HBox hbox, Button button) {
        for (Card card : User.getLoggedUser().getBoard().getHand()) {
            ImageView imageView = new ImageView(card.getImage());
            hbox.getChildren().add(imageView);
            imageView.setFitWidth(140);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(mouseEvent -> {
                Random random = new Random();
                Card newCard = User.getLoggedUser().getDeck().get(random.nextInt(0, User.getLoggedUser().getDeck().size()));
                User.getLoggedUser().getDeck().remove(newCard);
                User.getLoggedUser().getBoard().getHand().add(newCard);
                User.getLoggedUser().getBoard().getHand().remove(card);
                User.getLoggedUser().getDeck().add(card);
                ApplicationController.getRoot().getChildren().removeAll(hbox, button);
                for (Node node1 : ApplicationController.getRoot().getChildren()) {
                    node1.setDisable(false);
                }
                User.getLoggedUser().setFirstTurn(false);
                setImagesOfBoard();
                updateCardEvent();
            });
        }
    }

    private void setLeaderImage(User user, int height) {
        ImageView leader = new ImageView();
        leader.setImage(new Image(String.valueOf(GameMenu.class.getResource("/Leaders/" + user.getLeader().getName() + ".jpg"))));
        if (user.equals(User.getLoggedUser())) {
            leader.setOnMouseClicked(mouseEvent -> {
                user.getLeader().action(User.getLoggedUser());
                user.getLeader().setUsed(true);
                updateBorder();
                GaussianBlur gaussianBlur = new GaussianBlur(10);
                leader.setEffect(gaussianBlur);
            });
        }
        ApplicationController.getRoot().getChildren().add(leader);
        if (user.getLeader().isUsed()) {
            GaussianBlur gaussianBlur = new GaussianBlur(10);
            leader.setEffect(gaussianBlur);
        }
        leader.setY(height);
        leader.setX(113);
        leader.setFitHeight(115);
        leader.setFitWidth(80);
    }

    public void setActiveLeader(User turnUser) {
        ImageView activeLeader;
        activeLeader = new ImageView(new Image(GameMenu.class.getResourceAsStream("/someImages/icon_leader_active.png")));
        ApplicationController.getRoot().getChildren().add(activeLeader);
        activeLeader.setX(207);
        activeLeader.setY(108);
    }

    private void deckCardNumber(User loggedUser, int height) {
        Label labelUsername = new Label(String.valueOf(loggedUser.getBoard().getHand().size()));
        labelUsername.setId("deckNumber");
        labelUsername.setLayoutX(190);
        labelStyle(height, labelUsername);
    }

    private void setHighScoreIcon() {
        if (!ApplicationController.getRoot().getChildren().contains(highScoreImage))
            ApplicationController.getRoot().getChildren().add(highScoreImage);
        highScoreImage.setLayoutX(319);
        if (getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0)) > getTotalHboxPower(hBoxes.get(5)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(3)))
            highScoreImage.setLayoutY(582);
        else
            highScoreImage.setLayoutY(245);
        highScoreImage.setFitWidth(87);
        highScoreImage.setFitHeight(63);
        if (getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0)) == getTotalHboxPower(hBoxes.get(5)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(3))) {
            ApplicationController.getRoot().getChildren().remove(highScoreImage);
        }
    }

    private void setProfileImages(User user, int height) {
        ImageView imageView = new ImageView(new Image(GameMenu.class.getResourceAsStream("/someImages/profile.png")));
        ApplicationController.getRoot().getChildren().add(imageView);
        imageView.setX(90);
        imageView.setY(height);
        imageView.setFitWidth(130);
        imageView.setFitHeight(130);

        ImageView imageViewShield = new ImageView(new Image(GameMenu.class.getResourceAsStream("/someImages/deck_shield_" + user.getFaction().getName() + ".png")));
        ApplicationController.getRoot().getChildren().add(imageViewShield);
        imageViewShield.setX(90);
        imageViewShield.setY(height + 15);
        imageViewShield.setFitWidth(30);
        imageViewShield.setFitHeight(30);
        ImageView imageViewHealth;
        if (user.isFullHealth())
            imageViewHealth = new ImageView(new Image(GameMenu.class.getResourceAsStream("/someImages/icon_gem_on.png")));
        else
            imageViewHealth = new ImageView(new Image(GameMenu.class.getResourceAsStream("/someImages/icon_gem_off.png")));

        ApplicationController.getRoot().getChildren().add(imageViewHealth);
        imageViewHealth.setX(250);
        imageViewHealth.setY(height + 45);
        imageViewHealth.setFitWidth(60);
        imageViewHealth.setFitHeight(40);

        // put name of User.getLoggedUser()
        Label labelPlayer = new Label(user.getFaction().getName());
        labelPlayer.setId("usernameLable");
        labelPlayer.setLayoutX(200);
        labelStyle(height + 20, labelPlayer);
        Label labelUsername = new Label(user.getUsername());
        labelUsername.setId("playerId");
        labelUsername.setLayoutX(200);
        labelStyle(height, labelUsername);
    }

    private void labelStyle(int height, Label label) {
        label.setLayoutY(height);
        label.getStyleClass().add("label-style");
        ApplicationController.getRoot().getChildren().add(label);
        ApplicationController.getStage().getScene().getStylesheets().add(GameMenu.class.getResource("/someImages/label.css").toExternalForm());
        label.setPrefHeight(15);
        label.setMinWidth(20);
        label.setTextAlignment(TextAlignment.CENTER);
    }

    private void setImageCartNumber(int width, int height, int number) {
        Label label = new Label(String.valueOf(number));
        label.setId("labelNumber");
        label.setLayoutX(width);
        labelStyle(height, label);

    }

    private void setImageOfDeckBack(User user, int height) {
        ImageView imageView = new ImageView(new Image(GameMenu.class.getResource("/someImages/deck_back_" + user.getFaction().getName() + ".jpg").toExternalForm()));
        ApplicationController.getRoot().getChildren().add(imageView);
        imageView.setX(1387);
        imageView.setY(height);
        imageView.setFitWidth(80);
        imageView.setFitHeight(110);
        Label label = new Label();
        label.setId("label");
        label.setLayoutX(1418);
        label.setLayoutY(height + 90);
        label.getStyleClass().add("label-style");
        ApplicationController.getRoot().getChildren().add(label);
        ApplicationController.getStage().getScene().getStylesheets().add(GameMenu.class.getResource("/someImages/label.css").toExternalForm());
        label.setText(String.valueOf(user.getDeck().size()));
        label.setPrefHeight(15);
        label.setMinWidth(20);
    }

    public void putCardInDeck(Card card, ArrayList<Card> hand) {
        if (!card.isSelect()) {
            boolean isAnySelected = false;
            for (Card card1 : hand) {
                if (card1.isSelect()) {
                    isAnySelected = true;
                    break;
                }
            }
            if (!isAnySelected) {
                if (card.getType().equals("spell") && !card.getName().equals("Decoy")) {
                    if (hBoxes.get(7).getChildren().isEmpty())
                        hBoxes.get(7).setStyle("-fx-background-color: rgba(252,237,6,0.13);");
                    if (hBoxes.get(8).getChildren().isEmpty())
                        hBoxes.get(8).setStyle("-fx-background-color: rgba(252,237,6,0.13);");
                    if (hBoxes.get(9).getChildren().isEmpty())
                        hBoxes.get(9).setStyle("-fx-background-color: rgba(252,237,6,0.13);");
                    setSizeBigger(card);
                } else {
                    if (getHbox(card) >= 100)
                        hBoxes.get(getHbox(card) / 100).setStyle("-fx-background-color: rgba(252,237,6,0.13);");
                    setSizeBigger(card);
                    if (card.getName().equals("Decoy")) {
                        SpecialAction.decoy(card, User.getLoggedUser());
                        return;
                    }
                    hBoxes.get(getHbox(card) % 100).setStyle("-fx-background-color: rgba(252,237,6,0.13);");
                }
            }
        } else {
            if (card.isSelect()) {
                for (HBox hBox : hBoxes)
                    hBox.setStyle(null);
                if (card.getName().equals("Decoy")) {
                    updateCardEvent();
                }
                setSizeSmaller(card);
            }
        }

    }

    private void setSizeBigger(Card card) {
        card.setPrefWidth(card.getWidth() * 1.5);
        card.setPrefHeight(card.getHeight() * 1.5);
        ((Rectangle) card.getChildren().get(0)).setHeight(((Rectangle) card.getChildren().get(0)).getHeight() * 1.5);
        ((Rectangle) card.getChildren().get(0)).setWidth(((Rectangle) card.getChildren().get(0)).getWidth() * 1.5);
        card.setSelect(true);
    }

    public void setBurntCard() {
        if (!User.getLoggedUser().getBoard().getBurnedCard().isEmpty()) {
            turnBurnt.setImage(User.getLoggedUser().getBoard().getBurnedCard().getLast().getGameImage());
            turnBurnt.setOnMouseClicked(mouseEvent -> {
                showBurned(User.getLoggedUser());
            });
        } else turnBurnt.setImage(null);
        if (!User.getLoggedUser().getOpponentUser().getBoard().getBurnedCard().isEmpty()) {
            opponentBurnt.setImage(User.getLoggedUser().getOpponentUser().getBoard().getBurnedCard().getLast().getGameImage());
            opponentBurnt.setOnMouseClicked(mouseEvent -> {
                showBurned(User.getLoggedUser().getOpponentUser());
            });
        } else opponentBurnt.setImage(null);
    }

    private void showBurned(User turnUser) {
        HBox hBox = new HBox(5);
        hBox.setLayoutY(200);
        hBox.setLayoutX(10);
        hBox.setPrefWidth(1530);
        hBox.setAlignment(Pos.TOP_CENTER);
        for (Card card : turnUser.getBoard().getBurnedCard()) {
            ImageView imageView = new ImageView(card.getImage());
            imageView.setFitWidth((double) 1540 / (turnUser.getBoard().getBurnedCard().size() + 2));
            if (imageView.getFitWidth() > 300) imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
            hBox.getChildren().add(imageView);
        }
        ApplicationController.setDisable(ApplicationController.getRoot());
        ApplicationController.getRoot().getChildren().add(hBox);
        ApplicationController.getRoot().setOnMouseClicked(mouseEvent -> {
            ApplicationController.getRoot().setOnMouseClicked(mouseEvent1 -> {
                ApplicationController.getRoot().getChildren().remove(hBox);
                ApplicationController.setEnable(ApplicationController.getRoot());
                ApplicationController.getRoot().setOnMouseClicked(null);
            });
        });

    }

    public void setRandomHand() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(0, User.getLoggedUser().getDeck().size());
            User.getLoggedUser().getBoard().getHand().add(User.getLoggedUser().getDeck().get(num));
            User.getLoggedUser().getDeck().remove(num);
        }

    }

    private int getHbox(Card card) {
        if (card.getAbility() != null && card.getAbility().contains("spy")) {
            return switch (card.getType()) {
                case "closeCombatUnit" -> 3;
                case "rangedUnit" -> 4;
                case "siegeUnit" -> 5;
                case "agileUnit" -> 304;
                default -> 6;
            };
        }
        return switch (card.getType()) {
            case "closeCombatUnit" -> 2;
            case "rangedUnit" -> 1;
            case "siegeUnit" -> 0;
            case "weather" -> 6;
            case "agileUnit" -> 102;
            case "spell" -> 9;
            default -> 4;
        };

    }

    private int getTotalHboxPower(HBox hBox) {
        int total = 0;
        for (int i = 0; i < hBox.getChildren().size(); i++) {
            Card card = (Card) hBox.getChildren().get(i);
            Label label = (Label) card.getChildren().get(1);
            total += Integer.parseInt(label.getText());
        }
        return total;
    }

    public void changeTurn(Label turnLabel) {
        if (!ApplicationController.getRoot().getChildren().contains(turnLabel))
            ApplicationController.getRoot().getChildren().add(turnLabel);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), turnLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
        Timeline waitForChange = new Timeline(new KeyFrame(Duration.seconds(1.8), actionEvent -> {
            Object[] objects = new Object[1];
            User.getLoggedUser().hashMapMaker();
            objects[0] = User.getLoggedUser();
            Client.getConnection().doInServer("GameController", "nextTurn", objects);
        }));
        waitForChange.setCycleCount(1);
        waitForChange.play();
    }

    private void ending(User winner) {
        if (User.getLoggedUser().getActiveGame().getThirdRoundPointMe() < 0) {
            User.getLoggedUser().getActiveGame().setThirdRoundPointMe(0);
            User.getLoggedUser().getActiveGame().setThirdRoundPointOpponent(0);
        }
        if (winner == null) User.getLoggedUser().getActiveGame().setWinner(null);
        else User.getLoggedUser().getActiveGame().setWinner(winner.getUsername());
        User.getLoggedUser().getActiveGame().countTotalPoints();
        Client.getConnection().doInServer("GameController", "endGame",
                User.getLoggedUser(), User.getLoggedUser().getActiveGame());
    }

    public static void endShower(ArrayList<Object> objects) {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        GameHistory gameHistory = gson.fromJson(gson.toJson(objects.get(1)), GameHistory.class);
        ApplicationController.setEnable(ApplicationController.getRoot());
        User.getLoggedUser().setActiveGame(gameHistory);
        Platform.runLater(() -> {
            try {
                if (User.getLoggedUser().getActiveGame().getWinner() != null)
                    gameMenu.endGame(User.getLoggedUser().getActiveGame().getWinner());
                else
                    gameMenu.endGame(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void endGame(String winner) throws IOException {
        Label winnerLabel;
        if (winner == null) {
            winnerLabel = new Label("Draw");
            winnerLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #0472ef;");
        } else {
            winnerLabel = new Label("Winner " + winner);
            winnerLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #0e5201;");
        }
        ApplicationController.getRoot().getChildren().clear();
        ImageView imageView = new ImageView(new Image(String.valueOf(GameMenu.class.getResource("/backgrounds/Gwent_1.jpg"))));
        imageView.setFitWidth(1540);
        imageView.setFitHeight(890);
        ImageView state;
        if (winner == null) {
            state = new ImageView(new Image(String.valueOf(GameMenu.class.getResource("/someImages/Draw.png"))));
        } else if (winner.equals(User.getLoggedUser().getUsername())) {
            state = new ImageView(new Image(String.valueOf(GameMenu.class.getResource("/someImages/Win.png"))));
        } else {
            state = new ImageView(new Image(String.valueOf(GameMenu.class.getResource("/someImages/Lose.png"))));
        }
        state.setFitWidth(1540);
        state.setFitHeight(890);
        ApplicationController.getRoot().getChildren().add(imageView);
        ApplicationController.getRoot().getChildren().add(state);
        VBox vBox = new VBox(20);
        vBox.setPrefSize(800, 800);
        vBox.setLayoutX(400);
        vBox.setLayoutY(50);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().add(winnerLabel);
        Label firstRound = new Label("First Round:");
        firstRound.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #fb03f9;");
        Label secondRound = new Label("Second Round:");
        secondRound.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #09daf1;");
        Label thirdRound = new Label("Third Round:");
        thirdRound.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #ff001e");
        Label total = new Label("Total Points:");
        total.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: rgb(0,0,0)");
        Label firstResult = new Label(User.getLoggedUser().getUsername() + "  " + User.getLoggedUser().getActiveGame().getFirstRoundPointMe());
        Label secondResult = new Label(User.getLoggedUser().getUsername() + "  " + User.getLoggedUser().getActiveGame().getSecondRoundPointMe());
        Label thirdResult = new Label(User.getLoggedUser().getUsername() + "  " + User.getLoggedUser().getActiveGame().getThirdRoundPointMe());
        Label totalPoint = new Label(User.getLoggedUser().getUsername() + "  " + User.getLoggedUser().getActiveGame().getTotalPointsMe());
        Label firstResult2 = new Label(User.getLoggedUser().getOpponentUser().getUsername() + "  " +
                User.getLoggedUser().getActiveGame().getFirstRoundPointOpponent());
        Label secondResult2 = new Label(User.getLoggedUser().getOpponentUser().getUsername() + "  " +
                User.getLoggedUser().getActiveGame().getSecondRoundPointOpponent());
        Label thirdResult2 = new Label(User.getLoggedUser().getOpponentUser().getUsername() + "  " +
                User.getLoggedUser().getActiveGame().getThirdRoundPointOpponent());
        Label totalPoint2 = new Label(User.getLoggedUser().getOpponentUser().getUsername() + "  " +
                User.getLoggedUser().getActiveGame().getTotalPointsOpponent());
        Label[] labels = {firstResult, secondResult, thirdResult, totalPoint, firstResult2, secondResult2, thirdResult2, totalPoint2};
        for (Label label : labels) {
            label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white");
        }
        Button button = getButton();
        vBox.getChildren().addAll(firstRound, firstResult, firstResult2);
        vBox.getChildren().addAll(secondRound, secondResult, secondResult2);
        vBox.getChildren().addAll(thirdRound, thirdResult, thirdResult2);
        vBox.getChildren().addAll(total, totalPoint, totalPoint2);
        vBox.getChildren().add(button);
        User.getLoggedUser().setOpponentUser(null);
        User.getLoggedUser().setOppName(null);
        ApplicationController.getRoot().getChildren().add(vBox);
    }

    private Button getButton() {
        Button button = new Button("Back to Main Menu");
        button.setStyle("-fx-background-color: #f6a107; -fx-font-size: 20px; -fx-font-weight: bold;");
        button.setPrefWidth(250);
        button.setPrefHeight(70);
        button.setOnMouseClicked(mouseEvent -> {
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return button;
    }

    public boolean placeCard(HBox hBox, ImageView highScoreIcon) {
        for (Iterator<Card> cardIterator = User.getLoggedUser().getBoard().getHand().iterator(); cardIterator.hasNext(); ) {
            Card card = cardIterator.next();
            ArrayList<Card> targetArray = getArrayPlace(hBox);
            if (card.isSelect() && !hBox.getStyle().isEmpty()) {
                User.getLoggedUser().getBoard().getHand().remove(card);
                targetArray.add(card);
                card.setOnMouseClicked(null);
                hBox.setStyle(null);
                setSizeSmaller(card);
                for (HBox hBox1 : hBoxes)
                    hBox1.setStyle(null);
                if (card.getAbility() != null && card.getAbility().equals("mardoeme"))
                    targetArray = getArrayMardoem(hBox);
                AbilityActions.switchAction(card, targetArray, User.getLoggedUser());
                if (card.getAbility() == null || !card.getAbility().equals("medic")) {
                    updateCardEvent();
                    updateBorder();
                    setImagesOfBoard();
                }

                return true;
            }

        }
        return false;
    }

    private ArrayList<Card> getArrayMardoem(HBox hBox) {
        if (hBoxes.get(7).equals(hBox))
            return User.getLoggedUser().getBoard().getSiege();
        else if (hBoxes.get(8).equals(hBox))
            return User.getLoggedUser().getBoard().getRanged();
        else if ((hBoxes.get(9).equals(hBox)))
            return User.getLoggedUser().getBoard().getCloseCombat();
        else return null;
    }

    private ArrayList<Card> getArrayPlace(HBox hBox) {
        if (hBoxes.get(0).equals(hBox)) {
            return User.getLoggedUser().getBoard().getSiege();
        } else if (hBoxes.get(1).equals(hBox)) {
            return User.getLoggedUser().getBoard().getRanged();
        } else if (hBoxes.get(2).equals(hBox)) {
            return User.getLoggedUser().getBoard().getCloseCombat();
        } else if (hBoxes.get(5).equals(hBox)) {
            return User.getLoggedUser().getOpponentUser().getBoard().getSiege();
        } else if (hBoxes.get(4).equals(hBox)) {
            return User.getLoggedUser().getOpponentUser().getBoard().getRanged();
        } else if (hBoxes.get(3).equals(hBox)) {
            return User.getLoggedUser().getOpponentUser().getBoard().getCloseCombat();
        } else if (hBoxes.get(9).equals(hBox)) {
            return User.getLoggedUser().getBoard().getCloseNext();
        } else if (hBoxes.get(8).equals(hBox)) {
            return User.getLoggedUser().getBoard().getRangedNext();
        } else if (hBoxes.get(7).equals(hBox)) {
            return User.getLoggedUser().getBoard().getSiegeNext();
        } else if (hBoxes.get(12).equals(hBox)) {
            return User.getLoggedUser().getOpponentUser().getBoard().getCloseNext();
        } else if (hBoxes.get(11).equals(hBox)) {
            return User.getLoggedUser().getOpponentUser().getBoard().getRangedNext();
        } else if (hBoxes.get(10).equals(hBox)) {
            return User.getLoggedUser().getOpponentUser().getBoard().getSiegeNext();
        } else if (hBoxes.get(6).equals(hBox)) {
            return User.getLoggedUser().getBoard().getWeather();
        }
        return null;
    }

    public void setSizeSmaller(Card card) {
        card.setPrefWidth(card.getWidth() / 1.5);
        card.setPrefHeight(card.getHeight() / 1.5);
        ((Rectangle) card.getChildren().get(0)).setHeight(((Rectangle) card.getChildren().get(0)).getHeight() / 1.5);
        ((Rectangle) card.getChildren().get(0)).setWidth(((Rectangle) card.getChildren().get(0)).getWidth() / 1.5);
        card.setSelect(false);
    }

    public void nextRound(ImageView highScoreImage) throws IOException {
        updateBorder();
        User.getLoggedUser().getBoard().setHasPlayedOne(false);
        User.getLoggedUser().getOpponentUser().getBoard().setHasPlayedOne(false);
        Card monster1 = null;
        Card monster2 = null;
        int totalPoints1 = getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0));
        int totalPoints2 = getTotalHboxPower(hBoxes.get(3)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(5));
        calculatePoints(User.getLoggedUser(), totalPoints1, totalPoints2);
        User.getLoggedUser().getOpponentUser().mergeActiveGame(User.getLoggedUser());
        if (totalPoints1 > totalPoints2) {
            if (User.getLoggedUser().getOpponentUser().isFullHealth()) {
                User.getLoggedUser().getOpponentUser().setFullHealth(false);
                if (User.getLoggedUser().getFaction().getName().equals("NorthernRealms")) {
                    northernRealms(User.getLoggedUser());
                }
            } else {
                ending(User.getLoggedUser());
                return;
            }
        } else if (totalPoints1 < totalPoints2) {
            if (User.getLoggedUser().isFullHealth()) {
                if (User.getLoggedUser().getOpponentUser().getFaction().getName().equals("NorthernRealms")) {
                    northernRealms(User.getLoggedUser().getOpponentUser());
                }
                User.getLoggedUser().setFullHealth(false);
            } else {
                ending(User.getLoggedUser().getOpponentUser());
                return;
            }
        } else {
            if (User.getLoggedUser().getFaction().getName().equals("Nilfgaard") &&
                    !User.getLoggedUser().getOpponentUser().getFaction().getName().equals("Nilfgaard")) {
                if (User.getLoggedUser().getOpponentUser().isFullHealth())
                    User.getLoggedUser().getOpponentUser().setFullHealth(false);
                else {
                    ending(User.getLoggedUser());
                    return;
                }
            } else if (User.getLoggedUser().getOpponentUser().getFaction().getName().equals("Nilfgaard") &&
                    !User.getLoggedUser().getFaction().getName().equals("Nilfgaard")) {
                if (User.getLoggedUser().isFullHealth())
                    User.getLoggedUser().setFullHealth(false);
                else {
                    ending(User.getLoggedUser().getOpponentUser());
                    return;
                }

            } else {
                if (!User.getLoggedUser().isFullHealth() && !User.getLoggedUser().getOpponentUser().isFullHealth()) {
                    ending(null);
                    return;
                } else if (User.getLoggedUser().isFullHealth() && User.getLoggedUser().getOpponentUser().isFullHealth()) {
                    User.getLoggedUser().getOpponentUser().setFullHealth(false);
                    User.getLoggedUser().setFullHealth(false);
                    System.out.println("here");
                } else if (User.getLoggedUser().isFullHealth()) {
                    ending(User.getLoggedUser());
                    return;
                } else {
                    ending(User.getLoggedUser().getOpponentUser());
                    return;
                }
            }
        }
        if (User.getLoggedUser().getActiveGame().getThirdRoundPointMe() > 0) {
            ending(null);
            return;
        }
        System.out.println(User.getLoggedUser().isFullHealth());
        System.out.println(User.getLoggedUser().getOpponentUser().isFullHealth());
        if (User.getLoggedUser().getFaction().getName().equals("Monsters")) {
            monster1 = monstersAction(hBoxes.subList(0, 3));
        }
        if (User.getLoggedUser().getOpponentUser().getFaction().getName().equals("Monsters")) {
            monster2 = monstersAction(hBoxes.subList(3, 6));
        }
        putInBurntCards(User.getLoggedUser());
        putInBurntCards(User.getLoggedUser().getOpponentUser());
        ArrayList<Card> transform1 = AbilityActions.transformers(User.getLoggedUser());
        ArrayList<Card> transform2 = AbilityActions.transformers(User.getLoggedUser().getOpponentUser());
        for (HBox hBox : hBoxes) {
            ArrayList<Card> target = getArrayPlace(hBox);
            target.clear();
        }
        if (User.getLoggedUser().getFaction().getName().equals("Skellige")) {
            skelligeAction(User.getLoggedUser());
        }
        if (User.getLoggedUser().getOpponentUser().getFaction().getName().equals("Skellige")) {
            skelligeAction(User.getLoggedUser().getOpponentUser());
        }
        // ali hard code
        User.getLoggedUser().getOpponentUser().getBoard().getWeather().clear();
        if (monster1 != null) {
            if (monster1.getType().equals("siegeUnit")) {
                User.getLoggedUser().getBoard().getSiege().add(monster1);
            } else if (monster1.getType().equals("rangedUnit")) {
                User.getLoggedUser().getBoard().getRanged().add(monster1);
            } else {
                User.getLoggedUser().getBoard().getCloseCombat().add(monster1);
            }
        }
        if (monster2 != null) {
            if (monster2.getType().equals("siegeUnit")) {
                User.getLoggedUser().getOpponentUser().getBoard().getSiege().add(monster2);
            } else if (monster2.getType().equals("rangedUnit")) {
                User.getLoggedUser().getOpponentUser().getBoard().getRanged().add(monster2);
            } else {
                User.getLoggedUser().getOpponentUser().getBoard().getCloseCombat().add(monster2);
            }
        }
        if (!transform2.isEmpty()) {
            for (Card card : transform2) {
                if (card.getType().equals("rangedUnit"))
                    User.getLoggedUser().getOpponentUser().getBoard().getRanged().add(card);
                else
                    User.getLoggedUser().getOpponentUser().getBoard().getCloseCombat().add(card);
            }

        }
        if (!transform1.isEmpty()) {
            for (Card card : transform1) {
                if (card.getType().equals("rangedUnit"))
                    User.getLoggedUser().getBoard().getRanged().add(card);
                else
                    User.getLoggedUser().getBoard().getCloseCombat().add(card);
            }
        }
        User.getLoggedUser().getBoard().leaderBoost = new boolean[]{false, false, false, false, false};
        User.getLoggedUser().getOpponentUser().getBoard().leaderBoost = new boolean[]{false, false, false, false, false};
        User.getLoggedUser().hashMapMaker();
        setImagesOfBoard();
        Client.getConnection().doInServer("GameController", "goToNextRound",
                User.getLoggedUser(), User.getLoggedUser().getActiveGame());
    }

    private void skelligeAction(User user) {
        if (user.getActiveGame().getSecondRoundPointMe() > -0.9) {
            ArrayList<Card> nonSpellCards = new ArrayList<>();
            for (Card card : user.getBoard().getBurnedCard()) {
                if (card.getType().equals("weather") || card.getType().equals("spell")) continue;
                nonSpellCards.add(card);
            }

            while (nonSpellCards.size() > 2) {
                Random random = new Random();
                nonSpellCards.remove(nonSpellCards.get(random.nextInt(0, nonSpellCards.size())));
            }
            for (Card card : nonSpellCards) {
                switch (card.getType()) {
                    case "closeCombatUnit", "agileUnit" -> {
                        user.getBoard().getCloseCombat().add(card);
                        user.getBoard().getBurnedCard().remove(card);
                    }
                    case "rangedUnit" -> {
                        user.getBoard().getRanged().add(card);
                        user.getBoard().getBurnedCard().remove(card);
                    }
                    case "siegeUnit" -> {
                        user.getBoard().getSiege().add(card);
                        user.getBoard().getBurnedCard().remove(card);
                    }
                }
            }
        }
    }

    private Card monstersAction(List<HBox> hBoxes) {
        ArrayList<Card> allCards = new ArrayList<>();
        for (Node node : hBoxes.get(0).getChildren()) {
            Card card = (Card) node;
            if (card.getType().equals("special") || card.getType().equals("weather")) {
                continue;
            }
            allCards.add(card);
        }
        for (Node node : hBoxes.get(1).getChildren()) {
            Card card = (Card) node;
            if (card.getType().equals("special") || card.getType().equals("weather")) {
                continue;
            }
            allCards.add(card);
        }
        for (Node node : hBoxes.get(2).getChildren()) {
            Card card = (Card) node;
            if (card.getType().equals("spell") || card.getType().equals("weather"))
                continue;
            allCards.add(card);
        }
        Random random = new Random();
        if (!allCards.isEmpty())
            return allCards.get(random.nextInt(0, allCards.size()));
        else
            return null;
    }

    private void calculatePoints(User user, int userPoints, int opponentPoints) {
        if (user.getActiveGame().getFirstRoundPointMe() < 0) {
            user.getActiveGame().setFirstRoundPointMe(userPoints);
            user.getActiveGame().setFirstRoundPointOpponent(opponentPoints);
        } else if (user.getActiveGame().getSecondRoundPointMe() < 0) {
            user.getActiveGame().setSecondRoundPointMe(userPoints);
            user.getActiveGame().setSecondRoundPointOpponent(opponentPoints);
        } else {
            user.getActiveGame().setThirdRoundPointMe(userPoints);
            user.getActiveGame().setThirdRoundPointOpponent(opponentPoints);
        }
    }

    private void northernRealms(User user) {
        Random random = new Random();
        int rand = random.nextInt(0, user.getDeck().size());
        Card card = user.getDeck().get(rand);
        user.getDeck().remove(card);
        user.getBoard().getHand().add(card);
    }

    private void putInBurntCards(User user) {
        if (user.getBoard().getSiege() != null)
            user.getBoard().getBurnedCard().addAll(user.getBoard().getSiege());
        if (user.getBoard().getRanged() != null)
            user.getBoard().getBurnedCard().addAll(user.getBoard().getRanged());
        if (user.getBoard().getCloseCombat() != null)
            user.getBoard().getBurnedCard().addAll(user.getBoard().getCloseCombat());
        if (user.getBoard().getCloseNext() != null)
            user.getBoard().getBurnedCard().addAll(user.getBoard().getCloseNext());
        if (user.getBoard().getRangedNext() != null)
            user.getBoard().getBurnedCard().addAll(user.getBoard().getRangedNext());
        if (user.getBoard().getSiegeNext() != null)
            user.getBoard().getBurnedCard().addAll(user.getBoard().getSiegeNext());
        if (user.getBoard().getWeather() != null)
            user.getBoard().getBurnedCard().addAll(user.getBoard().getWeather());
    }

    public void updateBorder() {
        User user1 = User.getLoggedUser();
        User user2 = User.getLoggedUser().getOpponentUser();
        for (HBox hBox : hBoxes) {
            hBox.getChildren().clear();
        }
        deckHbox.getChildren().clear();
        for (Card card : user1.getBoard().getSiege()) {
            hBoxes.get(0).getChildren().add(card);
        }
        for (Card card : user1.getBoard().getRanged()) {
            hBoxes.get(1).getChildren().add(card);
        }
        for (Card card : user1.getBoard().getCloseCombat()) {
            hBoxes.get(2).getChildren().add(card);
        }
        for (Card card : user2.getBoard().getSiege()) {
            hBoxes.get(5).getChildren().add(card);
        }
        for (Card card : user2.getBoard().getRanged()) {
            hBoxes.get(4).getChildren().add(card);
        }
        for (Card card : user2.getBoard().getCloseCombat()) {
            hBoxes.get(3).getChildren().add(card);
        }
        for (Card card : user1.getBoard().getHand()) {
            deckHbox.getChildren().add(card);
        }
        for (Card card : user1.getBoard().getWeather()) {
            hBoxes.get(6).getChildren().add(card);
        }
        for (Card card : user2.getBoard().getWeather()) {
            hBoxes.get(6).getChildren().add(card);
        }
        for (Card card : user1.getBoard().getSiegeNext()) {
            hBoxes.get(7).getChildren().add(card);
        }
        for (Card card : user1.getBoard().getRangedNext()) {
            hBoxes.get(8).getChildren().add(card);
        }
        for (Card card : user1.getBoard().getCloseNext()) {
            hBoxes.get(9).getChildren().add(card);
        }
        for (Card card : user2.getBoard().getCloseNext()) {
            hBoxes.get(10).getChildren().add(card);
        }
        for (Card card : user2.getBoard().getRangedNext()) {
            hBoxes.get(11).getChildren().add(card);
        }
        for (Card card : user2.getBoard().getSiegeNext()) {
            hBoxes.get(12).getChildren().add(card);
        }
        setLabels(User.getLoggedUser());
        setLabels(User.getLoggedUser().getOpponentUser());
        setHighScoreIcon();
        setBurntCard();
    }

    public void updateCardEvent() {
        ApplicationController.getRoot().getChildren().remove(turnLabel);
        ApplicationController.getRoot().getChildren().remove(yourTurnLabel);
        ArrayList<Card> hand = User.getLoggedUser().getBoard().getHand();
        if (deckHbox == null) deckHbox = new HBox();
        deckHbox.getChildren().clear();
        for (Card card : hand) {
            deckHbox.getChildren().add(card);
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
            card.setOnMouseClicked(event -> {
                putCardInDeck(card, hand);
            });
        }
        for (Card card : User.getLoggedUser().getDeck()) {
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
            card.setOnMouseClicked(event -> {
                putCardInDeck(card, hand);
            });

        }
        setOnClickBoard();
    }

    public void setLabels(User user) {
        for (Card card : user.getBoard().getHand()) {
            Label label = (Label) card.getChildren().get(1);
            label.setText(String.valueOf(card.getPower()));
        }
        for (Card card : user.getBoard().getSiege()) {
            Label label = (Label) card.getChildren().get(1);
            if (card.getAbility() != null && card.getAbility().contains("hero")) {
                label.setText(String.valueOf(card.getPower()));
            } else {
                int number = card.getPower();
                if (!SpecialAction.clearWeather(user))
                    number = SpecialAction.torrentialRain(number, user);
                if (User.getLoggedUser().getBoard().leaderBoost[0]) {
                    number *= 2;
                } else {
                    ArrayList<Card> fullLine = new ArrayList<>();
                    fullLine.addAll(user.getBoard().getSiege());
                    fullLine.addAll(user.getBoard().getSiegeNext());
                    number = SpecialAction.commanderHorn(fullLine, card, number);
                }
                number = AbilityActions.tightBond(card, user.getBoard().getSiege(), number);
                number = SpecialAction.moralBoost(user.getBoard().getSiege(), card, number);
                label.setText(String.valueOf(number));
            }
        }
        for (Card card : user.getBoard().getRanged()) {
            Label label = (Label) card.getChildren().get(1);
            if (card.getAbility() != null && card.getAbility().contains("hero")) {
                label.setText(String.valueOf(card.getPower()));
            } else {
                int number = card.getPower();
                if (!SpecialAction.clearWeather(user))
                    number = SpecialAction.impenetrableFog(number, user);
                if (user.getBoard().leaderBoost[1]) {
                    number *= 2;
                } else {
                    ArrayList<Card> fullLine = new ArrayList<>();
                    fullLine.addAll(user.getBoard().getRanged());
                    fullLine.addAll(user.getBoard().getRangedNext());
                    number = SpecialAction.commanderHorn(fullLine, card, number);
                }
                number = AbilityActions.tightBond(card, user.getBoard().getRanged(), number);
                number = SpecialAction.moralBoost(user.getBoard().getRanged(), card, number);
                label.setText(String.valueOf(number));
            }
        }
        for (Card card : user.getBoard().getCloseCombat()) {
            Label label = (Label) card.getChildren().get(1);
            if (card.getAbility() != null && card.getAbility().contains("hero")) {
                label.setText(String.valueOf(card.getPower()));
            } else {
                int number = card.getPower();
                if (!SpecialAction.clearWeather(user))
                    number = SpecialAction.bitingFrost(number, User.getLoggedUser());
                if (user.getBoard().leaderBoost[2]) {
                    number *= 2;
                } else {
                    ArrayList<Card> fullLine = new ArrayList<>();
                    fullLine.addAll(user.getBoard().getCloseCombat());
                    fullLine.addAll(user.getBoard().getCloseNext());
                    number = SpecialAction.commanderHorn(fullLine, card, number);
                }
                number = AbilityActions.tightBond(card, user.getBoard().getCloseCombat(), number);
                number = SpecialAction.moralBoost(user.getBoard().getCloseCombat(), card, number);
                label.setText(String.valueOf(number));
            }
        }
    }

    public void setOnClickBoard() {
        for (Card card : User.getLoggedUser().getBoard().getSiege()) {
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
        }
        for (Card card : User.getLoggedUser().getBoard().getRanged()) {
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
        }
        for (Card card : User.getLoggedUser().getBoard().getCloseCombat()) {
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
        }
    }

    public void showOpponentCards(ArrayList<Card> cards) {
        ApplicationController.setDisable(ApplicationController.getRoot());
        HBox hBox = new HBox();
        hBox.setLayoutX(300);
        hBox.setLayoutY(400);
        hBox.setPrefHeight(300);
        hBox.setPrefWidth(1000);
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.CENTER);
        ApplicationController.getRoot().getChildren().add(hBox);
        for (Card card : cards) {
            if (!hBox.getChildren().contains(card))
                hBox.getChildren().add(card);
            ApplicationController.getRoot().setOnMouseClicked(mouseEvent -> {
                card.setSelect(false);
                ApplicationController.getRoot().getChildren().remove(hBox);
                card.setOnMouseClicked(null);
                ApplicationController.setEnable(ApplicationController.getRoot());
            });
        }
    }

    public static void getTurn(ArrayList<Object> objects) {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        GameHistory gameHistory = gson.fromJson(gson.toJson(objects.get(1)), GameHistory.class);
        User.getLoggedUser().setActiveGame(gameHistory);
        User.getLoggedUser().setCards(temp.getCards());
        System.out.println(User.getLoggedUser().getOpponentUser().getUsername());
        User.getLoggedUser().boardMaker();
        ApplicationController.setEnable(ApplicationController.getRoot());
        Platform.runLater(() -> {
            System.out.println(User.getLoggedUser().getOpponentUser().getUsername());
            gameMenu.setImagesOfBoard();
            gameMenu.updateCardEvent();
            gameMenu.yourTurn();
        });
    }

    private void yourTurn() {
        if (!ApplicationController.getRoot().getChildren().contains(yourTurnLabel)) {
            ApplicationController.getRoot().getChildren().add(yourTurnLabel);
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.2), actionEvent -> {
            ApplicationController.getRoot().getChildren().remove(yourTurnLabel);
        }));
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), yourTurnLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
        timeline.setCycleCount(1);
        timeline.play();
    }

    public static void waitForNextRound(ArrayList<Object> objects) {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User.getLoggedUser().setCards(temp.getCards());
        User.getLoggedUser().boardMaker();
        ApplicationController.setEnable(ApplicationController.getRoot());
        Platform.runLater(() -> {
            gameMenu.setImagesOfBoard();
        });
    }

    public static void startRound(ArrayList<Object> objects) {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        GameHistory gameHistory = gson.fromJson(gson.toJson(objects.get(1)), GameHistory.class);
        User.getLoggedUser().setActiveGame(gameHistory);
        User.getLoggedUser().setCards(temp.getCards());
        User.getLoggedUser().boardMaker();
        ApplicationController.setEnable(ApplicationController.getRoot());
        Platform.runLater(() -> {
            gameMenu.setImagesOfBoard();
            gameMenu.updateCardEvent();

        });
    }

    public static void opponentLostConnection (ArrayList<Object> objects) {
        Platform.runLater(() ->{
            ApplicationController.setDisable(ApplicationController.getRoot());
            gameMenu.opponentLost();
        });
    }

    private void opponentLost() {
        ApplicationController.getRoot().getChildren().add(timeLabel);
        ApplicationController.getRoot().getChildren().add(lostConnectionLabel);
        time = 120;
        lostConnectionTimeLine = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            time--;
            timeLabel.setText(String.valueOf(time) + " SECONDS");
            if (time < 1) {
                int totalPoints1 = getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0));
                int totalPoints2 = getTotalHboxPower(hBoxes.get(3)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(5));
                calculatePoints(User.getLoggedUser(), totalPoints1, totalPoints2);
                Client.getConnection().doInServer("GameController","endWithLostConnection",User.getLoggedUser(),User.getLoggedUser().getActiveGame());
                lostConnectionTimeLine.stop();
            }
        }));
        lostConnectionTimeLine.setCycleCount(120);
        lostConnectionTimeLine.play();
    }

    public static void opponentBackTurn (ArrayList<Object> objects) {
        Platform.runLater(() -> {
            User.getLoggedUser().boardMaker();
            ApplicationController.setEnable(ApplicationController.getRoot());
            ApplicationController.getRoot().getChildren().remove(gameMenu.timeLabel);
            ApplicationController.getRoot().getChildren().remove(gameMenu.lostConnectionLabel);
            gameMenu.lostConnectionTimeLine.stop();
            gameMenu.setImagesOfBoard();
            gameMenu.updateCardEvent();
            gameMenu.yourTurn();
        });
    }

    public static void opponentBack (ArrayList<Object> objects) {
        Platform.runLater(() -> {
            ApplicationController.getRoot().getChildren().remove(gameMenu.timeLabel);
            ApplicationController.getRoot().getChildren().remove(gameMenu.lostConnectionLabel);
            gameMenu.lostConnectionTimeLine.stop();
        });
    }
}
