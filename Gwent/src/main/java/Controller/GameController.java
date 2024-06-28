package Controller;

import Model.*;
import View.MainMenu;
import View.PreGameMenu;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;


public class GameController {

    public static ArrayList<Timeline> timelines = new ArrayList<>();
    private static ArrayList<HBox> hBoxes;
    private static ImageView highScoreIcon;
    private static HBox deckHbox;
    private static ImageView turnBurnt;
    private static ImageView opponentBurnt;
    private static Label turnLabel;
    private static ImageView biggerCardImage;

    public static void setBiggerCardImage(ImageView biggerCardImage) {
        GameController.biggerCardImage = biggerCardImage;
    }

    public static void setTurnLabel(Label turnLabel) {
        GameController.turnLabel = turnLabel;
    }

    public static void setTurnBurnt(ImageView turnBurnt) {
        GameController.turnBurnt = turnBurnt;
    }

    public static void setOpponentBurnt(ImageView opponentBurnt) {
        GameController.opponentBurnt = opponentBurnt;
    }

    public static void setHighScoreIcon(ImageView highScoreIcon) {
        GameController.highScoreIcon = highScoreIcon;
    }

    public static void setDeckHbox(HBox deckHbox) {
        GameController.deckHbox = deckHbox;
    }

    public static void sethBoxes(ArrayList<HBox> hBoxes) {
        GameController.hBoxes = hBoxes;
    }

    public static void setImagesOfBoard(User user) {
        updateBorder();
        ApplicationController.getRoot().getChildren().removeIf(node -> (node instanceof Label) && ((!Objects.equals(node.getId(), "no")) || (!Objects.equals(node.getId(), "no"))));
        setImageOfDeckBack(user, 686);
        setImageOfDeckBack(user.getOpponentUser(), 60);
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
        setHighScoreIcon(highScoreIcon);
        deckCardNumber(User.getTurnUser().getOpponentUser(), 295);
        deckCardNumber(User.getTurnUser(), 615);
        setLeaderImage(User.getTurnUser(), 687);
        setLeaderImage(User.getTurnUser().getOpponentUser(), 64);
    }

