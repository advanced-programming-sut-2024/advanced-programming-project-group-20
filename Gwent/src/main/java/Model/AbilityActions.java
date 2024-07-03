package Model;

import Controller.ApplicationController;
import Model.Factions.Skellige;
import View.GameMenu;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AbilityActions {
    public static void switchAction(Card card, ArrayList<Card> arrayListPlace, User user) {
        if (card.getAbility() != null) {
            if (card.getAbility().contains("spy"))
                spy(user);
            if (card.getAbility().contains("scorch"))
                scorch(card,user);
            if (card.getAbility().contains("medic"))
                medic(user);
            if (card.getAbility().contains("muster"))
                muster(arrayListPlace, card, user);
            if (card.getAbility().contains("mardoeme"))
                mardroeme(arrayListPlace, user);
        }

    }

    public static void medic(User user) {
        ArrayList<Card> normalCards = new ArrayList<>();
        for (Card card : user.getBoard().getBurnedCard()) {
            if(card.getAbility() == null || !(card.getAbility().contains("hero") ||
                    card.getType().equals("weather") || card.getType().equals("spell")))
                normalCards.add(card);
        }
        if (normalCards.isEmpty())
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
        for (Card card : normalCards) {
            if (!hBox.getChildren().contains(card))
                hBox.getChildren().add(card);
            setSizeSmaller(card, 2.5);
            card.setOnMouseClicked(mouseEvent -> {
                card.setSelect(false);
                root.getChildren().remove(hBox);
                user.getBoard().getBurnedCard().remove(card);
                user.getBoard().getHand().add(card);
                card.setOnMouseClicked(null);
                for (Node node : hBox.getChildren()) {
                    if (((Pane) node).getHeight() > 100) {
                        setSizeSmaller((Card) node, 0.4);
                    }
                }
                ApplicationController.setEnable(root);
                GameMenu.getGameMenu().updateBorder();
            });
        }
    }

    private static void setSizeSmaller(Card card, double scale) {
        card.setPrefWidth(card.getPrefWidth() * scale);
        card.setPrefHeight(card.getPrefHeight() * scale);
        ((Rectangle) card.getChildren().get(0)).setHeight(((Rectangle) card.getChildren().get(0)).getHeight() * scale);
        ((Rectangle) card.getChildren().get(0)).setWidth(((Rectangle) card.getChildren().get(0)).getWidth() * scale);
        card.setSelect(false);
    }


    public static void muster(ArrayList<Card> arrayListPlace, Card card, User user) {
        ArrayList<Card> hand = user.getBoard().getHand();
        ArrayList<Card> deck = user.getDeck();
        findMusters(arrayListPlace, card, hand, user);
        findMusters(arrayListPlace, card, deck, user);
    }

    private static void findMusters(ArrayList<Card> arrayListPlace, Card card, ArrayList<Card> deckOrHand, User user) {
        if (card.getName().equals("ArachasBehemoth")) {
            for (int i = deckOrHand.size() - 1; i >= 0; i--) {
                if (deckOrHand.get(i).getName().equals("Arachas")) {
                    user.getBoard().getCloseCombat().add(deckOrHand.get(i));
                    deckOrHand.get(i).setOnMouseClicked(null);
                    deckOrHand.remove(deckOrHand.get(i));
                }
            }
        } else if (card.getName().equals("GaunterO,Dimm")) {
            for (int i = deckOrHand.size() - 1; i >= 0; i--) {
                if (deckOrHand.get(i).getName().equals("GaunterO’DimmDarkness")) {
                    user.getBoard().getRanged().add(deckOrHand.get(i));
                    deckOrHand.get(i).setOnMouseClicked(null);
                    deckOrHand.remove(deckOrHand.get(i));
                }
            }
        } else if (card.getName().equals("Cerys")) {
            for (int i = deckOrHand.size() - 1; i >= 0; i--) {
                if (deckOrHand.get(i).getName().equals("LightLongship")) {
                    user.getBoard().getRanged().add(deckOrHand.get(i));
                    deckOrHand.get(i).setOnMouseClicked(null);
                    deckOrHand.remove(deckOrHand.get(i));
                }
            }
        } else {
            String[] split = card.getName().split("[A-Z]");
            for (int i = deckOrHand.size() - 1; i >= 0; i--) {
                String[] splitName = deckOrHand.get(i).getName().split("[A-Z]");
                if (split.length == splitName.length && splitName[1].equals(split[1])) {
                    arrayListPlace.add(deckOrHand.get(i));
                    deckOrHand.get(i).setOnMouseClicked(null);
                    deckOrHand.remove(deckOrHand.get(i));
                }
            }
        }
    }

    public static void spy(User user) {
        Random random = new Random();
        int num = random.nextInt(0, user.getDeck().size());
        user.getBoard().getHand().add(user.getDeck().get(num));
        user.getDeck().remove(num);
        num = random.nextInt(0, user.getDeck().size());
        user.getBoard().getHand().add(user.getDeck().get(num));
        user.getDeck().remove(num);
    }

    public static int tightBond(Card card, ArrayList<Card> unit, int number) {
        if (card.getAbility() == null || !card.getAbility().contains("tightBond")) return number;
        int multi = 0;
        for (Card card1 : unit) {
            if (card1.getPower() == card.getPower()) multi++;
        }
        return multi * number;
    }

    public static void scorch(Card scorch, User user) {
        if (scorch.getType().equals("closeCombatUnit")) {
            ArrayList<Card> shouldBurn = new ArrayList<>();
            int maxPointOfCards = 0;
            for (Card card : user.getOpponentUser().getBoard().getCloseCombat()) {
                if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                if (card.getRealPower() > maxPointOfCards) {
                    maxPointOfCards = card.getRealPower();
                }
            }
            for (Card card : user.getOpponentUser().getBoard().getCloseCombat()) {
                if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                if (card.getRealPower() == maxPointOfCards) {
                    shouldBurn.add(card);
                }
            }
            user.getOpponentUser().getBoard().getCloseCombat().removeAll(shouldBurn);
            user.getOpponentUser().getBoard().getBurnedCard().addAll(shouldBurn);
        } else if (scorch.getType().equals("siegeUnit")) {
            ArrayList<Card> shouldBurn = new ArrayList<>();
            int maxPointOfCards = 0;
            for (Card card : user.getOpponentUser().getBoard().getSiege()) {
                if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                if (card.getRealPower() > maxPointOfCards) {
                    maxPointOfCards = card.getRealPower();
                }
            }
            for (Card card : user.getOpponentUser().getBoard().getSiege()) {
                if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                if (card.getRealPower() == maxPointOfCards) {
                    shouldBurn.add(card);
                }
            }
            user.getOpponentUser().getBoard().getCloseCombat().removeAll(shouldBurn);
            user.getOpponentUser().getBoard().getBurnedCard().addAll(shouldBurn);
        } else {
            int maxPower = Card.maxPowerFinder(scorch.getType().equals("spell"));
            ArrayList<Card> iterator = new ArrayList<>(user.getBoard().getCloseCombat());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower && scorch.getType().equals("spell")) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    user.getBoard().getCloseCombat().remove(card);
                    user.getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(user.getBoard().getRanged());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower && scorch.getType().equals("spell")) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    user.getBoard().getRanged().remove(card);
                    user.getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(user.getBoard().getSiege());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower && scorch.getType().equals("spell")) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    user.getBoard().getSiege().remove(card);
                    user.getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(user.getOpponentUser().getBoard().getCloseCombat());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    user.getOpponentUser().getBoard().getCloseCombat().remove(card);
                    user.getOpponentUser().getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(user.getOpponentUser().getBoard().getRanged());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    user.getOpponentUser().getBoard().getRanged().remove(card);
                    user.getOpponentUser().getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(user.getOpponentUser().getBoard().getSiege());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    user.getOpponentUser().getBoard().getSiege().remove(card);
                    user.getOpponentUser().getBoard().getBurnedCard().add(card);
                }
            }
        }

    }

    public static void mardroeme(ArrayList<Card> arrayListPlace, User user) {
        Skellige skellige = new Skellige();
        for (int i = arrayListPlace.size() - 1; i >= 0; i--) {
            if (arrayListPlace.get(i).getAbility().contains("berserker"))
                arrayListPlace.set(i, CardBuilder.skellige("YoungVidkaarl", skellige));
        }

    }

    public static ArrayList<Card> transformers(User user) {
        ArrayList<Card> transformers = new ArrayList<>();
        for (Card card : user.getBoard().getCloseCombat()) {
            if (card.getAbility() == null) continue;
            if (card.getAbility().contains("transformers")) {
                transformers.add(CardBuilder.skellige("Hemdall" , new Skellige()));
            }
        }
        for (Card card : user.getBoard().getRanged()) {
            if (card.getAbility() == null) continue;
            if (card.getAbility().contains("transformers")) {
                transformers.add(CardBuilder.neutral("Chort"));
            }
        }
        return transformers;
    }
}
