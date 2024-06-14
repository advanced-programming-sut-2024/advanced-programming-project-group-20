package Controller;

import Model.Card;
import Model.CardBuilder;
import Model.Factions.Skellige;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PreGameController {

    public ScrollPane scroolpane;
    private ArrayList<ImageView> cards = new ArrayList<>();

    @FXML
    public void initialize() {
        TilePane vBox = new TilePane(0,0);
        vBox.setMaxWidth(300);
        for (int i = 1; i < 11; i++) {
            ImageView imageView = new ImageView(new Image(String.valueOf(PreGameController.class.getResource("/" + i + ".jpg"))));
            imageView.setFitHeight(200);
            imageView.setFitWidth(90);
            Button imageButton = new Button();
            imageButton.setGraphic(imageView);
            imageButton.setOnAction(event -> {
//                System.out.println("fds");
            });
            vBox.getChildren().add(imageView);
            vBox.getChildren().add(imageButton);
        }
        scroolpane.setContent(vBox);
    }

    private static void setRandomHand(){}
    public static void startGame(){}
    public static void showCards() {

    }

    public static void showDeck() {
    }

    public static void showInformation() {
    }

    public static void saveDeckByAddress() {
    }

    public static void saveDeckByName() {
    }

    public static String loadGameByName() {
        return null;
    }

    public static String loadGameByFile() {
return null;
    }

    public static String showLeaders() {
        return null;
    }


    public static void selectLeaders(int leaderNumber) {
    }

    public static void addCardToDeck(String cardName, int count) {
    }

    public static void delete(String cardName, int count) {
    }
    public static void changeTurn(){}
}
