package Model;

import Controller.ApplicationController;
import Controller.GameController;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class AbilityActions {
    public static void
    switchAction(Card card) {
        if (card.getAbility() != null)
            switch (card.getAbility()) {
                case "spy":
                    spy();
                    break;
                case "scorch":
                    scorch();
                    break;
                case "medic":
                    medic();
                    break;

            }


    }

    public static void commanderHorn() {

    }

    public static void medic() {
        Pane root = ApplicationController.getRoot();
        ApplicationController.setDisable(root);
        HBox hBox = new HBox();
        hBox.setLayoutX(300);
        hBox.setLayoutY(400);
        hBox.setPrefHeight(300);
        hBox.setPrefWidth(1000);
        hBox.setSpacing(100);
        hBox.setAlignment(Pos.CENTER);
        root.getChildren().add(hBox);
        for (Card card : User.getTurnUser().getBoard().getBurnedCard()) {
            if (card.getAbility() == null || !(card.getAbility().equals("hero") || card.getType().equals("weather") || card.getType().equals("spell"))) {
                System.out.println(card.getName());
                if (!hBox.getChildren().contains(card))
                    hBox.getChildren().add(card);
                setSizeSmaller(card, 2.5);
                card.setOnMouseClicked(mouseEvent -> {
                    card.setSelect(false);
                    root.getChildren().remove(hBox);
                    User.getTurnUser().getBoard().getBurnedCard().remove(card);
                    User.getTurnUser().getBoard().getHand().add(card);
                    card.setOnMouseClicked(null);
                    for (Node node : hBox.getChildren()) {
                        if (((Pane) node).getHeight() > 100) {
                            setSizeSmaller((Card) node, 0.4);
                        }
                    }
                    ApplicationController.setEnable(root);
                    //todo pak
                    System.out.println(6);
                });
            }
        }
    }

    private static void setSizeSmaller(Card card, double scale) {
        card.setPrefWidth(card.getPrefWidth() * scale);
        card.setPrefHeight(card.getPrefHeight() * scale);
        ((Rectangle) card.getChildren().get(0)).setHeight(((Rectangle) card.getChildren().get(0)).getHeight() * scale);
        ((Rectangle) card.getChildren().get(0)).setWidth(((Rectangle) card.getChildren().get(0)).getWidth() * scale);
        card.setSelect(false);
    }

    public static void moralBoost() {

    }

    public static void muster() {

    }

    public static void spy() {
        Random random = new Random();
        int num = random.nextInt(0, User.getTurnUser().getDeck().size());
        User.getTurnUser().getBoard().getHand().add(User.getTurnUser().getDeck().get(num));
        User.getTurnUser().getDeck().remove(num);
        num = random.nextInt(0, User.getTurnUser().getDeck().size());
        User.getTurnUser().getBoard().getHand().add(User.getTurnUser().getDeck().get(num));
        User.getTurnUser().getDeck().remove(num);
    }

    public static void tightBond() {

    }

    public static void scorch() {
        ArrayList<Card> shouldBurn = new ArrayList<>();
        ArrayList<Card> allCardsOnBoard = new ArrayList<>();
        //have all cards in one arraylist
        allCardsOnBoard.addAll(User.getTurnUser().getBoard().getRanged());
        allCardsOnBoard.addAll(User.getTurnUser().getBoard().getSiege());
        allCardsOnBoard.addAll(User.getTurnUser().getBoard().getCloseCombat());
        allCardsOnBoard.addAll(User.getTurnUser().getOpponentUser().getBoard().getCloseCombat());
        allCardsOnBoard.addAll(User.getTurnUser().getOpponentUser().getBoard().getSiege());
        allCardsOnBoard.addAll(User.getTurnUser().getOpponentUser().getBoard().getRanged());
        int maxPointOfCards = 0;
        for (Card card : allCardsOnBoard) {
            if (card.getPower() > maxPointOfCards && !card.getAbility().equals("hero")) {
                maxPointOfCards = card.getPower();
            }
        }
        for (Card card : allCardsOnBoard) {
            //TODO check this part
            if (!card.getAbility().equals("hero") && card.getPower() == maxPointOfCards) {
                shouldBurn.add(card);
            }
        }

    }

    public static void berserker() {

    }

    public static void mardroeme() {

    }

    public static void transformers() {

    }
}
