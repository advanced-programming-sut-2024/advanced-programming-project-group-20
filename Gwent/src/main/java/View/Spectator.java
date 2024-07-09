package View;

import Model.*;
import Model.chat.Message;
import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import webConnection.Client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;

public class Spectator extends Application {
    public HBox turnSiegeNext;
    public HBox turnRangedNext;
    public HBox turnCloseNext;
    public HBox opponentCloseNext;
    public HBox opponentRangedNext;
    public HBox opponentSiegeNext;
    @FXML
    public Label passed;
    public Label passedOpponent;
    public AnchorPane pane;
    public HBox turnRanged;
    public HBox turnSiege;
    public HBox opponentCombat;
    public HBox opponentSiege;
    public HBox opponentRanged;
    public HBox turnCombat;
    public HBox spellHbox;
    public ImageView highScoreImage;
    public ImageView turnBurnt;
    public ImageView opponentBurnt;
    private static User gameUser;
    public Button exit;
    private static Spectator spectator;
    public Button changeGame;
    public AnchorPane chatPane;
    public ScrollPane chatScroll;
    public Button sendButton;
    public TextField sendField;
    public Button chatButton;
    Timeline timeline;
    public static Chat chat;

    public static void setGameUser(User gameUser) {
        Spectator.gameUser = gameUser;
    }

