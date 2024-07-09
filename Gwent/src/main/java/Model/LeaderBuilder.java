package Model;

import View.ApplicationController;
import View.GameMenu;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LeaderBuilder {
    public static Leader nilfgaard(String cardName, Faction faction) {
        return switch (cardName) {
            case "EmperorOfNilfgaard" -> new Leader(faction, "EmperorOfNilfgaard") {
                @Override
                public void action(User user) {
                    user.getOpponentUser().getLeader().setUsed(true);
                }
            };
            case "HisImperialMajesty" -> new Leader(faction, "HisImperialMajesty") {
                @Override
                public void action(User user) {
                    Pane root = ApplicationController.getRoot();
                    for (Node node : root.getChildren()) {
                        node.setDisable(true);
                    }
                    ArrayList<Card> randCards = new ArrayList<>();
                    randCards.addAll(user.getOpponentUser().getBoard().getHand());
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
                public void action(User user) {
                    Pane root = ApplicationController.getRoot();
                    for (Node node : root.getChildren()) {
                        node.setDisable(true);
                    }
                    HBox hBox = new HBox(5);
                    hBox.setMaxWidth(root.getWidth());
                    hBox.setPrefHeight(root.getHeight());
                    for (Card card : user.getOpponentUser().getBoard().getBurnedCard()) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        ImageView imageView = new ImageView();
                        imageView.setImage(card.getImage());
                        imageView.setOnMouseClicked(mouseEvent -> {
                            user.getOpponentUser().getBoard().getBurnedCard().remove(card);
                            user.getBoard().getHand().add(card);
                            GameMenu.getGameMenu().updateBorder();
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
                public void action(User user) {
                    Random random = new Random();
                    ArrayList<Card> nonHeroCards = new ArrayList<>();
                    for (Card card1 : user.getBoard().getBurnedCard()){
                        if (card1.getAbility() != null){
                            if (card1.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        nonHeroCards.add(card1);
                    }
                    if (!nonHeroCards.isEmpty()) {
                        int randomNumber = random.nextInt(0,nonHeroCards.size());
                        Card card = nonHeroCards.get(randomNumber);
                        user.getBoard().getBurnedCard().remove(card);
                        user.getBoard().getHand().add(card);
                    }
                    nonHeroCards = new ArrayList<>();
                    for (Card card1 : user.getOpponentUser().getBoard().getBurnedCard()){
                        if (card1.getAbility() != null){
                            if (card1.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        nonHeroCards.add(card1);
                    }
                    if (!nonHeroCards.isEmpty()) {
                        int randomNumber = random.nextInt(0,nonHeroCards.size());
                        Card card = nonHeroCards.get(randomNumber);
                        user.getOpponentUser().getBoard().getBurnedCard().remove(card);
                        user.getOpponentUser().getBoard().getHand().add(card);
                    }
                    GameMenu.getGameMenu().updateBorder();
                }
            };
            case "TheWhiteFlame" -> new Leader(faction, "TheWhiteFlame") {
                @Override
                public void action(User user) {
                    for (Card card : user.getDeck()) {
                        if (card.getName().equals("TorrentialRain")) {
                            user.getDeck().remove(card);
                            user.getBoard().getWeather().add(card);
                            GameMenu.getGameMenu().updateBorder();
                            return;
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
                public void action(User user) {
                    Card mostPowerfulCard = null;
                    int n = 0;
                    for (Card card : user.getOpponentUser().getBoard().getSiege()){
                        n += card.getRealPower();
                        if (mostPowerfulCard == null) {
                            mostPowerfulCard = card;
                        }
                        else if (mostPowerfulCard.getRealPower() < card.getRealPower()) {
                            mostPowerfulCard = card;
                        }
                    }
                    if (n > 10) {
                        for (Card card : user.getOpponentUser().getBoard().getSiege()) {
                            if (card.getRealPower() == mostPowerfulCard.getRealPower()) {
                                user.getOpponentUser().getBoard().getSiege().remove(card);
                                user.getOpponentUser().getBoard().getBurnedCard().add(card);
                            }
                        }

                    }
                }
            };
            case "KingOfTemperia" -> new Leader(faction, "KingOfTemperia") {
                @Override
                public void action(User user) {
                    user.getBoard().leaderBoost[0] = true;
                }
            };
            case "TheSteal-Forged" -> new Leader(faction, "TheSteal-Forged") {
                @Override
                public void action(User user) {
                    for (Card card : user.getBoard().getWeather()) {
                        user.getBoard().getWeather().remove(card);
                        user.getBoard().getBurnedCard().add(card);
                    }
                    for (Card card : user.getOpponentUser().getBoard().getWeather()) {
                        user.getOpponentUser().getBoard().getWeather().remove(card);
                        user.getOpponentUser().getBoard().getBurnedCard().add(card);
                    }
                }
            };
            case "TheSiegemaster" -> new Leader(faction, "TheSiegemaster") {
                @Override
                public void action(User user) {
                    for (Card card : user.getDeck()) {
                        if (card.getName().equals("ImpenetrableFog")) {
                            user.getDeck().remove(card);
                            user.getBoard().getWeather().add(card);
                            GameMenu.getGameMenu().updateBorder();
                            return;
                        }
                    }
                }
            };
            case "SunOfMedell" -> new Leader(faction, "SunOfMedell") {
                @Override
                public void action(User user) {
                    Card mostPowerfulCard = null;
                    int n = 0;
                    for (Card card : user.getOpponentUser().getBoard().getRanged()){
                        n += card.getRealPower();
                        if (mostPowerfulCard == null) {
                            mostPowerfulCard = card;
                        }
                        else if (mostPowerfulCard.getRealPower() < card.getRealPower()) {
                            mostPowerfulCard = card;
                        }
                    }
                    if (n > 10) {
                        for (Card card : user.getOpponentUser().getBoard().getRanged()) {
                            if (card.getRealPower() == mostPowerfulCard.getRealPower()) {
                                user.getOpponentUser().getBoard().getRanged().remove(card);
                                user.getOpponentUser().getBoard().getBurnedCard().add(card);
                            }
                        }

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
                public void action(User user) {
                    Pane root = ApplicationController.getRoot();
                    for (Node node : root.getChildren()) {
                        node.setDisable(true);
                    }
                    HBox hBox = new HBox(5);
                    hBox.setMaxWidth(root.getWidth());
                    hBox.setPrefHeight(root.getHeight());
                    for (Card card : user.getBoard().getBurnedCard()) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        ImageView imageView = new ImageView();
                        imageView.setFitWidth(hBox.getMaxWidth() / user.getBoard().getBurnedCard().size() - 20);
                        imageView.setPreserveRatio(true);
                        imageView.setImage(card.getImage());
                        imageView.setOnMouseClicked(mouseEvent -> {
                            user.getBoard().getBurnedCard().remove(card);
                            user.getBoard().getHand().add(card);
                            GameMenu.getGameMenu().updateCardEvent();
                            root.getChildren().remove(hBox);
                            for (Node node : root.getChildren()) {
                                node.setDisable(false);
                            }
                        });
                        hBox.getChildren().add(imageView);
                    }
                    if (hBox.getChildren().isEmpty()) {
                        for (Node node : root.getChildren()) {
                            node.setDisable(false);
                        }
                    }
                    else
                        root.getChildren().add(hBox);
                }
            };
            case "CommanderOfRedRiders" -> new Leader(faction, "CommanderOfRedRiders") {
                @Override
                public void action(User user) {
                    ArrayList<Card> allWeatherCards = new ArrayList<>();
                    for (Card card : user.getDeck()) {
                        if (card.getName().equals("ImpenetrableFog")) {
                            allWeatherCards.add(card);
                            break;
                        }
                    }
                    for (Card card : user.getDeck()) {
                        if (card.getName().equals("BitingFrost")) {
                            allWeatherCards.add(card);
                            break;
                        }
                    }
                    for (Card card : user.getDeck()) {
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
                            user.getDeck().remove(allWeatherCards.get(finalI));
                            user.getBoard().addWeather(allWeatherCards.get(finalI));
                            for (Node node : root.getChildren()) {
                                node.setDisable(false);
                            }
                        });
                    }
                }
            };
            case "DestroyerOfWorlds" -> new Leader(faction, "DestroyerOfWorlds") {
                @Override
                public void action(User user) {
                    ApplicationController.alert("Burn", "Choose 2 Card from your hand for burn");
                    AtomicInteger choose = new AtomicInteger(0);
                    for (Card card : user.getBoard().getHand()) {
                        card.setOnMouseClicked(mouseEvent -> {
                            user.getBoard().getHand().remove(card);
                            user.getBoard().getBurnedCard().add(card);
                            GameMenu.getGameMenu().updateBorder();
                            DestroyerOfWorlds2(user);
                        });
                    }
                }

                private void DestroyerOfWorlds2(User user) {
                    for (Card card : user.getBoard().getHand()) {
                        card.setOnMouseClicked(mouseEvent -> {
                            user.getBoard().getHand().remove(card);
                            user.getBoard().getBurnedCard().add(card);
                            GameMenu.getGameMenu().updateBorder();
                            showDeckForChoose(user);
                        });
                    }
                }
            };
            case "BringerOfDeath" -> new Leader(faction, "BringerOfDeath") {
                @Override
                public void action(User user) {
                    user.getBoard().leaderBoost[2] = true;
                }
            };
            case "TheTreacherous" -> new Leader(faction, "TheTreacherous") {
                @Override
                public void action(User user) {
                    user.getBoard().leaderBoost[3] = true;
                }
            };

            default -> null;
        };
    }

    public static Leader scoiaTael(String cardName, Faction faction) {
        return switch (cardName) {
            case "PurebloodElf" -> new Leader(faction, "PurebloodElf") {
                @Override
                public void action(User user) {
                    for (Card card : user.getDeck()) {
                        if (card.getName().equals("BitingFrost")) {
                            user.getDeck().remove(card);
                            user.getBoard().getWeather().add(card);
                            GameMenu.getGameMenu().updateBorder();
                            return;
                        }
                    }
                }
            };
            case "DaisyOfTheValley" -> new Leader(faction, "DaisyOfTheValley") {
                @Override
                public void action(User user) {
                    ArrayList<Card> nonHero = new ArrayList<>();
                    for (Card card : user.getDeck()) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        nonHero.add(card);
                    }
                    Random random = new Random();
                    user.getBoard().getHand().add(nonHero.get(random.nextInt(0, nonHero.size())));
                    GameMenu.getGameMenu().updateBorder();
                }
            };
            case "TheBeautiful" -> new Leader(faction, "TheBeautiful") {
                @Override
                public void action(User user) {
                    user.getBoard().leaderBoost[1] = true;
                }
            };
            case "HopeOfTheAenSeidhe" -> new Leader(faction, "HopeOfTheAenSeidhe") {
                @Override
                public void action(User user) {
                    ArrayList<Card> cards = new ArrayList<>(user.getBoard().getCloseCombat());
                    for (Card card : cards) {
                        if (!card.getType().equals("agileUnit") || card.getAbility().contains("hero")) continue;
                        int currentPower = card.getPower(), secondPower = card.getPower();
                        currentPower = SpecialAction.bitingFrost(currentPower, user);
                        secondPower = SpecialAction.impenetrableFog(secondPower, user);
                        if (user.getBoard().leaderBoost[2]){
                            currentPower *=2;
                        } else {
                            currentPower = SpecialAction.commanderHorn(user.getBoard().getCloseCombat(),card,currentPower);
                        }
                        if (user.getBoard().leaderBoost[1]){
                            secondPower *=2;
                        } else {
                            secondPower = SpecialAction.commanderHorn(user.getBoard().getRanged(),card,secondPower);
                        }
                        currentPower = SpecialAction.moralBoost(user.getBoard().getCloseCombat(),card,currentPower);
                        secondPower = SpecialAction.moralBoost(user.getBoard().getRanged(),card,secondPower);
                        if (secondPower > currentPower) {
                            user.getBoard().getCloseCombat().remove(card);
                            user.getBoard().getRanged().add(card);
                        }
                    }
                    cards = new ArrayList<>(user.getBoard().getRanged());
                    for (Card card : cards) {
                        if (!card.getType().equals("agileUnit") ||
                                (card.getAbility() != null && card.getAbility().contains("hero"))) continue;
                        int currentPower = card.getPower(), secondPower = card.getPower();
                        currentPower = SpecialAction.impenetrableFog(currentPower, user);
                        secondPower = SpecialAction.bitingFrost(secondPower, user);
                        if (user.getBoard().leaderBoost[1]){
                            currentPower *=2;
                        } else {
                            currentPower = SpecialAction.commanderHorn(user.getBoard().getRanged(),card,currentPower);
                        }
                        if (user.getBoard().leaderBoost[2]){
                            secondPower *=2;
                        } else {
                            secondPower = SpecialAction.commanderHorn(user.getBoard().getCloseCombat(),card,secondPower);
                        }
                        currentPower = SpecialAction.moralBoost(user.getBoard().getRanged(),card,currentPower);
                        secondPower = SpecialAction.moralBoost(user.getBoard().getCloseCombat(),card,secondPower);
                        if (secondPower > currentPower) {
                            user.getBoard().getRanged().remove(card);
                            user.getBoard().getCloseCombat().add(card);
                        }
                    }
                 }
            };
            case "QueenOfDolBlathanna" -> new Leader(faction, "QueenOfDolBlathanna") {
                @Override
                public void action(User user) {
                    Card mostPowerfulCard = null;
                    int n = 0;
                    for (Card card : user.getOpponentUser().getBoard().getCloseCombat()){
                        n += card.getRealPower();
                        if (mostPowerfulCard == null) {
                            mostPowerfulCard = card;
                        }
                        else if (mostPowerfulCard.getRealPower() < card.getRealPower()) {
                            mostPowerfulCard = card;
                        }
                    }
                    if (n > 10) {
                        for (Card card : user.getOpponentUser().getBoard().getCloseCombat()) {
                            if (card.getRealPower() == mostPowerfulCard.getRealPower()) {
                                user.getOpponentUser().getBoard().getCloseCombat().remove(card);
                                user.getOpponentUser().getBoard().getBurnedCard().add(card);
                            }
                        }
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
                public void action(User user) {
                    ArrayList<Card> allNonHeroes = new ArrayList<>();
                    ArrayList<Card> iterator = new ArrayList<>(user.getBoard().getBurnedCard());
                    for (Card card : iterator) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        user.getBoard().getBurnedCard().remove(card);
                        allNonHeroes.add(card);
                    }
                    iterator = new ArrayList<>(user.getOpponentUser().getBoard().getBurnedCard());
                    for (Card card : iterator) {
                        if (card.getAbility() != null){
                            if (card.getAbility().contains("hero")) {
                                continue;
                            }
                        }
                        user.getOpponentUser().getBoard().getBurnedCard().remove(card);
                        allNonHeroes.add(card);
                    }
                    Random random = new Random();
                    int size = allNonHeroes.size();
                    for (int i = 0; i < size / 2; i++) {
                        int num = random.nextInt(0, allNonHeroes.size());
                        user.getBoard().getHand().add(allNonHeroes.get(num));
                        allNonHeroes.remove(num);
                    }
                    user.getOpponentUser().getBoard().getHand().addAll(allNonHeroes);
                    GameMenu.getGameMenu().updateBorder();
                    GameMenu.getGameMenu().updateCardEvent();
                }
            };
            case "KingBran" -> new Leader(faction, "KingBran") {
                @Override
                public void action(User user) {
                    user.getBoard().leaderBoost[4] = true;
                }
            };

            default -> null;
        };
    }
    public static void showDeckForChoose(User user) {
        Pane root = ApplicationController.getRoot();
        for (Node node : root.getChildren()) {
            node.setDisable(true);
        }
        HBox hBox = new HBox(5);
        hBox.setMaxWidth(root.getWidth());
        hBox.setPrefHeight(root.getHeight());
        for (Card card : user.getDeck()) {
            if (card.getAbility() != null){
                if (card.getAbility().contains("hero")) {
                    continue;
                }
            }
            ImageView imageView = new ImageView();
            imageView.setImage(card.getImage());
            imageView.setFitWidth(hBox.getMaxWidth() / (user.getDeck().size() + 2));
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(mouseEvent -> {
                user.getDeck().remove(card);
                user.getBoard().getHand().add(card);
                GameMenu.getGameMenu().updateBorder();
                root.getChildren().remove(hBox);
                for (Node node : root.getChildren()) {
                    node.setDisable(false);
                }
                GameMenu.getGameMenu().updateCardEvent();
            });
            hBox.getChildren().add(imageView);
        }
        root.getChildren().add(hBox);
    }
}
