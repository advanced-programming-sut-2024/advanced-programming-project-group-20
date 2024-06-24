package Model;

import Controller.ApplicationController;
import javafx.animation.Timeline;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

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
                    Button button = new Button("OK");
                    root.getChildren().add(button);
                    root.setOnMouseClicked(mouseEvent -> {
                        for (Node node : root.getChildren()) {
                            node.setDisable(false);
                        }
                        root.getChildren().removeAll(randImages);
                        root.getChildren().remove(button);
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
                    hBox.maxHeight(root.getHeight());
                    hBox.prefWidth(root.getWidth());
                    for (Card card : User.getTurnUser().getOpponentUser().getBoard().getBurnedCard()) {
                        if (card.getAbility().contains("hero")) {
                            continue;
                        }
                        ImageView imageView = new ImageView();
                        imageView.setImage(card.getImage());
                        imageView.setOnMouseClicked(mouseEvent -> {
                            User.getTurnUser().getOpponentUser().getBoard().getBurnedCard().remove(card);
                            User.getTurnUser().getBoard().getHand().add(card);
                            ApplicationController.getGameMenu().putCardInDeck();
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
                        if (card1.getAbility().contains("hero")) {
                            continue;
                        }
                        nonHeroCards.add(card1);
                    }
                    int randomNumber = random.nextInt(0,nonHeroCards.size());
                    Card card = nonHeroCards.get(randomNumber);
                    User.getTurnUser().getBoard().getBurnedCard().remove(card);
                    User.getTurnUser().getBoard().getHand().add(card);
                    ApplicationController.getGameMenu().putCardInDeck();
                    nonHeroCards = new ArrayList<>();
                    for (Card card1 : User.getTurnUser().getBoard().getBurnedCard()){
                        if (card1.getAbility().contains("hero")) {
                            continue;
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

                }
            };
            case "TheSteal-Forged" -> new Leader(faction, "TheSteal-Forged") {
                @Override
                public void action() {

                }
            };
            case "TheSiegemaster" -> new Leader(faction, "TheSiegemaster") {
                @Override
                public void action() {

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
                    hBox.maxHeight(root.getHeight());
                    hBox.prefWidth(root.getWidth());
                    for (Card card : User.getTurnUser().getBoard().getBurnedCard()) {
                        if (card.getAbility().contains("hero")) {
                            continue;
                        }
                        ImageView imageView = new ImageView();
                        imageView.setImage(card.getImage());
                        imageView.setOnMouseClicked(mouseEvent -> {
                            User.getTurnUser().getBoard().getBurnedCard().remove(card);
                            User.getTurnUser().getBoard().getHand().add(card);
                            ApplicationController.getGameMenu().putCardInDeck();
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

                }
            };
            case "DestroyerOfWorlds" -> new Leader(faction, "DestroyerOfWorlds") {
                @Override
                public void action() {

                }
            };
            case "BringerOfDeath" -> new Leader(faction, "BringerOfDeath") {
                @Override
                public void action() {

                }
            };
            case "TheTreacherous" -> new Leader(faction, "TheTreacherous") {
                @Override
                public void action() {

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

                }
            };
            case "DaisyOfTheValley" -> new Leader(faction, "DaisyOfTheValley") {
                @Override
                public void action() {
                    ArrayList<Card> nonHero = new ArrayList<>();
                    for (Card card : User.getTurnUser().getDeck()) {
                        if (card.getAbility().contains("hero")) {
                            continue;
                        }
                        nonHero.add(card);
                    }
                    Random random = new Random();
                    User.getTurnUser().getBoard().getHand().add(nonHero.get(random.nextInt(0, nonHero.size())));
                    ApplicationController.getGameMenu().putCardInDeck();
                }
            };
            case "TheBeautiful" -> new Leader(faction, "TheBeautiful") {
                @Override
                public void action() {

                }
            };
            case "HopeOfTheAenSeidhe" -> new Leader(faction, "HopeOfTheAenSeidhe") {
                @Override
                public void action() {

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
                        if (card.getAbility().contains("hero")) {
                            continue;
                        }
                        allNonHeroes.add(card);
                    }
                    for (Card card : User.getTurnUser().getOpponentUser().getBoard().getBurnedCard()) {
                        if (card.getAbility().contains("hero")) {
                            continue;
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
                    ApplicationController.getGameMenu().putCardInDeck();
                }
            };
            case "KingBran" -> new Leader(faction, "KingBran") {
                @Override
                public void action() {

                }
            };

            default -> null;
        };
    }
}
