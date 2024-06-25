package Model;

import Controller.GameController;
import View.GameMenu;
import javafx.application.Application;
import javafx.scene.Node;
import Controller.ApplicationController;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class SpecialAction {
    public static void bitingFrost(){
        for (Card card : User.getTurnUser().getBoard().getCloseCombat()){
        }
    }

    public static void impenetrableFog(){

    }

    public static void torrentialRain(){

    }

    public static void decoy(Card decoy) {
        for (Card card : User.getTurnUser().getBoard().getCloseCombat()) {
            card.setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().setHasPlayedOne(true);
                User.getTurnUser().getBoard().getHand().remove(decoy);
                decoy.setOnMouseClicked(null);
                GameController.setSizeSmaller(decoy);
                decoyAction(decoy);
                User.getTurnUser().getBoard().getHand().add(card);
                User.getTurnUser().getBoard().getCloseCombat().remove(card);
                User.getTurnUser().getBoard().getCloseCombat().add(decoy);
                GameController.updateBorder();
            });
        }
        for (Card card : User.getTurnUser().getBoard().getSiege()) {
            card.setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().setHasPlayedOne(true);
                User.getTurnUser().getBoard().getHand().remove(decoy);
                decoy.setOnMouseClicked(null);
                GameController.setSizeSmaller(decoy);
                decoyAction(decoy);
                User.getTurnUser().getBoard().getHand().add(card);
                User.getTurnUser().getBoard().getSiege().remove(card);
                User.getTurnUser().getBoard().getSiege().add(decoy);
                GameController.updateBorder();
            });
        }
        for (Card card : User.getTurnUser().getBoard().getRanged()) {
            card.setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getBoard().setHasPlayedOne(true);
                User.getTurnUser().getBoard().getHand().remove(decoy);
                decoy.setOnMouseClicked(null);
                GameController.setSizeSmaller(decoy);
                decoyAction(decoy);
                User.getTurnUser().getBoard().getHand().add(card);
                User.getTurnUser().getBoard().getRanged().remove(card);
                User.getTurnUser().getBoard().getRanged().add(decoy);
                GameController.updateBorder();
            });
        }


    }

    public static void decoyAction(Card decoy) {
        ArrayList<Card> allcards = new ArrayList<>();
        allcards.addAll(User.getTurnUser().getBoard().getRanged());
        allcards.addAll(User.getTurnUser().getBoard().getSiege());
        allcards.addAll(User.getTurnUser().getBoard().getCloseCombat());
        for (Card card : allcards) {
            card.setOnMouseClicked(null);
        }
        for (Card card : User.getTurnUser().getBoard().getHand()) {
            card.setOnMouseClicked(null);
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

    public static void weather() {
        if (!User.getTurnUser().getBoard().getWeather().isEmpty()){
            for (Card card : User.getTurnUser().getBoard().getWeather()) {
                if (card.getName().equals("TorrentialRain"))
                    torrentialRain();
                if (card.getName().equals("ImpenetrableFog"))
                    torrentialRain();
                if (card.getName().equals("BitingFrost"))
                    torrentialRain();
            }
        }
        if (!User.getTurnUser().getOpponentUser().getBoard().getWeather().isEmpty()){
            for (Card card : User.getTurnUser().getBoard().getWeather()) {
                if (card.getName().equals("TorrentialRain"))
                    torrentialRain();
                if (card.getName().equals("ImpenetrableFog"))
                    torrentialRain();
                if (card.getName().equals("BitingFrost"))
                    torrentialRain();
            }
        }
    }
}