    public ArrayList<HBox> hBoxes = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationController.setStage(stage);
        ApplicationController.setMedia("/music/along-the-wayside-medieval-folk-music-128697.mp3");
        URL url = PreGameMenu.class.getResource("/FXML/Spectator.fxml");
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
        stage.show();
        stage.setHeight(740);
        stage.setWidth(1280);
        stage.setHeight(741);
        stage.centerOnScreen();
    }

    @FXML
    public void initialize() {
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
            Client.getConnection().doInServer("ChatController", "getMessages", message, gameUser.getUsername());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            chat.getvBox().getChildren().clear();
            for (Message message1 : chat.getMessages()) {
                chat.getvBox().getChildren().add(message1.toVBox());
            }
            sendField.setText("");
        });
        //chat
        ApplicationController.setRoot(pane);
        pane.getChildren().remove(passed);
        pane.getChildren().remove(passedOpponent);
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> {
            Client.getConnection().doInServer("GameController", "getUpdateGame", gameUser);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        exit.setOnMouseClicked(mouseEvent -> {
            timeline.stop();
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        boolean tournament = false;
        for (String string : Tournament.getTournament().getActiveGames()) {
            if (string.contains(gameUser.getUsername()) && string.contains(gameUser.getOpponentUser().getUsername())) {
                changeGame.setOnMouseClicked(mouseEvent ->
                        Client.getConnection().doInServer("TournamentController", "getCurrentGames",
                                User.getLoggedUser().getUsername()));
                tournament = true;
            }
        }
        if (!tournament) pane.getChildren().remove(changeGame);
        setHboxes();
        setImagesOfBoard();
        setHighScoreIcon();
        spectator = this;
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

    public void setImagesOfBoard() {
        if (ApplicationController.getRoot().getChildren().contains(passed))
            ApplicationController.getRoot().getChildren().remove(passed);
        if (ApplicationController.getRoot().getChildren().contains(passedOpponent))
            ApplicationController.getRoot().getChildren().remove(passedOpponent);
        updateBorder();
        ApplicationController.getRoot().getChildren().removeIf(node -> (node instanceof Label) && ((!Objects.equals(node.getId(), "no")) || (!Objects.equals(node.getId(), "no"))));
        setImageOfDeckBack(gameUser, 686);
        setImageOfDeckBack(gameUser.getOpponentUser(), 60);
        setImageCartNumber(420, 50, getTotalHboxPower(hBoxes.get(5)));
        setImageCartNumber(420, 157, getTotalHboxPower(hBoxes.get(4)));
        setImageCartNumber(420, 272, getTotalHboxPower(hBoxes.get(3)));
        setImageCartNumber(420, 391, getTotalHboxPower(hBoxes.get(2)));
        setImageCartNumber(420, 500, getTotalHboxPower(hBoxes.get(1)));
        setImageCartNumber(420, 612, getTotalHboxPower(hBoxes.get(0)));
        setImageCartNumber(358, 260, getTotalHboxPower(hBoxes.get(5)) + getTotalHboxPower(hBoxes.get(4)) + getTotalHboxPower(hBoxes.get(3)));
        setImageCartNumber(358, 590, getTotalHboxPower(hBoxes.get(2)) + getTotalHboxPower(hBoxes.get(1)) + getTotalHboxPower(hBoxes.get(0)));
        setProfileImages(gameUser.getOpponentUser(), 200);
        setProfileImages(gameUser, 520);
        setHighScoreIcon();
        if (!ApplicationController.getRoot().getChildren().contains(passed) &&
                gameUser.isPassed()) ApplicationController.getRoot().getChildren().add(passed);
        if (!ApplicationController.getRoot().getChildren().contains(passedOpponent) &&
                gameUser.getOpponentUser().isPassed())
            ApplicationController.getRoot().getChildren().add(passedOpponent);
        deckCardNumber(gameUser.getOpponentUser(), 295);
        deckCardNumber(gameUser, 615);
        setLeaderImage(gameUser, 687);
        setLeaderImage(gameUser.getOpponentUser(), 64);
    }

    public void updateBorder() {
        User user1 = gameUser;
        User user2 = gameUser.getOpponentUser();
        for (HBox hBox : hBoxes) {
            hBox.getChildren().clear();
        }
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
        setLabels(gameUser);
        setLabels(gameUser.getOpponentUser());
        setBurntCard();
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
                if (gameUser.getBoard().leaderBoost[0]) {
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
                    number = SpecialAction.bitingFrost(number, gameUser);
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

    private void setLeaderImage(User user, int height) {
        ImageView leader = new ImageView();
        leader.setImage(new Image(String.valueOf(GameMenu.class.getResource("/Leaders/" + user.getLeader().getName() + ".jpg"))));
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
        Label labelPlayer = new Label(user.getFaction().getName());
        labelPlayer.setId("usernameLable");
        labelPlayer.setLayoutX(200);
        labelStyle(height + 20, labelPlayer);
        Label labelUsername = new Label(user.getUsername());
        labelUsername.setId("playerId");
        labelUsername.setLayoutX(200);
        labelStyle(height, labelUsername);
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

    private int getTotalHboxPower(HBox hBox) {
        int total = 0;
        for (int i = 0; i < hBox.getChildren().size(); i++) {
            Card card = (Card) hBox.getChildren().get(i);
            Label label = (Label) card.getChildren().get(1);
            total += Integer.parseInt(label.getText());
        }
        return total;
    }

    private void deckCardNumber(User loggedUser, int height) {
        Label labelUsername = new Label(String.valueOf(loggedUser.getBoard().getHand().size()));
        labelUsername.setId("deckNumber");
        labelUsername.setLayoutX(190);
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

    public void setBurntCard() {
        if (!gameUser.getBoard().getBurnedCard().isEmpty()) {
            turnBurnt.setImage(gameUser.getBoard().getBurnedCard().getLast().getGameImage());
        } else turnBurnt.setImage(null);
        if (!gameUser.getOpponentUser().getBoard().getBurnedCard().isEmpty()) {
            opponentBurnt.setImage(gameUser.getOpponentUser().getBoard().getBurnedCard().getLast().getGameImage());
        } else opponentBurnt.setImage(null);
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


    public static void updateGame(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Gson gson = new Gson();
            User user = gson.fromJson(gson.toJson(objects.get(0)), User.class);
            gameUser.setCards(user.getCards());
            gameUser.boardMaker();
            spectator.setImagesOfBoard();
        });
    }

    public static void endGame(ArrayList<Object> objects) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("End Game");
            alert.setHeaderText("Game Over");
            alert.show();
            Spectator.spectator.timeline.stop();
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
