package Model;

import Controller.ApplicationController;
import Controller.GameController;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Random;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;

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
        for (Timeline timeline : GameController.timelines)
            timeline.stop();
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
                if (!hBox.getChildren().contains(card))
                    hBox.getChildren().add(card);
                System.out.println(card.getName());
                card.getChildren().get(0).setScaleX(2.5);
                card.getChildren().get(0).setScaleY(2.5);
                card.setOnMouseClicked(mouseEvent -> {
                    for (Timeline timeline : GameController.timelines)
                        timeline.play();
                    root.getChildren().remove(hBox);
                    User.getTurnUser().getBoard().getBurnedCard().remove(card);
                    User.getTurnUser().getBoard().getHand().add(card);
                    card.setOnMouseClicked(null);
                    for (Node node : hBox.getChildren()) {
                        if (((Pane) node).getChildren().get(0).getScaleX() > 1) {
                            node.setScaleX(0.4);
                            node.setScaleY(0.4);
                        }
                    }
                    ApplicationController.setEnable(root);
                    //todo pak
                    System.out.println(6);
                });
            }
        }


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


    }

    public static void berserker() {

    }

    public static void mardroeme() {

    }

    public static void transformers() {

    }
}
