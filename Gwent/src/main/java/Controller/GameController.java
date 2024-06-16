package Controller;

import Model.Board;
import Model.Card;
import Model.Factions.Monsters;
import Model.Factions.Nilfgaard;
import Model.User;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class GameController {


    public static void initializeFromMenu() {

        setImagesOfBoard(User.getLoggedUser());

    }


    private static void setImagesOfBoard(User user) {
        // image and lable of deck back
        setImageOfDeckBack(user, 686);
        setImageOfDeckBack(user.getOpponentUser(), 60);
        // set number of card in each row
        setImageCartNumber(user, 420, 50, 10);
        setImageCartNumber(user, 420, 157, 11);
        setImageCartNumber(user, 420, 272, 1);
        setImageCartNumber(user.getOpponentUser(), 420, 391, 1);
        setImageCartNumber(user.getOpponentUser(), 420, 500, 1);
        setImageCartNumber(user.getOpponentUser(), 420, 612, 1);
        setImageCartNumber(user, 358, 260, 1);
        setImageCartNumber(user.getOpponentUser(), 358, 590, 1);
        setProfileImages(user, 200, 5);
        setProfileImages(user.getOpponentUser(), 520, 5);
        setHighScoreIcon(User.getLoggedUser());
        deckCardNumber(User.getLoggedUser());
        deckCardNumber(User.getLoggedUser());
    }

    public static void setActiveLeader(User turnUser) {
        ImageView activeLeader = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/icon_leader_active.png")));
        ApplicationController.getRoot().getChildren().add(activeLeader);
        // todo if for avtive
        activeLeader.setX(207);
        if (0 == 1)
            activeLeader.setY(108);
        else
            activeLeader.setY(727);


    }

    private static void deckCardNumber(User loggedUser) {
        Label labelUsername = new Label("15");
        labelUsername.setId("deckNumber");
        labelUsername.setLayoutX(190);
        labelStyle(295, labelUsername);
//        labelStyle(615, labelUsername);
    }

    private static void setHighScoreIcon(User loggedUser) {
        //todo if for high score
        ImageView imageView = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/icon_high_score.png")));
        ApplicationController.getRoot().getChildren().add(imageView);
        imageView.setX(323);
        // for every one
//        imageView.setY(255);
        imageView.setY(580);

        imageView.setFitWidth(80);
        imageView.setFitHeight(60);
    }

    private static void setProfileImages(User user, int height, int cartsNumber) {
        ImageView imageView = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/profile.png")));
        ApplicationController.getRoot().getChildren().add(imageView);
        imageView.setX(90);
        imageView.setY(height);
        imageView.setFitWidth(130);
        imageView.setFitHeight(130);

        ImageView imageViewShield = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/deck_shield_" + user.getFaction().getName() + ".png")));
        ApplicationController.getRoot().getChildren().add(imageViewShield);
        imageViewShield.setX(90);
        imageViewShield.setY(height + 15);
        imageViewShield.setFitWidth(30);
        imageViewShield.setFitHeight(30);

        ImageView imageViewHealth = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/icon_gem_on.png")));
        ApplicationController.getRoot().getChildren().add(imageViewHealth);
        imageViewHealth.setX(250);
        imageViewHealth.setY(height + 45);
        imageViewHealth.setFitWidth(60);
        imageViewHealth.setFitHeight(40);

        // put name of user
        Label labelPlayer = new Label("alireza");
        labelPlayer.setId("usernameLable");
        labelPlayer.setLayoutX(200);
        labelStyle(height + 20, labelPlayer);
        Label labelUsername = new Label("player");
        labelUsername.setId("playerId");
        labelUsername.setLayoutX(200);
        labelStyle(height, labelUsername);
    }

    private static void labelStyle(int height, Label label) {
        label.setLayoutY(height);
        label.getStyleClass().add("label-style");
        ApplicationController.getRoot().getChildren().add(label);
        ApplicationController.getStage().getScene().getStylesheets().add(GameController.class.getResource("/someImages/label.css").toExternalForm());
        label.setPrefHeight(15);
        label.setMinWidth(20);
        label.setTextAlignment(TextAlignment.CENTER);
    }


    private static void setImageCartNumber(User user, int width, int height, int number) {
        Label label = new Label(String.valueOf(number));
        label.setId("labelNumber");
        label.setLayoutX(width);
        labelStyle(height, label);

    }

    private static void setImageOfDeckBack(User user, int height) {
        ImageView imageView = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/deck_back_" + user.getFaction().getName() + ".jpg")));
        ApplicationController.getRoot().getChildren().add(imageView);
        imageView.setX(1387);
        imageView.setY(height);
        imageView.setFitWidth(80);
        imageView.setFitHeight(110);
        //todo number of lable
        Label label = new Label();
        label.setId("label");
        label.setLayoutX(1418);
        label.setLayoutY(height + 90);
        label.getStyleClass().add("label-style");
        ApplicationController.getRoot().getChildren().add(label);
        ApplicationController.getStage().getScene().getStylesheets().add(GameController.class.getResource("/someImages/label.css").toExternalForm());
        label.setText("10");
        label.setPrefHeight(15);
        label.setMinWidth(20);
    }

    public static void putCardInDeck(AnchorPane pane, ArrayList<HBox> hBoxes, Label passedLabel, ImageView biggerCardImage, HBox deckHbox) {
        pane.getChildren().remove(passedLabel);
        ArrayList<Card> hand = User.getTurnUser().getBoard().getHand();
        // todo get carda from deck not by code
        // to remove here
        ArrayList<Card> cards = new ArrayList<>();
        Nilfgaard nilfgaard = new Nilfgaard();
        cards.add(nilfgaard.getCollection().get(0));
        cards.add(nilfgaard.getCollection().get(1));
        cards.add(nilfgaard.getCollection().get(2));
        cards.add(nilfgaard.getCollection().get(3));
        cards.add(nilfgaard.getCollection().get(4));
        cards.add(nilfgaard.getCollection().get(5));
        cards.add(nilfgaard.getCollection().get(6));
        cards.add(nilfgaard.getCollection().get(7));
        cards.add(nilfgaard.getCollection().get(8));
        cards.add(nilfgaard.getCollection().get(9));
        for (int i = 0; i < 10; i++)
            hand.add(cards.get(i));
        // until here
        for (Card card : User.getTurnUser().getBoard().getHand()) {
            deckHbox.getChildren().add(card);
            showCardInfo(card,biggerCardImage);
            card.setOnMouseClicked(event -> {
                if (!card.isSelect() && card.isInDeck()) {
                    boolean isAnySelected = false;
                    for (Card card1 : hand){
                        if (card1.isSelect()) {
                            isAnySelected = true;
                            break;
                        }
                    }
                        if (!isAnySelected) {
                            hBoxes.get(getHbox(card.getType())).setStyle("-fx-background-color: rgba(252,237,6,0.13);");
                            deckHbox.setLayoutY(deckHbox.getLayoutY() - 20);
                            card.setWidth(card.getWidth() * 1.5);
                            card.setHeight(card.getHeight() * 1.5);
                            card.setSelect(true);
                        }
                } else {
                    if (card.isSelect()) {
                        hBoxes.get(getHbox(card.getType())).setStyle(null);
                        deckHbox.setLayoutY(deckHbox.getLayoutY() + 20);
                        card.setWidth(card.getWidth() / 1.5);
                        card.setHeight(card.getHeight() / 1.5);
                        card.setSelect(false);
                    }
                }
            });

        }

    }

    private static int getHbox(String type) {
        return switch (type) {
            case "closeCombatUnit" -> 2;
            case "rangedUnit" -> 1;
            case "siegeUnit" -> 0;
            default -> 4;
        };

    }

    public static String vetoCard(Card card) {

        return null;
    }

    public static void showCardInfo(Card card, ImageView biggerCardImage) {
        card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
        card.setOnMouseExited(event -> biggerCardImage.setImage(null));
    }

    public static void showRemainingCard(Card card) {

    }

    public static void showBurnedCard(Card card) {

    }

    public static String showRowCard(int number) {
        return null;
    }

    public static void showSpells() {

    }

    public static void placeCard(Card putCard, int rowNumber) {

    }

    public static void showLeader() {

    }

    public static void leaderAction() {

    }

    public static void showPlayersInfo() {

    }

    public static void showTurnInfo() {

    }

    public static void showTotalScore() {

    }

    public static void showTotalScoreOfRow(int rowNumber) {


    }

    public static void passTurn() {

    }

    public static boolean checkEnding() {
        return true;
    }

    private static void endGame() {

    }

    private static void killCard(Card card) {

    }

    public static void placeCard(AnchorPane pane, ArrayList<HBox> hBoxes,  HBox deckHbox) {
        for (HBox hBox : hBoxes) {
            hBox.setOnMouseClicked(event -> {
                for (Card card : User.getTurnUser().getBoard().getHand()) {
                    System.out.println(card.getName());
                    if (card.isSelect() && card.isInDeck()) {
                        deckHbox.getChildren().remove(card);
                        hBox.getChildren().add(card);
                        hBox.setStyle(null);
                        deckHbox.setLayoutY(deckHbox.getLayoutY() + 20);
                        card.setWidth(card.getWidth() / 1.5);
                        card.setHeight(card.getHeight() / 1.5);
                        card.setSelect(false);
                        card.setInDeck(false);
                    }
                }
            });
        }
    }

    ;

}
