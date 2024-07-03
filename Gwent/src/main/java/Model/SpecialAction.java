package Model;

import View.GameMenu;
import javafx.application.Application;
import javafx.scene.Node;
import Controller.ApplicationController;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class SpecialAction {
    public static int bitingFrost(int number, User user){
        for (Card card : user.getBoard().getWeather()) {
            if (card.getName().equals("BitingFrost")) {
                if (user.getBoard().leaderBoost[4] ||
                        user.getOpponentUser().getBoard().leaderBoost[4]) {
                    return (1 + number) / 2;
                } else {
                    return 1;
                }
            }
        }
        for (Card card : user.getOpponentUser().getBoard().getWeather()) {
            if (card.getName().equals("BitingFrost")) {
                if (user.getBoard().leaderBoost[4] ||
                        user.getOpponentUser().getBoard().leaderBoost[4]) {
                    return (1 + number) / 2;
                } else {
                    return 1;
                }
            }
        }
        return number;
    }

    public static int impenetrableFog(int number, User user){
        for (Card card : user.getBoard().getWeather()) {
            if (card.getName().equals("ImpenetrableFog") || card.getName().equals("SkelligeStorm")) {
                if (user.getBoard().leaderBoost[4] ||
                        user.getOpponentUser().getBoard().leaderBoost[4]) {
                    return (1 + number) / 2;
                } else {
                    return 1;
                }
            }
        }
        for (Card card : user.getOpponentUser().getBoard().getWeather()) {
            if (card.getName().equals("ImpenetrableFog") || card.getName().equals("SkelligeStorm")) {
                if (user.getBoard().leaderBoost[4] ||
                        user.getOpponentUser().getBoard().leaderBoost[4]) {
                    return (1 + number) / 2;
                } else {
                    return 1;
                }
            }
        }
        return number;
    }

    public static int torrentialRain(int number, User user){
        for (Card card : user.getBoard().getWeather()) {
            if (card.getName().equals("TorrentialRain") || card.getName().equals("SkelligeStorm")) {
                if (user.getBoard().leaderBoost[4] ||
                        user.getOpponentUser().getBoard().leaderBoost[4]) {
                    return (1 + number) / 2;
                } else {
                    return 1;
                }
            }
        }
        for (Card card : user.getOpponentUser().getBoard().getWeather()) {
            if (card.getName().equals("TorrentialRain") || card.getName().equals("SkelligeStorm")) {
                if (user.getBoard().leaderBoost[4] ||
                        user.getOpponentUser().getBoard().leaderBoost[4]) {
                    return (1 + number) / 2;
                } else {
                    return 1;
                }
            }
        }
        return number;
    }

    public static void decoy(Card decoy, User user) {
        for (Card card : user.getBoard().getCloseCombat()) {
            card.setOnMouseClicked(mouseEvent -> {
                user.getBoard().setHasPlayedOne(true);
                user.getBoard().getHand().remove(decoy);
                decoy.setOnMouseClicked(null);
                GameMenu.getGameMenu().setSizeSmaller(decoy);
                decoyAction(decoy, user);
                user.getBoard().getHand().add(card);
                user.getBoard().getCloseCombat().remove(card);
                user.getBoard().getCloseCombat().add(decoy);
                GameMenu.getGameMenu().updateBorder();
            });
        }
        for (Card card : user.getBoard().getSiege()) {
            card.setOnMouseClicked(mouseEvent -> {
                user.getBoard().setHasPlayedOne(true);
                user.getBoard().getHand().remove(decoy);
                decoy.setOnMouseClicked(null);
                GameMenu.getGameMenu().setSizeSmaller(decoy);
                decoyAction(decoy, user);
                user.getBoard().getHand().add(card);
                user.getBoard().getSiege().remove(card);
                user.getBoard().getSiege().add(decoy);
                GameMenu.getGameMenu().updateBorder();
            });
        }
        for (Card card : user.getBoard().getRanged()) {
            card.setOnMouseClicked(mouseEvent -> {
                user.getBoard().setHasPlayedOne(true);
                user.getBoard().getHand().remove(decoy);
                decoy.setOnMouseClicked(null);
                GameMenu.getGameMenu().setSizeSmaller(decoy);
                decoyAction(decoy, user);
                user.getBoard().getHand().add(card);
                user.getBoard().getRanged().remove(card);
                user.getBoard().getRanged().add(decoy);
                GameMenu.getGameMenu().updateBorder();
            });
        }


    }

    public static void decoyAction(Card decoy, User user) {
        ArrayList<Card> allcards = new ArrayList<>();
        allcards.addAll(user.getBoard().getRanged());
        allcards.addAll(user.getBoard().getSiege());
        allcards.addAll(user.getBoard().getCloseCombat());
        for (Card card : allcards) {
            card.setOnMouseClicked(null);
        }
        if (!User.getLoggedUser().getOpponentUser().isPassed()) {
            for (Card card : user.getBoard().getHand()) {
                card.setOnMouseClicked(null);
            }
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
            if (card.getAbility() == null) continue;
            if (card.getAbility().equals("commander'sHorn") && !card.equals(target)) {
                return 2 * number;
            }
        }
        return number;
    }

    public static boolean clearWeather(User user) {
        for (Card card : user.getBoard().getWeather()) {
            if (card.getName().equals("ClearWeather")) return true;
        }
        for (Card card : user.getOpponentUser().getBoard().getWeather()) {
            if (card.getName().equals("ClearWeather")) return true;
        }
        return false;
    }
}