    private static void setLeaderImage(User user, int height) {
        ImageView leader = new ImageView();
        leader.setImage(new Image(String.valueOf(GameController.class.getResource("/Leaders/" + user.getLeader().getName() + ".jpg"))));
        if (user.equals(User.getTurnUser())) {
            leader.setOnMouseClicked(mouseEvent -> {
                user.getLeader().action();
                user.getLeader().setUsed(true);
                GameController.updateBorder();
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

    public static void setActiveLeader(User turnUser) {
        ImageView activeLeader;
        activeLeader = new ImageView(new Image(GameController.class.getResourceAsStream("/someImages/icon_leader_active.png")));
        ApplicationController.getRoot().getChildren().add(activeLeader);
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

    private static void setHighScoreIcon() {
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

    public static void putCardInDeck(Card card, ArrayList<Card> hand) {
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
                        SpecialAction.decoy(card);
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

    private static void setSizeBigger(Card card) {
        card.setPrefWidth(card.getWidth() * 1.5);
        card.setPrefHeight(card.getHeight() * 1.5);
        ((Rectangle) card.getChildren().get(0)).setHeight(((Rectangle) card.getChildren().get(0)).getHeight() * 1.5);
        ((Rectangle) card.getChildren().get(0)).setWidth(((Rectangle) card.getChildren().get(0)).getWidth() * 1.5);
        card.setSelect(true);
    }

    public static void setBurntCard(ImageView turnBurnt, ImageView opponentBurnt) {
        if (!User.getTurnUser().getBoard().getBurnedCard().isEmpty())
            turnBurnt.setImage(User.getTurnUser().getBoard().getBurnedCard().getLast().getGameImage());
        else turnBurnt.setImage(null);
        if (!User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().isEmpty())
            opponentBurnt.setImage(User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().getLast().getGameImage());
        else
            opponentBurnt.setImage(null);
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

    private static int getTotalHboxPower(HBox hBox) {
        int total = 0;
        for (int i = 0; i < hBox.getChildren().size(); i++) {
            Card card = (Card) hBox.getChildren().get(i);
            Label label = (Label) card.getChildren().get(1);
            total += Integer.parseInt(label.getText());
        }
        return total;
    }

    public static void changeTurn(ImageView highScoreIcon, Label turnLabel) {
        if (!ApplicationController.getRoot().getChildren().contains(turnLabel))
            ApplicationController.getRoot().getChildren().add(turnLabel);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), turnLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
        Timeline waitForChange = new Timeline(new KeyFrame(Duration.seconds(1.8), actionEvent -> {
            User.setTurnUser(User.getTurnUser().getOpponentUser());
            updateBorder();
            GameController.setImagesOfBoard(User.getTurnUser());
        }));
        waitForChange.setCycleCount(1);
        waitForChange.play();
    }

    private static void endGame(User winner) throws IOException {
        if (winner.getActiveGame().getThirdRoundPointMe() < 0) {
            winner.getActiveGame().setThirdRoundPointMe(0);
            winner.getActiveGame().setThirdRoundPointOpponent(0);
            winner.getOpponentUser().getActiveGame().setThirdRoundPointMe(0);
            winner.getOpponentUser().getActiveGame().setThirdRoundPointOpponent(0);
        }
        winner.getActiveGame().countTotalPoints();
        winner.getOpponentUser().getActiveGame().countTotalPoints();
        winner.getActiveGame().setWinner(winner.getUsername());
        winner.getOpponentUser().getActiveGame().setWinner(winner.getUsername());
        Pane root = ApplicationController.getRoot();
        root.getChildren().clear();
        ImageView imageView = new ImageView(new Image(String.valueOf(GameController.class.getResource("/backgrounds/Gwent_1.jpg"))));
        imageView.setFitWidth(root.getWidth());
        imageView.setFitHeight(root.getHeight());
        root.getChildren().add(imageView);
        VBox vBox = new VBox(20);
        vBox.setPrefSize(800, 800);
        vBox.setLayoutX(400);
        vBox.setLayoutY(50);
        vBox.setAlignment(Pos.TOP_CENTER);
        Label winnerLabel = new Label("Winner " + winner.getUsername());
        winnerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: #0e5201;");
        vBox.getChildren().add(winnerLabel);
        Label firstRound = new Label("First Round:");
        firstRound.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #fb03f9;");
        Label secondRound = new Label("Second Round:");
        secondRound.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #09daf1;");
        Label thirdRound = new Label("Third Round:");
        thirdRound.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ff001e");
        Label total = new Label("Total Points:");
        total.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: rgb(0,0,0)");
        Label firstResult = new Label(winner.getUsername() + "  " + winner.getActiveGame().getFirstRoundPointMe());
        Label secondResult = new Label(winner.getUsername() + "  " + winner.getActiveGame().getSecondRoundPointMe());
        Label thirdResult = new Label(winner.getUsername() + "  " + winner.getActiveGame().getThirdRoundPointMe());
        Label totalPoint = new Label(winner.getUsername() + "  " + winner.getActiveGame().getTotalPointsMe());
        Label firstResult2 = new Label(winner.getOpponentUser().getUsername() + "  " + winner.getActiveGame().getFirstRoundPointOpponent());
        Label secondResult2 = new Label(winner.getOpponentUser().getUsername() + "  " + winner.getActiveGame().getSecondRoundPointOpponent());
        Label thirdResult2 = new Label(winner.getOpponentUser().getUsername() + "  " + winner.getActiveGame().getThirdRoundPointOpponent());
        Label totalPoint2 = new Label(winner.getOpponentUser().getUsername() + "  " + winner.getActiveGame().getTotalPointsOpponent());
        Label[] labels = {firstResult, secondResult, thirdResult, totalPoint, firstResult2, secondResult2, thirdResult2, totalPoint2};
        for (Label label : labels) {
            label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white");
        }
        Button button = new Button("Back to Main Menu");
        button.setStyle("-fx-background-color: #f6a107");
        button.setPrefWidth(150);
        button.setPrefHeight(50);
        button.setOnMouseClicked(mouseEvent -> {
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        winner.getGameHistories().add(winner.getActiveGame());
        winner.getOpponentUser().getGameHistories().add(winner.getOpponentUser().getActiveGame());
        vBox.getChildren().addAll(firstRound, firstResult, firstResult2);
        vBox.getChildren().addAll(secondRound, secondResult, secondResult2);
        vBox.getChildren().addAll(thirdRound, thirdResult, thirdResult2);
        vBox.getChildren().addAll(total, totalPoint, totalPoint2);
        vBox.getChildren().add(button);
        root.getChildren().add(vBox);
    }

    public static boolean placeCard(HBox hBox, ImageView highScoreIcon) {
        for (Iterator<Card> cardIterator = User.getTurnUser().getBoard().getHand().iterator(); cardIterator.hasNext(); ) {
            Card card = cardIterator.next();
            ArrayList<Card> targetArray = getArrayPlace(hBox);
            if (card.isSelect() && !hBox.getStyle().isEmpty()) {
                User.getTurnUser().getBoard().getHand().remove(card);
                targetArray.add(card);
                card.setOnMouseClicked(null);
                hBox.setStyle(null);
                setSizeSmaller(card);
                for (HBox hBox1 : hBoxes)
                    hBox1.setStyle(null);
                if (card.getAbility() != null)
                    targetArray = getArrayMardoem(hBox);
                AbilityActions.switchAction(card, targetArray);
                updateCardEvent();
                updateBorder();
                setImagesOfBoard(User.getTurnUser());
                return true;
            }

        }
        return false;
    }

    private static ArrayList<Card> getArrayMardoem(HBox hBox) {
        if (hBoxes.get(7).equals(hBox))
            return User.getTurnUser().getBoard().getSiege();
        else if (hBoxes.get(8).equals(hBox))
            return User.getTurnUser().getBoard().getRanged();
        else if ((hBoxes.get(9).equals(hBox)))
            return User.getTurnUser().getBoard().getCloseCombat();
        else return null;
    }

    private static ArrayList<Card> getArrayPlace(HBox hBox) {
        if (hBoxes.get(0).equals(hBox)) {
            return User.getTurnUser().getBoard().getSiege();
        } else if (hBoxes.get(1).equals(hBox)) {
            return User.getTurnUser().getBoard().getRanged();
        } else if (hBoxes.get(2).equals(hBox)) {
            return User.getTurnUser().getBoard().getCloseCombat();
        } else if (hBoxes.get(5).equals(hBox)) {
            return User.getTurnUser().getOpponentUser().getBoard().getSiege();
        } else if (hBoxes.get(4).equals(hBox)) {
            return User.getTurnUser().getOpponentUser().getBoard().getRanged();
        } else if (hBoxes.get(3).equals(hBox)) {
            return User.getTurnUser().getOpponentUser().getBoard().getCloseCombat();
        } else if (hBoxes.get(9).equals(hBox)) {
            return User.getTurnUser().getBoard().getCloseNext();
        } else if (hBoxes.get(8).equals(hBox)) {
            return User.getTurnUser().getBoard().getRangedNext();
        } else if (hBoxes.get(7).equals(hBox)) {
            return User.getTurnUser().getBoard().getSiegeNext();
        } else if (hBoxes.get(12).equals(hBox)) {
            return User.getTurnUser().getOpponentUser().getBoard().getCloseNext();
        } else if (hBoxes.get(11).equals(hBox)) {
            return User.getTurnUser().getOpponentUser().getBoard().getRangedNext();
        } else if (hBoxes.get(10).equals(hBox)) {
            return User.getTurnUser().getOpponentUser().getBoard().getSiegeNext();
        } else if (hBoxes.get(6).equals(hBox)) {
            return User.getTurnUser().getBoard().getWeather();
        }
        return null;
    }

    public static void setSizeSmaller(Card card) {
        card.setPrefWidth(card.getWidth() / 1.5);
        card.setPrefHeight(card.getHeight() / 1.5);
        ((Rectangle) card.getChildren().get(0)).setHeight(((Rectangle) card.getChildren().get(0)).getHeight() / 1.5);
        ((Rectangle) card.getChildren().get(0)).setWidth(((Rectangle) card.getChildren().get(0)).getWidth() / 1.5);
        card.setSelect(false);
    }

    public static void nextRound(ImageView highScoreImage) throws IOException {
        updateBorder();
        User.getTurnUser().getBoard().setHasPlayedOne(false);
        User.getTurnUser().getOpponentUser().getBoard().setHasPlayedOne(false);
        Card monster1 = null;
        Card monster2 = null;
        int totalPoints1 = getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0));
        int totalPoints2 = getTotalHboxPower(hBoxes.get(3)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(5));
        // todo un commment them
        calculatePoints(User.getTurnUser(), totalPoints1, totalPoints2);
        calculatePoints(User.getTurnUser().getOpponentUser(), totalPoints2, totalPoints1);
        if (totalPoints1 > totalPoints2) {
            if (User.getTurnUser().getOpponentUser().isFullHealth()) {
                User.getTurnUser().getOpponentUser().setFullHealth(false);
                if (User.getTurnUser().getFaction().getName().equals("NorthernRealms")) {
                    northernRealms(User.getTurnUser());
                }
            } else {
                endGame(User.getTurnUser());
                return;
            }
        } else if (totalPoints1 < totalPoints2) {
            if (User.getTurnUser().isFullHealth()) {
                if (User.getTurnUser().getOpponentUser().getFaction().getName().equals("NorthernRealms")) {
                    northernRealms(User.getTurnUser().getOpponentUser());
                }
                User.getTurnUser().setFullHealth(false);
            } else {
                endGame(User.getTurnUser().getOpponentUser());
                return;
            }
        } else {
            if (User.getTurnUser().getFaction().getName().equals("Nilfgaard")) {
                if (!User.getTurnUser().getOpponentUser().getFaction().getName().equals("Nilfgaard")) {
                    if (User.getTurnUser().getOpponentUser().isFullHealth())
                        User.getTurnUser().getOpponentUser().setFullHealth(false);
                    else {
                        endGame(User.getTurnUser());
                        return;
                    }
                }
            }
            if (User.getTurnUser().getOpponentUser().getFaction().getName().equals("Nilfgaard")) {
                if (!User.getTurnUser().getFaction().getName().equals("Nilfgaard")) {
                    if (User.getTurnUser().getOpponentUser().isFullHealth())
                        User.getTurnUser().getOpponentUser().setFullHealth(false);
                    else {
                        endGame(User.getTurnUser().getOpponentUser());
                        return;
                    }
                }
            }
        }
        if (User.getTurnUser().getFaction().getName().equals("Monsters")) {
            monster1 = monstersAction(hBoxes.subList(0, 3));
        }
        if (User.getTurnUser().getOpponentUser().getFaction().getName().equals("Monsters")) {
            monster2 = monstersAction(hBoxes.subList(3, 6));
        }
        turnStarter();
        putInBurntCards(User.getTurnUser());
        putInBurntCards(User.getTurnUser().getOpponentUser());
        ArrayList<Card> transform1 = AbilityActions.transformers(User.getTurnUser());
        ArrayList<Card> transform2 = AbilityActions.transformers(User.getTurnUser().getOpponentUser());
        for (HBox hBox : hBoxes) {
            ArrayList<Card> target = getArrayPlace(hBox);
            target.clear();
        }
        if (User.getTurnUser().getFaction().getName().equals("Skellige")) {
            skelligeAction(User.getTurnUser());
        }
        if (User.getTurnUser().getOpponentUser().getFaction().getName().equals("Skellige")) {
            skelligeAction(User.getTurnUser().getOpponentUser());
        }
        // ali hard code
        User.getTurnUser().getOpponentUser().getBoard().getWeather().clear();
        if (monster1 != null) {
            if (monster1.getType().equals("siegeUnit")) {
                User.getTurnUser().getBoard().getSiege().add(monster1);
            } else if (monster1.getType().equals("rangedUnit")) {
                User.getTurnUser().getBoard().getRanged().add(monster1);
            } else {
                User.getTurnUser().getBoard().getCloseCombat().add(monster1);
            }
        }
        if (monster2 != null) {
            if (monster2.getType().equals("siegeUnit")) {
                User.getTurnUser().getOpponentUser().getBoard().getSiege().add(monster2);
            } else if (monster2.getType().equals("rangedUnit")) {
                User.getTurnUser().getOpponentUser().getBoard().getRanged().add(monster2);
            } else {
                User.getTurnUser().getOpponentUser().getBoard().getCloseCombat().add(monster2);
            }
        }
        if (!transform2.isEmpty()) {
            for (Card card : transform2) {
                if (card.getType().equals("rangedUnit"))
                    User.getTurnUser().getOpponentUser().getBoard().getRanged().add(card);
                else
                    User.getTurnUser().getOpponentUser().getBoard().getCloseCombat().add(card);
            }

        }
        if (!transform1.isEmpty()) {
            for (Card card : transform2) {
                if (card.getType().equals("rangedUnit"))
                    User.getTurnUser().getBoard().getRanged().add(card);
                else
                    User.getTurnUser().getBoard().getCloseCombat().add(card);
            }
        }
        User.getTurnUser().getBoard().leaderBoost = new boolean[]{false, false, false, false, false};
        User.getTurnUser().getOpponentUser().getBoard().leaderBoost = new boolean[]{false, false, false, false, false};
        updateBorder();
        setImagesOfBoard(User.getTurnUser());
    }

    private static void skelligeAction(User user) {
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

    private static void calculatePoints(User user, int userPoints, int opponentPoints) {
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
        int rand = random.nextInt(0, user.getDeck().size());
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

    public static void updateBorder() {
        User user1 = User.getTurnUser();
        User user2 = User.getTurnUser().getOpponentUser();
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
        setLabels(User.getTurnUser());
        setLabels(User.getTurnUser().getOpponentUser());
        setHighScoreIcon();
        GameController.setBurntCard(turnBurnt, opponentBurnt);
    }

    public static void updateCardEvent() {
        ApplicationController.getRoot().getChildren().remove(turnLabel);
        ArrayList<Card> hand = User.getTurnUser().getBoard().getHand();
        deckHbox.getChildren().clear();
        for (Card card : hand) {
            deckHbox.getChildren().add(card);
            card.setOnMouseEntered(event -> GameController.biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> GameController.biggerCardImage.setImage(null));
            card.setOnMouseClicked(event -> {
                GameController.putCardInDeck(card, hand);
            });
        }
        for (Card card : User.getTurnUser().getDeck()) {
            card.setOnMouseEntered(event -> GameController.biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> GameController.biggerCardImage.setImage(null));
            card.setOnMouseClicked(event -> {
                GameController.putCardInDeck(card, hand);
            });

        }
        setOnClickBoard();
    }

    public static void setLabels(User user) {
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
                if (!SpecialAction.clearWeather())
                    number = SpecialAction.torrentialRain(number);
                if (user.getBoard().leaderBoost[0]) {
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
            if (card.getAbility() != null && card.getAbility().equals("hero")) {
                label.setText(String.valueOf(card.getPower()));
            } else {
                int number = card.getPower();
                if (!SpecialAction.clearWeather())
                    number = SpecialAction.impenetrableFog(number);
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
            if (card.getAbility() != null && card.getAbility().equals("hero")) {
                label.setText(String.valueOf(card.getPower()));
            } else {
                int number = card.getPower();
                if (!SpecialAction.clearWeather())
                    number = SpecialAction.bitingFrost(number);
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

    public static void setOnClickBoard() {
        for (Card card : User.getTurnUser().getBoard().getSiege()) {
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
        }
        for (Card card : User.getTurnUser().getBoard().getRanged()) {
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
        }
        for (Card card : User.getTurnUser().getBoard().getCloseCombat()) {
            card.setOnMouseEntered(event -> biggerCardImage.setImage(card.getImage()));
            card.setOnMouseExited(event -> biggerCardImage.setImage(null));
        }
    }

    public static void doCheat(String text) {
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
            // 4 , 5 for leader works
            case "4":
                User.getTurnUser().getLeader().setUsed(false);
            case "5":

            case "6":
                showOpponentCards(User.getTurnUser().getOpponentUser().getBoard().getHand());
                break;
            case "7":
                showOpponentCards(User.getTurnUser().getOpponentUser().getDeck());

                break;
        }
        setOnClickBoard();
        updateBorder();
        setImagesOfBoard(User.getTurnUser());

    }

    public static void showOpponentCards(ArrayList<Card> cards) {
        Pane root = ApplicationController.getRoot();
        ApplicationController.setDisable(root);
        HBox hBox = new HBox();
        hBox.setLayoutX(300);
        hBox.setLayoutY(400);
        hBox.setPrefHeight(300);
        hBox.setPrefWidth(1000);
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.CENTER);
        root.getChildren().add(hBox);
        for (Card card : cards) {
            if (!hBox.getChildren().contains(card))
                hBox.getChildren().add(card);
            root.setOnMouseClicked(mouseEvent -> {
                card.setSelect(false);
                root.getChildren().remove(hBox);
                card.setOnMouseClicked(null);
                ApplicationController.setEnable(root);
            });
        }
    }
}


