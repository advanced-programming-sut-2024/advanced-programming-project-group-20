package Model;

import Controller.GameController;
import javafx.application.Application;
import javafx.scene.Node;
import Controller.ApplicationController;

import java.util.concurrent.atomic.AtomicReference;

public class SpecialAction {
    public static void bitingFrost(){

    }

    public static void impenetrableFog(){

    }

    public static void torrentialRain(){

    }

    public static void decoy(Card decoy) {
        for (Card card : User.getTurnUser().getBoard().getCloseCombat()) {
            card.setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().getHand().add(card);
                User.getTurnUser().getBoard().getCloseCombat().remove(card);
                User.getTurnUser().getBoard().getCloseCombat().add(decoy);
            });
        }
        for (Card card : User.getTurnUser().getBoard().getSiege()) {
            card.setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().getHand().add(card);
                User.getTurnUser().getBoard().getSiege().remove(card);
                User.getTurnUser().getBoard().getSiege().add(decoy);
            });
        }
        for (Card card : User.getTurnUser().getBoard().getRanged()) {
            card.setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().getHand().add(card);
                User.getTurnUser().getBoard().getRanged().remove(card);
                User.getTurnUser().getBoard().getRanged().add(decoy);
            });
        }


    }



    public static void Villentretenmerth(){

    }

    public static void toad(){

    }

    public static void schirru(){

    }

    public static void mardroeme() {

    }

    public static void berserker() {

    }


    public static void cerys(){

    }

    public static void kombi(){

    }

    public static void shieldmaide(){

    }

    public static void clanDimunPirate(){

    }
    public static void youngBerserker() {

    }
}
