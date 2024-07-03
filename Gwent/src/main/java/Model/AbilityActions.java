package Model;

import View.ApplicationController;
import Controller.GameController;
import Model.Factions.Skellige;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class AbilityActions {
    public static void switchAction(Card card, ArrayList<Card> arrayListPlace) {
        if (card.getAbility() != null) {
            if (card.getAbility().contains("spy"))
                spy();
            if (card.getAbility().contains("scorch"))
                scorch(card);
            if (card.getAbility().contains("medic"))
                medic();
            if (card.getAbility().contains("muster"))
                muster(arrayListPlace, card);
            if (card.getAbility().contains("mardoeme"))
                mardroeme(arrayListPlace);
        }

    }

    public static void medic() {
        ArrayList<Card> normalCards = new ArrayList<>();
        for (Card card : User.getTurnUser().getBoard().getBurnedCard()) {
            if(card.getAbility() == null || !(card.getAbility().contains("hero") ||
                    card.getType().equals("weather") || card.getType().equals("spell")))
                normalCards.add(card);
        }
        if (normalCards.isEmpty()) {
            GameController.updateBorder();
            return;
        }

        Pane root = ApplicationController.getRoot();
        ApplicationController.setDisable(root);
        HBox hBox = new HBox(10);
        hBox.setLayoutX(10);
        hBox.setLayoutY(150);
        hBox.setPrefHeight(300);
        hBox.setPrefWidth(1000);
        hBox.setSpacing(100);
        hBox.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(hBox);
        for (Card card : normalCards) {
            ImageView imageView = new ImageView(card.getImage());
            hBox.getChildren().add(imageView);
            imageView.setFitWidth(1400 / normalCards.size());
            if (imageView.getFitWidth() > 300) imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(mouseEvent -> {
                card.setSelect(false);
                root.getChildren().remove(hBox);
                User.getTurnUser().getBoard().getBurnedCard().remove(card);
                User.getTurnUser().getBoard().getHand().add(card);
                card.setOnMouseClicked(null);
                ApplicationController.setEnable(root);
                GameController.updateBorder();
            });
        }
    }


    public static void muster(ArrayList<Card> arrayListPlace, Card card) {
        ArrayList<Card> hand = User.getTurnUser().getBoard().getHand();
        ArrayList<Card> deck = User.getTurnUser().getDeck();
        findMusters(arrayListPlace, card, hand);
        findMusters(arrayListPlace, card, deck);
    }

    private static void findMusters(ArrayList<Card> arrayListPlace, Card card, ArrayList<Card> deckOrHand) {
        if (card.getName().equals("ArachasBehemoth")) {
            for (int i = deckOrHand.size() - 1; i >= 0; i--) {
                if (deckOrHand.get(i).getName().equals("Arachas")) {
                    User.getTurnUser().getBoard().getCloseCombat().add(deckOrHand.get(i));
                    deckOrHand.get(i).setOnMouseClicked(null);
                    deckOrHand.remove(deckOrHand.get(i));
                }
            }
        } else if (card.getName().equals("GaunterO,Dimm")) {
            for (int i = deckOrHand.size() - 1; i >= 0; i--) {
                if (deckOrHand.get(i).getName().equals("GaunterO’DimmDarkness")) {
                    User.getTurnUser().getBoard().getRanged().add(deckOrHand.get(i));
                    deckOrHand.get(i).setOnMouseClicked(null);
                    deckOrHand.remove(deckOrHand.get(i));
                }
            }
        } else if (card.getName().equals("Cerys")) {
            for (int i = deckOrHand.size() - 1; i >= 0; i--) {
                if (deckOrHand.get(i).getName().equals("LightLongship")) {
                    User.getTurnUser().getBoard().getRanged().add(deckOrHand.get(i));
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

    public static void spy() {
        Random random = new Random();
        int num = random.nextInt(0, User.getTurnUser().getDeck().size());
        User.getTurnUser().getBoard().getHand().add(User.getTurnUser().getDeck().get(num));
        User.getTurnUser().getDeck().remove(num);
        num = random.nextInt(0, User.getTurnUser().getDeck().size());
        User.getTurnUser().getBoard().getHand().add(User.getTurnUser().getDeck().get(num));
        User.getTurnUser().getDeck().remove(num);
    }

    public static int tightBond(Card card, ArrayList<Card> unit, int number) {
        if (card.getAbility() == null || !card.getAbility().contains("tightBond")) return number;
        int multi = 0;
        for (Card card1 : unit) {
            if (card1.getPower() == card.getPower() && card1.getAbility() != null &&
                    card1.getAbility().contains("tightBond")) multi++;
        }
        return multi * number;
    }

    public static void scorch(Card scorch) {
        if (scorch.getType().equals("closeCombatUnit")) {
            ArrayList<Card> shouldBurn = new ArrayList<>();
            int maxPointOfCards = 0;
            for (Card card : User.getTurnUser().getOpponentUser().getBoard().getCloseCombat()) {
                if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                if (card.getRealPower() > maxPointOfCards) {
                    maxPointOfCards = card.getRealPower();
                }
            }
            for (Card card : User.getTurnUser().getOpponentUser().getBoard().getCloseCombat()) {
                if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                if (card.getRealPower() == maxPointOfCards) {
                    shouldBurn.add(card);
                }
            }
            User.getTurnUser().getOpponentUser().getBoard().getCloseCombat().removeAll(shouldBurn);
            User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().addAll(shouldBurn);
        } else if (scorch.getType().equals("siegeUnit")) {
            ArrayList<Card> shouldBurn = new ArrayList<>();
            int maxPointOfCards = 0;
            for (Card card : User.getTurnUser().getOpponentUser().getBoard().getSiege()) {
                if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                if (card.getRealPower() > maxPointOfCards) {
                    maxPointOfCards = card.getRealPower();
                }
            }
            for (Card card : User.getTurnUser().getOpponentUser().getBoard().getSiege()) {
                if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                if (card.getRealPower() == maxPointOfCards) {
                    shouldBurn.add(card);
                }
            }
            User.getTurnUser().getOpponentUser().getBoard().getCloseCombat().removeAll(shouldBurn);
            User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().addAll(shouldBurn);
        } else {
            int maxPower = Card.maxPowerFinder(scorch.getType().equals("spell"));
            ArrayList<Card> iterator = new ArrayList<>(User.getTurnUser().getBoard().getCloseCombat());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower && scorch.getType().equals("spell")) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    User.getTurnUser().getBoard().getCloseCombat().remove(card);
                    User.getTurnUser().getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(User.getTurnUser().getBoard().getRanged());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower && scorch.getType().equals("spell")) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    User.getTurnUser().getBoard().getRanged().remove(card);
                    User.getTurnUser().getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(User.getTurnUser().getBoard().getSiege());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower && scorch.getType().equals("spell")) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    User.getTurnUser().getBoard().getSiege().remove(card);
                    User.getTurnUser().getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(User.getTurnUser().getOpponentUser().getBoard().getCloseCombat());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    User.getTurnUser().getOpponentUser().getBoard().getCloseCombat().remove(card);
                    User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(User.getTurnUser().getOpponentUser().getBoard().getRanged());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    User.getTurnUser().getOpponentUser().getBoard().getRanged().remove(card);
                    User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().add(card);
                }
            }
            iterator = new ArrayList<>(User.getTurnUser().getOpponentUser().getBoard().getSiege());
            for (Card card : iterator) {
                if (card.getRealPower() == maxPower) {
                    if (card.getAbility() != null && card.getAbility().contains("hero")) continue;
                    User.getTurnUser().getOpponentUser().getBoard().getSiege().remove(card);
                    User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().add(card);
                }
            }
        }

    }

    public static void mardroeme(ArrayList<Card> arrayListPlace) {
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
