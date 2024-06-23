package Controller;

import Model.Board;
import Model.Card;
import Model.User;
import View.RegisterMenu;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

public class GameController {


    public static void setImagesOfBoard(User user, ArrayList<HBox> hBoxes, ImageView highScoreIcon) {
        ApplicationController.getRoot().getChildren().removeIf(node -> (node instanceof Label) && ((!Objects.equals(node.getId(), "no")) || (!Objects.equals(node.getId(), "no"))));
        // image and lable of deck back
        setImageOfDeckBack(user, 686);
        setImageOfDeckBack(user.getOpponentUser(), 60);
        // set number of card in each row
        setImageCartNumber(user, 420, 50, getTotalHboxPower(hBoxes.get(5)));
        setImageCartNumber(user, 420, 157, getTotalHboxPower(hBoxes.get(4)));
        setImageCartNumber(user, 420, 272, getTotalHboxPower(hBoxes.get(3)));
        setImageCartNumber(user.getOpponentUser(), 420, 391, getTotalHboxPower(hBoxes.get(2)));
        setImageCartNumber(user.getOpponentUser(), 420, 500, getTotalHboxPower(hBoxes.get(1)));
        setImageCartNumber(user.getOpponentUser(), 420, 612, getTotalHboxPower(hBoxes.get(0)));
        setImageCartNumber(user, 358, 260, getTotalHboxPower(hBoxes.get(5)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(3)));
        setImageCartNumber(user.getOpponentUser(), 358, 590, getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0)));
        setProfileImages(user.getOpponentUser(), 200);
        setProfileImages(user, 520);
        setHighScoreIcon(hBoxes, highScoreIcon);
        deckCardNumber(User.getTurnUser().getOpponentUser(), 295);
        deckCardNumber(User.getTurnUser(), 615);
        setLeaderImage(User.getTurnUser(), 687);
        setLeaderImage(User.getTurnUser().getOpponentUser(), 64);
    }

    private static void setLeaderImage(User user, int height) {
        //  i dont have lider please make it
        ImageView leader = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/deck_back_" + user.getFaction().getName() + ".jpg")));
        ApplicationController.getRoot().getChildren().add(leader);
        leader.setY(height);
        leader.setX(113);
        leader.setFitHeight(115);
        leader.setFitWidth(80);
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

    private static void deckCardNumber(User loggedUser, int height) {
        Label labelUsername = new Label(String.valueOf(loggedUser.getBoard().getHand().size()));
        labelUsername.setId("deckNumber");
        labelUsername.setLayoutX(190);
        labelStyle(height, labelUsername);
    }

    private static void setHighScoreIcon(ArrayList<HBox> hBoxes, ImageView highScoreIcon) {
        if (!ApplicationController.getRoot().getChildren().contains(highScoreIcon))
            ApplicationController.getRoot().getChildren().add(highScoreIcon);
        highScoreIcon.setLayoutX(319);
        if (getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0)) > getTotalHboxPower(hBoxes.get(5)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(3)))
            highScoreIcon.setLayoutY(582);
        else
            highScoreIcon.setLayoutY(245);
        highScoreIcon.setFitWidth(87);
        highScoreIcon.setFitHeight(63);
        if (getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0)) == getTotalHboxPower(hBoxes.get(5)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(3))) {
            ApplicationController.getRoot().getChildren().remove(highScoreIcon);

        }
    }

    private static void setProfileImages(User user, int height) {
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
        ImageView imageViewHealth;
        if (user.isFullHealth())
            imageViewHealth = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/icon_gem_on.png")));
        else
            imageViewHealth = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/icon_gem_off.png")));

        ApplicationController.getRoot().getChildren().add(imageViewHealth);
        imageViewHealth.setX(250);
        imageViewHealth.setY(height + 45);
        imageViewHealth.setFitWidth(60);
        imageViewHealth.setFitHeight(40);

        // put name of user
        Label labelPlayer = new Label(user.getFaction().getName());
        labelPlayer.setId("usernameLable");
        labelPlayer.setLayoutX(200);
        labelStyle(height + 20, labelPlayer);
        Label labelUsername = new Label(user.getUsername());
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
        label.setText(String.valueOf(user.getDeck().size()));
        label.setPrefHeight(15);
        label.setMinWidth(20);
    }


    public static void putCardInDeck(ArrayList<HBox> hBoxes, HBox deckHbox, Card card, ArrayList<Card> hand) {
        if (!card.isSelect() && card.isInDeck()) {
            boolean isAnySelected = false;
            for (Card card1 : hand) {
                if (card1.isSelect()) {
                    isAnySelected = true;
                    break;
                }
            }
            if (!isAnySelected) {
                if (getHbox(card) >= 10)
                    hBoxes.get(getHbox(card) / 10).setStyle("-fx-background-color: rgba(252,237,6,0.13);");

                hBoxes.get(getHbox(card) % 10).setStyle("-fx-background-color: rgba(252,237,6,0.13);");
                deckHbox.setLayoutY(deckHbox.getLayoutY() - 20);
                card.setWidth(card.getWidth() * 1.5);
                card.setHeight(card.getHeight() * 1.5);
                card.setSelect(true);
            }
        } else {
            if (card.isSelect()) {
                if (getHbox(card) >= 10)
                    hBoxes.get(getHbox(card) / 10).setStyle(null);
                hBoxes.get(getHbox(card) % 10).setStyle(null);
                deckHbox.setLayoutY(deckHbox.getLayoutY() + 20);
                card.setWidth(card.getWidth() / 1.5);
                card.setHeight(card.getHeight() / 1.5);
                card.setSelect(false);
            }
        }
    }

    public static void setRandomHand(User user) {
        Random random = new Random();
        user.setBoard(new Board());
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(0, user.getDeck().size());
            user.getBoard().getHand().add(user.getDeck().get(num));
            user.getDeck().remove(num);
        }

    }

    private static int getHbox(Card card) {
        if (card.getAbility()!=null&&card.getAbility().equals("spy")) {
            return switch (card.getType()) {
                case "closeCombatUnit" -> 3;
                case "rangedUnit" -> 4;
                case "siegeUnit" -> 5;
                case "agileUnit" -> 34;
                default -> 6;
            };
        }
        return switch (card.getType()) {
            case "closeCombatUnit" -> 2;
            case "rangedUnit" -> 1;
            case "siegeUnit" -> 0;
            case "weather" -> 6;
            case "agileUnit" -> 12;
            // spell ro chikar konam?
//            case "spell" -> 6;
            default -> 4;
        };

    }

    private static int getTotalHboxPower(HBox hBox) {
        int total = 0;

        for (int i = 0; i < hBox.getChildren().size(); i++)
            total += ((Card) hBox.getChildren().get(i)).getPower();
        return total;
    }

    public static String vetoCard(Card card) {

        return null;
    }

    public static void showCardInfo(Card card, ImageView biggerCardImage) {
        card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
        card.setOnMouseExited(event -> biggerCardImage.setImage(null));
    }







    public static void leaderAction() {

    }









    public static void changeTurn(HBox deckHbox, ArrayList<HBox> hBoxes, ImageView highScoreIcon, Label turnLabel) {
        if (!ApplicationController.getRoot().getChildren().contains(turnLabel))
            ApplicationController.getRoot().getChildren().add(turnLabel);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), turnLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
        User.setTurnUser(User.getTurnUser().getOpponentUser());
        deckHbox.getChildren().clear();
        swapHboxes(0, 5, hBoxes);
        swapHboxes(1, 4, hBoxes);
        swapHboxes(2, 3, hBoxes);
        GameController.setImagesOfBoard(User.getTurnUser(), hBoxes, highScoreIcon);

    }

    private static void swapHboxes(int hbox1, int hbox2, ArrayList<HBox> hBoxes) {
        ArrayList<Node> swapArray = new ArrayList<>(hBoxes.get(hbox1).getChildren());
        hBoxes.get(hbox1).getChildren().removeAll();
        hBoxes.get(hbox1).getChildren().addAll(hBoxes.get(hbox2).getChildren());
        hBoxes.get(hbox2).getChildren().removeAll();
        hBoxes.get(hbox2).getChildren().addAll(swapArray);
    }

    public static boolean checkEnding() {
        return true;
    }

    private static void endGame() {

    }

    private static void killCard(Card card) {

    }


    public static void placeCard(ArrayList<HBox> hBoxes, HBox deckHbox, HBox hBox, ImageView highScoreIcon) {
        for (Iterator<Card> cardIterator = User.getTurnUser().getBoard().getHand().iterator(); cardIterator.hasNext(); ) {
            Card card = cardIterator.next();
            System.out.println(card.getName());
            System.out.println(card.isSelect());
            System.out.println(card.isInDeck());
            System.out.println(hBox.getStyle());
            if (card.isSelect() && card.isInDeck() && hBox.getStyle().equals("-fx-background-color: rgba(252,237,6,0.13);")) {
                deckHbox.getChildren().remove(card);
                hBox.getChildren().add(card);
                hBox.setStyle(null);
                deckHbox.setLayoutY(deckHbox.getLayoutY() + 20);
                card.setWidth(card.getWidth() / 1.5);
                card.setHeight(card.getHeight() / 1.5);
                card.setSelect(false);
                card.setInDeck(false);
                cardIterator.remove();
                setImagesOfBoard(User.getTurnUser(), hBoxes, highScoreIcon);
                for (HBox hBox1 : hBoxes)
                    hBox1.setStyle(null);
                card.abilityAction();
            }
        }
    }


