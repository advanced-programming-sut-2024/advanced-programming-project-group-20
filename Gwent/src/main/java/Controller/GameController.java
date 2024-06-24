package Controller;

import Model.*;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CountDownLatch;



public class GameController {
public static ArrayList<Timeline> timelines = new ArrayList<>();


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
        updateBorder(hBoxes);
    }

    private static void setLeaderImage(User user, int height) {
        ImageView leader = new ImageView();
        leader.setImage(user.getLeader().getImage());
        if (user.equals(User.getTurnUser())) {
            leader.setOnMouseClicked(mouseEvent -> {
                user.getLeader().action();
                user.getLeader().setUsed(true);
            });
        }
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
                if (getHbox(card) >= 10 )
                    hBoxes.get(getHbox(card) / 10).setStyle("-fx-background-color: rgba(252,237,6,0.13);");
                if (getHbox(card) == 9) return;
                hBoxes.get(getHbox(card) % 10).setStyle("-fx-background-color: rgba(252,237,6,0.13);");
                card.setPrefWidth(card.getWidth() * 1.5);
                card.setPrefHeight(card.getHeight() * 1.5);
                ((Rectangle) card.getChildren().get(0)).setHeight(((Rectangle) card.getChildren().get(0)).getHeight() * 1.5);
                ((Rectangle) card.getChildren().get(0)).setWidth(((Rectangle) card.getChildren().get(0)).getWidth() * 1.5);
                card.setSelect(true);
                if (card.getName().equals("decoy")) {
                    SpecialAction.decoy(card);
                }
            }
        } else {
            if (card.isSelect()) {
                if (getHbox(card) >= 10)
                    hBoxes.get(getHbox(card) / 10).setStyle(null);
                hBoxes.get(getHbox(card) % 10).setStyle(null);
                setSizeSmaller(card);
            }
        }

    }

    public static void setBurntCard(ImageView turnBurnt, ImageView opponentBurnt) {
        if (!User.getTurnUser().getBoard().getBurnedCard().isEmpty()) {
            turnBurnt.setImage(User.getTurnUser().getBoard().getBurnedCard().getLast().getGameImage());
            if (!User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().isEmpty()) {
                opponentBurnt.setImage(User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().getLast().getGameImage());
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
        if (card.getAbility() != null && card.getAbility().equals("spy")) {
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
            case "spell" -> 9;
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
        Timeline waitForChange = new Timeline(new KeyFrame(Duration.seconds(1.8), actionEvent -> {
            User.setTurnUser(User.getTurnUser().getOpponentUser());
            swapHboxes(0, 5, hBoxes);
            swapHboxes(1, 4, hBoxes);
            swapHboxes(2, 3, hBoxes);
            GameController.setImagesOfBoard(User.getTurnUser(), hBoxes, highScoreIcon);
        }));
        waitForChange.setCycleCount(1);
        waitForChange.play();
        timelines.add(waitForChange);
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


    public static boolean placeCard(ArrayList<HBox> hBoxes, HBox deckHbox, HBox hBox, ImageView highScoreIcon, CountDownLatch latch) {
        for (Iterator<Card> cardIterator = User.getTurnUser().getBoard().getHand().iterator(); cardIterator.hasNext(); ) {
            Card card = cardIterator.next();
            System.out.println(card.getName());
            System.out.println(card.isSelect());
            System.out.println(card.isInDeck());
            System.out.println(!hBox.getStyle().isEmpty());
            if (card.isSelect() && card.isInDeck() && !hBox.getStyle().isEmpty()) {
                System.out.println(2.5);
                deckHbox.getChildren().remove(card);
                hBox.getChildren().add(card);
                hBox.setStyle(null);
                setSizeSmaller(card);
                card.setInDeck(false);
                cardIterator.remove();
                setImagesOfBoard(User.getTurnUser(), hBoxes, highScoreIcon);
                for (HBox hBox1 : hBoxes)
                    hBox1.setStyle(null);
                AbilityActions.switchAction(card);
                return true;
            }
        }
        return false;
    }

    private static void setSizeSmaller(Card card) {
        card.setPrefWidth(card.getWidth() / 1.5);
        card.setPrefHeight(card.getHeight() / 1.5);
        ((Rectangle) card.getChildren().get(0)).setHeight(((Rectangle) card.getChildren().get(0)).getHeight() / 1.5);
        ((Rectangle) card.getChildren().get(0)).setWidth(((Rectangle) card.getChildren().get(0)).getWidth() / 1.5);
        card.setSelect(false);
    }


    public static void nextRound(ArrayList<HBox> hBoxes, ImageView highScoreImage) {
        User.getTurnUser().getBoard().setHasPlayedOne(false);
        User.getTurnUser().getOpponentUser().getBoard().setHasPlayedOne(false);
        System.out.println(User.getTurnUser().getUsername());
        System.out.println(User.getTurnUser().getOpponentUser().getUsername());
        System.out.println(hBoxes.get(0));
        Card monster1 = null;
        Card monster2 = null;
        double totalPoints1 = getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0));
        double totalPoints2 = getTotalHboxPower(hBoxes.get(3)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(5));
        // todo un commment them
//        calculatePoints(User.getTurnUser(),totalPoints1, totalPoints2);
//        calculatePoints(User.getTurnUser().getOpponentUser(),totalPoints2, totalPoints1);
        if (totalPoints1 > totalPoints2) {
            if (User.getTurnUser().getOpponentUser().isFullHealth()){
                User.getTurnUser().getOpponentUser().setFullHealth(false);
                if (User.getTurnUser().getFaction().getName().equals("NorthernRealms")) {
                    northernRealms(User.getTurnUser());
                }
            }
            else System.exit(0);
        } else if (totalPoints1 < totalPoints2) {
            if (User.getTurnUser().isFullHealth()) {
                if (User.getTurnUser().getOpponentUser().getFaction().getName().equals("NorthernRealms")) {
                    northernRealms(User.getTurnUser().getOpponentUser());
                }
                User.getTurnUser().setFullHealth(false);
            }
            else System.exit(0);
        } else {
            if (User.getTurnUser().getFaction().getName().equals("Nilfgaard")) {
                if (!User.getTurnUser().getOpponentUser().getFaction().getName().equals("Nilfgaard")) {
                    if (User.getTurnUser().getOpponentUser().isFullHealth())
                        User.getTurnUser().getOpponentUser().setFullHealth(false);
                    else System.exit(0);
                }
            }
            if (User.getTurnUser().getOpponentUser().getFaction().getName().equals("Nilfgaard")) {
                if (!User.getTurnUser().getFaction().getName().equals("Nilfgaard")) {
                    if (User.getTurnUser().getOpponentUser().isFullHealth())
                        User.getTurnUser().getOpponentUser().setFullHealth(false);
                    else System.exit(0);
                }
            }
        }
        if (User.getTurnUser().getFaction().getName().equals("Monsters")) {
            monster1 = monstersAction(hBoxes.subList(0,3));
        }
        if (User.getTurnUser().getOpponentUser().getFaction().getName().equals("Monsters")){
            monster2 = monstersAction(hBoxes.subList(3,6));
        }
        if (User.getTurnUser().getFaction().getName().equals("Skellige")) {
            skelligeAction(User.getTurnUser());
        }
        if (User.getTurnUser().getOpponentUser().getFaction().getName().equals("Skellige")){
            skelligeAction(User.getTurnUser().getOpponentUser());
        }
        turnStarter();

        putInBurntCards(User.getTurnUser());
        putInBurntCards(User.getTurnUser().getOpponentUser());
        for (HBox hBox : hBoxes)
            hBox.getChildren().clear();
        if (monster1 != null) {
            if (monster1.getType().equals("siegeUnit")) {
                hBoxes.get(2).getChildren().add(monster1);
            } else if (monster1.getType().equals("rangedUnit")) {
                hBoxes.get(1).getChildren().add(monster1);
            } else {
                hBoxes.get(0).getChildren().add(monster1);
            }
        }
        if (monster2 != null) {
            if (monster2.getType().equals("siegeUnit")) {
                hBoxes.get(5).getChildren().add(monster2);
            } else if (monster2.getType().equals("rangedUnit")) {
                hBoxes.get(4).getChildren().add(monster2);
            } else {
                hBoxes.get(3).getChildren().add(monster2);
            }
        }
        setImagesOfBoard(User.getTurnUser(), hBoxes, highScoreImage);


    }


    private static void skelligeAction(User user) {
        if (user.getActiveGame().getSecondRoundPointMe() > -0.9) {
            for (int i = 0;i < 2; i++) {
                Random random = new Random();
                int rand = random.nextInt(0,user.getDeck().size());
                Card card = user.getDeck().get(rand);
                user.getDeck().remove(card);
                user.getBoard().getHand().add(card);
            }
        }
    }

    private static Card monstersAction(List<HBox> hBoxes) {
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
        return allCards.get(random.nextInt(0, allCards.size()));
    }


    private static void calculatePoints(User user,double userPoints, double opponentPoints) {
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

    private static void northernRealms(User user) {
        Random random = new Random();
        int rand = random.nextInt(0,user.getDeck().size());
        Card card = user.getDeck().get(rand);
        user.getDeck().remove(card);
        user.getBoard().getHand().add(card);
    }
    public static void turnStarter() {
        if (User.getLoggedUser().getFaction().getName().equals("ScoiaTeal") &&
                !User.getLoggedUser().getOpponentUser().getFaction().getName().equals("ScoiaTeal")) {
            User.setTurnUser(User.getLoggedUser());
        } else if (!User.getLoggedUser().getFaction().getName().equals("ScoiaTeal") &&
                User.getLoggedUser().getOpponentUser().getFaction().getName().equals("ScoiaTeal")) {
            User.setTurnUser(User.getLoggedUser().getOpponentUser());
        } else {
            Random random = new Random();
            boolean loggedUser = random.nextBoolean();
            if (loggedUser) {
                User.setTurnUser(User.getLoggedUser());
            } else {
                User.setTurnUser(User.getLoggedUser().getOpponentUser());
            }
        }
    }

    private static void putInBurntCards(User user) {
        if (user.getBoard().getSiege() != null){
            System.out.println("no null siege");
            for (Card card : user.getBoard().getSiege())
                user.getBoard().getBurnedCard().add((Card) card);}
        if (user.getBoard().getRanged() != null) {
            System.out.println("no null siege");
            for (Card card : user.getBoard().getRanged())
                user.getBoard().getBurnedCard().add((Card) card);
        }
        if (user.getBoard().getCloseCombat() != null) {
            System.out.println("no null siege");
            for (Card card : user.getBoard().getCloseCombat())
                user.getBoard().getBurnedCard().add((Card) card);
        }
    }

    public static void updateBorder(ArrayList<HBox> hBoxes) {
        for (Node node:hBoxes.get(0).getChildren())
            User.getTurnUser().getBoard().getSiege().add((Card) node);
        for (Node node:hBoxes.get(1).getChildren())
            User.getTurnUser().getBoard().getRanged().add((Card) node);
        for (Node node:hBoxes.get(2).getChildren())
            User.getTurnUser().getBoard().getRanged().add((Card) node);
        for (Node node:hBoxes.get(3).getChildren())
            User.getTurnUser().getOpponentUser().getBoard().getCloseCombat().add((Card) node);
        for (Node node:hBoxes.get(4).getChildren())
            User.getTurnUser().getOpponentUser().getBoard().getRanged().add((Card) node);
        for (Node node:hBoxes.get(5).getChildren())
            User.getTurnUser().getOpponentUser().getBoard().getSiege().add((Card) node);



    }
}
