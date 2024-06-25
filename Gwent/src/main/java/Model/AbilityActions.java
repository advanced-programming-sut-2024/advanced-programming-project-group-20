package Model;

import Controller.ApplicationController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class AbilityActions {
    public static void
    switchAction(Card card, ArrayList<Card> arrayListPlace) {
        if (card.getAbility() != null) {
            if (card.getAbility().contains("spy"))
                spy();
            if (card.getAbility().contains("scorch"))
                scorch();
            if (card.getAbility().contains("medic"))
                medic();
            if (card.getAbility().contains("moralBoost"))
                moralBoost(arrayListPlace, card);
            if (card.getAbility().contains("muster"))
                muster(arrayListPlace, card);
        }



    }

    public static void commanderHorn() {

    }

    public static void medic() {
        if (User.getTurnUser().getBoard().getBurnedCard().isEmpty())
            return;
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
                setSizeSmaller(card,2.5);
                card.setOnMouseClicked(mouseEvent -> {
                    card.setSelect(false);
                    root.getChildren().remove(hBox);
                    User.getTurnUser().getBoard().getBurnedCard().remove(card);
                    User.getTurnUser().getBoard().getHand().add(card);
                    card.setOnMouseClicked(null);
                    for (Node node : hBox.getChildren()) {
                        if (((Pane) node).getHeight()>100) {
                          setSizeSmaller((Card) node,0.4);
                        }
                    }
                    ApplicationController.setEnable(root);

                });
            }
        }
    }
    private static void setSizeSmaller(Card card , double scale) {
        card.setPrefWidth(card.getPrefWidth() *scale);
        card.setPrefHeight(card.getPrefHeight() *scale);
        ((Rectangle) card.getChildren().get(0)).setHeight(((Rectangle) card.getChildren().get(0)).getHeight() * scale);
        ((Rectangle) card.getChildren().get(0)).setWidth(((Rectangle) card.getChildren().get(0)).getWidth() * scale);
        card.setSelect(false);
    }

    public static void moralBoost(ArrayList<Card> arrayListPlace, Card card) {
        int power = card.getPower();
        System.out.println(power);
        for (Card card1:arrayListPlace){
            if ((card1.getAbility()==null||!card1.getAbility().contains("hero"))&&!card.equals(card1)) {
                card1.setPower(card1.getPower() + 1);
                ((Label) card1.getChildren().get(1)).setText(String.valueOf(card1.getPower()));
            }
        }
//        card.setPower(power);
//        ((Label) card.getChildren().get(1)).setText(String.valueOf(power));
    }

    public static void muster(ArrayList<Card> arrayListPlace, Card card) {
        ArrayList<Card> hand = User.getTurnUser().getBoard().getHand();
        ArrayList<Card> deck = User.getTurnUser().getDeck();
        findMusters(arrayListPlace, card, hand);
        findMusters(arrayListPlace, card, deck);
    }

    private static void findMusters(ArrayList<Card> arrayListPlace, Card card, ArrayList<Card> deck) {
        for (int i = deck.size()-1; i >= 0; i--) {
            if (deck.get(i).getName().equals(card.getName())) {
                User.getTurnUser().getBoard().getHand().remove(deck.get(i));
                arrayListPlace.add(deck.get(i));
                deck.get(i).setOnMouseClicked(null);
            }
        }
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


    }

    public static void berserker() {

    }

    public static void mardroeme() {

    }

    public static void transformers() {

    }
}