//public static void addToBoard (Card card, HBox hBox, ArrayList<HBox> hBoxes){
//        switch (hBoxes.indexOf(hBox)){
//            case 0:
//                User.getTurnUser().getBoard().getSiege().add(card);
//                break;
//            case 1:
//                User.getTurnUser().getBoard().getRanged().add(card);
//                break;
//            case 2:
//                User.getTurnUser().getBoard().getCloseCombat().add(card);
//                break;
//            case 3:
//                User.getTurnUser().getOpponentUser().getBoard().getCloseCombat().add(card);
//                break;
//            case 4:
//                User.getTurnUser().getOpponentUser().getBoard().getRanged().add(card);
//                break;
//            case 5:
//                User.getTurnUser().getOpponentUser().getBoard().getSiege().add(card);
//                break;
//            case 6:
//                User.getTurnUser().getBoard().get
//        }


//}
    public static void nextRound(ArrayList<HBox> hBoxes, ImageView highScoreImage) {
        System.out.println(User.getTurnUser().getUsername());
        System.out.println(User.getTurnUser().getOpponentUser().getUsername());
        System.out.println(hBoxes.get(0));
        if (getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0)) > getTotalHboxPower(hBoxes.get(5)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(3))) {
            if (User.getTurnUser().getOpponentUser().isFullHealth())
                User.getTurnUser().getOpponentUser().setFullHealth(false);
            else System.exit(0);
        } else {
            if (User.getTurnUser().isFullHealth())
                User.getTurnUser().setFullHealth(false);
            else System.exit(0);
        }
        for (HBox hBox : hBoxes)
            hBox.getChildren().clear();
        setImagesOfBoard(User.getTurnUser(), hBoxes, highScoreImage);


    }
}
