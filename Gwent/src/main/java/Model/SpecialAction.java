package Model;

import Controller.GameController;
import View.GameMenu;
import javafx.application.Application;
import javafx.scene.Node;
import Controller.ApplicationController;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class SpecialAction {
    public static int bitingFrost(int number){
        for (Card card : User.getTurnUser().getBoard().getWeather()) {
            if (card.getName().equals("BitingFrost"))
                if (User.getTurnUser().getBoard().leaderBoost[4] ||
                        User.getTurnUser().getOpponentUser().getBoard().leaderBoost[4]) {
                    return (1 + number) / 2;
                } else {
                    return 1;
                }
        }
        for (Card card : User.getTurnUser().getOpponentUser().getBoard().getWeather()) {
            if (card.getName().equals("BitingFrost"))
                if (User.getTurnUser().getBoard().leaderBoost[4] ||
                        User.getTurnUser().getOpponentUser().getBoard().leaderBoost[4]) {
                    return (1 + number) / 2;
                } else {
                    return 1;
                }
        }
        return number;
    }

    public static int impenetrableFog(int number){
        for (Card card : User.getTurnUser().getBoard().getWeather()) {
            if (card.getName().equals("ImpenetrableFog")) return 1;
        }
        for (Card card : User.getTurnUser().getOpponentUser().getBoard().getWeather()) {
            if (card.getName().equals("ImpenetrableFog")) return 1;
        }
        return number;
    }

    public static int torrentialRain(int number){
        for (Card card : User.getTurnUser().getBoard().getWeather()) {
            if (card.getName().equals("TorrentialRain")) return 1;
        }
        for (Card card : User.getTurnUser().getOpponentUser().getBoard().getWeather()) {
            if (card.getName().equals("TorrentialRain")) return 1;
        }
        return number;
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

    public static int moralBoost(ArrayList<Card> arrayListPlace, Card card,int power) {
        for (Card card1 : arrayListPlace) {
            if (card1.equals(card)) continue;
            if (card1.getAbility() != null && card1.getAbility().contains("moralBoost")){
                power += 1;
            }
        }
        return power;
    }
    public static int commanderHorn(ArrayList <Card> cards,Card target,int number) {
        for (Card card : cards) {
            if (card.getAbility()!=null&&card.getAbility().equals("commander'sHorn") && !card.equals(target)) {
                return 2 * number;
            }
        }
        return number;
    }

}
