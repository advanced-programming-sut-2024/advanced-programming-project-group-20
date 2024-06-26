package Model;

import Controller.ApplicationController;
import Controller.GameController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Formattable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LeaderBuilder {
    public static Leader nilfgaard(String cardName, Faction faction) {
        return switch (cardName) {
            case "EmperorOfNilfgaard" -> new Leader(faction, "EmperorOfNilfgaard") {
                @Override
                public void action() {
                    User.getTurnUser().getOpponentUser().getLeader().setUsed(true);
                }
            };
            case "HisImperialMajesty" -> new Leader(faction, "HisImperialMajesty") {
                @Override
                public void action() {
                    Pane root = ApplicationController.getRoot();
                    for (Node node : root.getChildren()) {
                        node.setDisable(true);
                    }
                    ArrayList<Card> randCards = new ArrayList<>();
                    randCards.addAll(User.getTurnUser().getOpponentUser().getBoard().getHand());
                    while (randCards.size() > 3) {
                        Random random = new Random();
                        int delete = random.nextInt(0, randCards.size());
                        randCards.remove(delete);
                    }
                    ArrayList<ImageView> randImages = new ArrayList<>();
                    for (int i = 0; i < randCards.size(); i++) {
                        ImageView imageView = new ImageView();
                        imageView.setImage(randCards.get(i).getImage());
                        imageView.setFitHeight(root.getHeight() / 2);
                        imageView.setFitWidth(0.53 * imageView.getFitHeight());
                        imageView.setY(root.getHeight() / 5);
                        imageView.setX((root.getWidth() / (randCards.size() + 2)) * (i + 1));
                        randImages.add(imageView);
                        root.getChildren().add(imageView);
                    }
                    root.setOnMouseClicked(mouseEvent -> {
                        for (Node node : root.getChildren()) {
                            node.setDisable(false);
                        }
                        root.getChildren().removeAll(randImages);
                        root.setOnMouseClicked(null);
                    });
                }
            };
            case "TheRelentless" -> new Leader(faction, "TheRelentless") {
                @Override
                public void action() {
                    Pane root = ApplicationController.getRoot();
                    for (Node node : root.getChildren()) {
                        node.setDisable(true);
                    }
                    HBox hBox = new HBox(5);
                    hBox.setMaxWidth(root.getWidth());
                    hBox.setPrefHeight(root.getHeight());
                    for (Card card : User.getTurnUser().getOpponentUser().getBoard().getBurnedCard()) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        ImageView imageView = new ImageView();
                        imageView.setImage(card.getImage());
                        imageView.setOnMouseClicked(mouseEvent -> {
                            User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().remove(card);
                            User.getTurnUser().getBoard().getHand().add(card);
                            GameController.updateBorder();
                            root.getChildren().remove(hBox);
                            for (Node node : root.getChildren()) {
                                node.setDisable(false);
                            }
                        });
                        hBox.getChildren().add(imageView);
                    }
                    root.getChildren().add(hBox);
                }
            };
            case "InvaderOfNorth" -> new Leader(faction, "InvaderOfNorth") {
                @Override
                public void action() {
                    Random random = new Random();
                    ArrayList<Card> nonHeroCards = new ArrayList<>();
                    for (Card card1 : User.getTurnUser().getBoard().getBurnedCard()){
                        if (card1.getAbility() != null){
                            if (card1.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        nonHeroCards.add(card1);
                    }
                    int randomNumber = random.nextInt(0,nonHeroCards.size());
                    Card card = nonHeroCards.get(randomNumber);
                    User.getTurnUser().getBoard().getBurnedCard().remove(card);
                    User.getTurnUser().getBoard().getHand().add(card);
                    GameController.updateBorder();
                    nonHeroCards = new ArrayList<>();
                    for (Card card1 : User.getTurnUser().getBoard().getBurnedCard()){
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        nonHeroCards.add(card1);
                    }
                    randomNumber = random.nextInt(0,nonHeroCards.size());
                    card = nonHeroCards.get(randomNumber);
                    User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().remove(card);
                    User.getTurnUser().getOpponentUser().getBoard().getHand().add(card);
                }
            };
            case "TheWhiteFlame" -> new Leader(faction, "TheWhiteFlame") {
                @Override
                public void action() {
                    for (Card card : User.getTurnUser().getDeck()) {
                        if (card.getName().equals("TorrentialRain")) {
                            User.getTurnUser().getDeck().remove(card);
                            User.getTurnUser().getBoard().getWeather().add(card);
                            GameController.updateBorder();
                        }
                    }
                }
            };

            default -> null;
        };
    }

    public static Leader northernRealms(String cardName, Faction faction) {
        return switch (cardName) {
            case "LordOfCommanderOfTheNorth" -> new Leader(faction, "LordOfCommanderOfTheNorth") {
                @Override
                public void action() {
                    Card mostPowerfulCard = null;
                    int n = 0;
                    for (Card card : User.getTurnUser().getOpponentUser().getBoard().getSiege()){
                        n += card.getPower();
                        if (mostPowerfulCard == null) {
                            mostPowerfulCard = card;
                        }
                        else if (mostPowerfulCard.getPower() < card.getPower()) {
                            mostPowerfulCard = card;
                        }
                    }
                    if (n > 10) {
                        User.getTurnUser().getOpponentUser().getBoard().getSiege().remove(mostPowerfulCard);
                    }
                }
            };
            case "KingOfTemperia" -> new Leader(faction, "KingOfTemperia") {
                @Override
                public void action() {
                    User.getTurnUser().getBoard().leaderBoost[0] = true;
                }
            };
            case "TheSteal-Forged" -> new Leader(faction, "TheSteal-Forged") {
                @Override
                public void action() {
                    for (Card card : User.getTurnUser().getBoard().getWeather()) {
                        User.getTurnUser().getBoard().getWeather().remove(card);
                        User.getTurnUser().getBoard().getBurnedCard().add(card);
                    }
                    for (Card card : User.getTurnUser().getOpponentUser().getBoard().getWeather()) {
                        User.getTurnUser().getOpponentUser().getBoard().getWeather().remove(card);
                        User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().add(card);
                    }
                }
            };
            case "TheSiegemaster" -> new Leader(faction, "TheSiegemaster") {
                @Override
                public void action() {
                    for (Card card : User.getTurnUser().getDeck()) {
                        if (card.getName().equals("ImpenetrableFog")) {
                            User.getTurnUser().getDeck().remove(card);
                            User.getTurnUser().getBoard().getWeather().add(card);
                            GameController.updateBorder();
                            return;
                        }
                    }
                }
            };
            case "SunOfMedell" -> new Leader(faction, "SunOfMedell") {
                @Override
                public void action() {
                    Card mostPowerfulCard = null;
                    int n = 0;
                    for (Card card : User.getTurnUser().getOpponentUser().getBoard().getRanged()){
                        n += card.getPower();
                        if (mostPowerfulCard == null) {
                            mostPowerfulCard = card;
                        }
                        else if (mostPowerfulCard.getPower() < card.getPower()) {
                            mostPowerfulCard = card;
                        }
                    }
                    if (n > 10) {
                        User.getTurnUser().getOpponentUser().getBoard().getRanged().remove(mostPowerfulCard);
                    }
                }
            };

            default -> null;
        };
    }

    public static Leader monsters(String cardName, Faction faction) {
        return switch (cardName) {
            case "KingOfTheWildHunt" -> new Leader(faction, "KingOfTheWildHunt") {
                @Override
                public void action() {
                    Pane root = ApplicationController.getRoot();
                    for (Node node : root.getChildren()) {
                        node.setDisable(true);
                    }
                    HBox hBox = new HBox(5);
                    hBox.setMaxWidth(root.getWidth());
                    hBox.setPrefHeight(root.getHeight());
                    for (Card card : User.getTurnUser().getBoard().getBurnedCard()) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        ImageView imageView = new ImageView();
                        imageView.setImage(card.getImage());
                        imageView.setOnMouseClicked(mouseEvent -> {
                            User.getTurnUser().getBoard().getBurnedCard().remove(card);
                            User.getTurnUser().getBoard().getHand().add(card);
                            GameController.updateBorder();
                            root.getChildren().remove(hBox);
                            for (Node node : root.getChildren()) {
                                node.setDisable(false);
                            }
                        });
                        hBox.getChildren().add(imageView);
                    }
                    root.getChildren().add(hBox);
                }
            };
            case "CommanderOfRedRiders" -> new Leader(faction, "CommanderOfRedRiders") {
                @Override
                public void action() {
                    ArrayList<Card> allWeatherCards = new ArrayList<>();
                    for (Card card : User.getTurnUser().getDeck()) {
                        if (card.getName().equals("ImpenetrableFog")) {
                            allWeatherCards.add(card);
                            break;
                        }
                    }
                    for (Card card : User.getTurnUser().getDeck()) {
                        if (card.getName().equals("BitingFrost")) {
                            allWeatherCards.add(card);
                            break;
                        }
                    }
                    for (Card card : User.getTurnUser().getDeck()) {
                        if (card.getName().equals("TorrentialRain")) {
                            allWeatherCards.add(card);
                            break;
                        }
                    }
                    if (allWeatherCards.isEmpty()) return;
                    Pane root = ApplicationController.getRoot();
                    for (Node node : root.getChildren()) {
                        node.setDisable(true);
                    }
                    ArrayList<ImageView> imageViews = new ArrayList<>();
                    for (int i = 0; i < allWeatherCards.size(); i++) {
                        ImageView imageView = new ImageView();
                        imageView.setImage(allWeatherCards.get(i).getImage());
                        imageView.setFitHeight(root.getHeight() / 2);
                        imageView.setFitWidth(0.53 * imageView.getFitHeight());
                        imageView.setY(root.getHeight() / 5);
                        imageView.setX((root.getWidth() / (allWeatherCards.size() + 2)) * (i + 1));
                        imageViews.add(imageView);
                        root.getChildren().add(imageView);
                    }
                    for (int i = 0; i < allWeatherCards.size(); i++) {
                        int finalI = i;
                        imageViews.get(i).setOnMouseClicked(mouseEvent -> {
                            root.getChildren().removeAll(imageViews);
                            User.getTurnUser().getDeck().remove(allWeatherCards.get(finalI));
                            User.getTurnUser().getBoard().addWeather(allWeatherCards.get(finalI));
                            for (Node node : root.getChildren()) {
                                node.setDisable(false);
                            }
                        });
                    }
                }
            };
            case "DestroyerOfWorlds" -> new Leader(faction, "DestroyerOfWorlds") {
                @Override
                public void action() {
                    ApplicationController.alert("Burn", "Choose 2 Card from your hand for burn");
                    AtomicInteger choose = new AtomicInteger(0);
                    for (Card card : User.getTurnUser().getBoard().getHand()) {
                        card.setOnMouseClicked(mouseEvent -> {
                            User.getTurnUser().getBoard().getHand().remove(card);
                            User.getTurnUser().getBoard().getBurnedCard().add(card);
                            GameController.updateBorder();
                            DestroyerOfWorlds2();
                        });
                    }
                }

                private void DestroyerOfWorlds2() {
                    for (Card card : User.getTurnUser().getBoard().getHand()) {
                        card.setOnMouseClicked(mouseEvent -> {
                            User.getTurnUser().getBoard().getHand().remove(card);
                            User.getTurnUser().getBoard().getBurnedCard().add(card);
                            GameController.updateBorder();
                            showDeckForChoose();
                        });
                    }
                }
            };
            case "BringerOfDeath" -> new Leader(faction, "BringerOfDeath") {
                @Override
                public void action() {
                    User.getTurnUser().getBoard().leaderBoost[2] = true;
                }
            };
            case "TheTreacherous" -> new Leader(faction, "TheTreacherous") {
                @Override
                public void action() {
                    User.getTurnUser().getBoard().leaderBoost[3] = true;
                }
            };

            default -> null;
        };
    }

    public static Leader scoiaTael(String cardName, Faction faction) {
        return switch (cardName) {
            case "PurebloodElf" -> new Leader(faction, "PurebloodElf") {
                @Override
                public void action() {
                    for (Card card : User.getTurnUser().getDeck()) {
                        if (card.getName().equals("BitingFrost")) {
                            User.getTurnUser().getDeck().remove(card);
                            User.getTurnUser().getBoard().getWeather().add(card);
                            GameController.updateBorder();
                            return;
                        }
                    }
                }
            };
            case "DaisyOfTheValley" -> new Leader(faction, "DaisyOfTheValley") {
                @Override
                public void action() {
                    ArrayList<Card> nonHero = new ArrayList<>();
                    for (Card card : User.getTurnUser().getDeck()) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        nonHero.add(card);
                    }
                    Random random = new Random();
                    User.getTurnUser().getBoard().getHand().add(nonHero.get(random.nextInt(0, nonHero.size())));
                    GameController.updateBorder();
                }
            };
            case "TheBeautiful" -> new Leader(faction, "TheBeautiful") {
                @Override
                public void action() {
                    User.getTurnUser().getBoard().leaderBoost[1] = true;
                }
            };
            case "HopeOfTheAenSeidhe" -> new Leader(faction, "HopeOfTheAenSeidhe") {
                @Override
                public void action() {
                    for (Card card : User.getTurnUser().getBoard().getCloseCombat()) {
                        if (!card.getType().equals("agileUnit") || card.getAbility().contains("hero")) continue;
                        int currentPower = card.getPower(), secondPower = card.getPower();
                        currentPower = SpecialAction.bitingFrost(currentPower);
                        secondPower = SpecialAction.impenetrableFog(secondPower);
                        if (User.getTurnUser().getBoard().leaderBoost[2]){
                            currentPower *=2;
                        } else {
                            currentPower = SpecialAction.commanderHorn(User.getTurnUser().getBoard().getCloseCombat(),card,currentPower);
                        }
                        if (User.getTurnUser().getBoard().leaderBoost[1]){
                            secondPower *=2;
                        } else {
                            secondPower = SpecialAction.commanderHorn(User.getTurnUser().getBoard().getRanged(),card,secondPower);
                        }
                        currentPower = SpecialAction.moralBoost(User.getTurnUser().getBoard().getCloseCombat(),card,currentPower);
                        secondPower = SpecialAction.moralBoost(User.getTurnUser().getBoard().getRanged(),card,secondPower);
                        if (secondPower > currentPower) {
                            User.getTurnUser().getBoard().getCloseCombat().remove(card);
                            User.getTurnUser().getBoard().getRanged().add(card);
                        }
                    }
                    for (Card card : User.getTurnUser().getBoard().getRanged()) {
                        if (!card.getType().equals("agileUnit") || card.getAbility().contains("hero")) continue;
                        int currentPower = card.getPower(), secondPower = card.getPower();
                        currentPower = SpecialAction.impenetrableFog(currentPower);
                        secondPower = SpecialAction.bitingFrost(secondPower);
                        if (User.getTurnUser().getBoard().leaderBoost[1]){
                            currentPower *=2;
                        } else {
                            currentPower = SpecialAction.commanderHorn(User.getTurnUser().getBoard().getRanged(),card,currentPower);
                        }
                        if (User.getTurnUser().getBoard().leaderBoost[2]){
                            secondPower *=2;
                        } else {
                            secondPower = SpecialAction.commanderHorn(User.getTurnUser().getBoard().getCloseCombat(),card,secondPower);
                        }
                        currentPower = SpecialAction.moralBoost(User.getTurnUser().getBoard().getRanged(),card,currentPower);
                        secondPower = SpecialAction.moralBoost(User.getTurnUser().getBoard().getCloseCombat(),card,secondPower);
                        if (secondPower > currentPower) {
                            User.getTurnUser().getBoard().getRanged().remove(card);
                            User.getTurnUser().getBoard().getCloseCombat().add(card);
                        }
                    }
                 }
            };
            case "QueenOfDolBlathanna" -> new Leader(faction, "QueenOfDolBlathanna") {
                @Override
                public void action() {
                    Card mostPowerfulCard = null;
                    int n = 0;
                    for (Card card : User.getTurnUser().getOpponentUser().getBoard().getCloseCombat()){
                        n += card.getPower();
                        if (mostPowerfulCard == null) {
                            mostPowerfulCard = card;
                        }
                        else if (mostPowerfulCard.getPower() < card.getPower()) {
                            mostPowerfulCard = card;
                        }
                    }
                    if (n > 10) {
                        User.getTurnUser().getOpponentUser().getBoard().getCloseCombat().remove(mostPowerfulCard);
                    }
                }
            };

            default -> null;
        };
    }

    public static Leader skellige(String cardName, Faction faction) {
        return switch (cardName) {
            case "CrachAnCraite" -> new Leader(faction, "CrachAnCraite") {
                @Override
                public void action() {
                    ArrayList<Card> allNonHeroes = new ArrayList<>();
                    for (Card card : User.getTurnUser().getBoard().getBurnedCard()) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        allNonHeroes.add(card);
                    }
                    for (Card card : User.getTurnUser().getOpponentUser().getBoard().getBurnedCard()) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        allNonHeroes.add(card);
                    }
                    Random random = new Random();
                    for (int i = 0; i < allNonHeroes.size() / 2; i++) {
                        int num = random.nextInt(0, allNonHeroes.size());
                        User.getTurnUser().getBoard().getHand().add(allNonHeroes.get(num));
                        allNonHeroes.remove(num);
                    }
                    User.getTurnUser().getOpponentUser().getBoard().getHand().addAll(allNonHeroes);
                    GameController.updateBorder();
                }
            };
            case "KingBran" -> new Leader(faction, "KingBran") {
                @Override
                public void action() {
                    User.getTurnUser().getBoard().leaderBoost[4] = true;
                }
            };

            default -> null;
        };
    }
    public static void showDeckForChoose() {
        Pane root = ApplicationController.getRoot();
        for (Node node : root.getChildren()) {
            node.setDisable(true);
        }
        HBox hBox = new HBox(5);
        hBox.setMaxWidth(root.getWidth());
        hBox.setPrefHeight(root.getHeight());
        for (Card card : User.getTurnUser().getDeck()) {
            if (card.getAbility() != null){
                if (card.getAbility().contains("hero")) {
                    continue;
                }
            }
            ImageView imageView = new ImageView();
            imageView.setImage(card.getImage());
            imageView.setFitWidth(hBox.getMaxWidth() / (User.getTurnUser().getDeck().size() + 2));
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(mouseEvent -> {
                User.getTurnUser().getDeck().remove(card);
                User.getTurnUser().getBoard().getHand().add(card);
                GameController.updateBorder();
                root.getChildren().remove(hBox);
                for (Node node : root.getChildren()) {
                    node.setDisable(false);
                }
                GameController.updateCardEvent();
            });
            hBox.getChildren().add(imageView);
        }
        root.getChildren().add(hBox);
    }
}
