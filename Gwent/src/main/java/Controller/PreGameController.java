package Controller;

import Model.*;
import Model.Factions.*;
import View.GameMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class PreGameController {
    public AnchorPane root;
    public ScrollPane collectionPane;
    public ScrollPane deckPane;
    public Label totalLabel;
    public Label unitLabel;
    public Label specialLabel;
    public Label strengthLabel;
    public Label heroLabel;
    public ImageView userImageView;
    public Label factionName;
    public Label username;
    private ArrayList<ImageView> cards = new ArrayList<>();
    private int totalCard = 0;
    private int unitCard = 0;
    private int specialCard = 0;
    private int heroCard = 0;
    private int strength = 0;
    private TilePane deckContent;
    private ArrayList<Card> collection = new ArrayList<>();

    @FXML
    public void initialize() {
        ArrayList<Card> allCards = new Neutral().getCollection();
        allCards.addAll(User.getTurnUser().getFaction().getCollection());
        collection = allCards;
        //todo uncoment this line
//        loadLastDeckContent();
        setContents();
    }



    private void setContents() {
        //TODO put write image
        username.setText(User.getTurnUser().getUsername());
        factionName.setText(User.getTurnUser().getFaction().getName());
        userImageView.setImage(new Image(PreGameController.class.getResource
                ("/Leaders/" + User.getTurnUser().getLeader().getName() + ".jpg").toExternalForm()));
        userImageView.setFitHeight(180);
        userImageView.setFitWidth(120);
        //set cards of factions
        showCards();
    }

    private void updateData() {
        totalCard = User.getTurnUser().getDeck().size();
        unitCard = 0;
        specialCard = 0;
        heroCard = 0;
        strength = 0;
        for (Card card : User.getTurnUser().getDeck()) {
            if (card.getType().equals("weather") || card.getType().equals("spell")) {
                specialCard++;
            } else {
                unitCard++;
            }
            strength += card.getPower();
            if (card.getAbility() != null) {
                String[] abilities = card.getAbility().split("&");
                for (int i = 0; i < abilities.length; i++) {
                    if (abilities[i].equals("hero")) heroCard++;
                }
            }
        }
        totalLabel.setText("Total Cards in Deck: " + totalCard);
        unitLabel.setText("Number of Unit Cards: " + unitCard);
        specialLabel.setText("Special Cards: " + specialCard);
        strengthLabel.setText("Total Unit Card Strength: " + strength);
        heroLabel.setText("Hero Cards: " + heroCard);
    }

    public void startGame() throws Exception {
        GameController.turnStarter();
        User.getLoggedUser().setActiveGame(new GameHistory(User.getLoggedUser().getOpponentUser(), new Date()));
        User.getLoggedUser().getOpponentUser().setActiveGame(new GameHistory(User.getLoggedUser(), new Date()));
        GameMenu gameMenu = new GameMenu();
        gameMenu.start(ApplicationController.getStage());
    }

    public void showCards() {
        TilePane collectionContent = new TilePane(5, 5);
        collectionContent.setPrefWidth(418);
        collectionContent.setMinHeight(600);
        collectionContent.setStyle("-fx-background-color: #161716");
        deckContent = new TilePane(5, 5);
        deckContent.setMinHeight(600);
        deckContent.setPrefWidth(418);
        deckContent.setStyle("-fx-background-color: #161716");
        showDeck(deckContent, collectionContent);
        showCollection(collectionContent, deckContent);
        collectionPane.setContent(collectionContent);
        deckPane.setContent(deckContent);
    }

    private ArrayList<Card> sortCards(ArrayList<Card> allCards) {
        ArrayList<Card> full = new ArrayList<>();
        ArrayList<Card> spell = new ArrayList<>();
        ArrayList<Card> units = new ArrayList<>(allCards);
        for (Card card : allCards) {
            if (card.getType().equals("weather")) {
                full.add(card);
                units.remove(card);
            } else if (card.getType().equals("spell")) {
                spell.add(card);
                units.remove(card);
            }
        }
        full.sort(Comparator.comparing(Card::getName));
        spell.sort(Comparator.comparing(Card::getName));
        units.sort(Comparator.comparingInt(Card::getPower).reversed().thenComparing(Card::getName));
        full.addAll(spell);
        full.addAll(units);
        return full;
    }

    private void showCollection(TilePane collectionContent, TilePane deckContent) {
        collectionContent.getChildren().clear();
        collection = sortCards(collection);
        for (Card card : collection) {
            ImageView imageView = new ImageView(card.getImage());
            imageView.setFitHeight(300);
            imageView.setFitWidth(135);
            imageView.setOnMouseClicked(mouseEvent -> {
                collection.remove(card);
                User.getTurnUser().getDeck().add(card);
                showCollection(collectionContent,deckContent);
                showDeck(deckContent,collectionContent);
                updateData();
            });
            collectionContent.getChildren().add(imageView);
        }
    }

    private void showDeck(TilePane deckContent, TilePane collectionContent) {
        deckContent.getChildren().clear();
        User.getTurnUser().setDeck(sortCards(User.getTurnUser().getDeck()));
        for (Card card : User.getTurnUser().getDeck()) {
            ImageView imageView = new ImageView(card.getImage());
            imageView.setFitHeight(300);
            imageView.setFitWidth(135);
            imageView.setOnMouseClicked(mouseEvent -> {
                collection.add(card);
                User.getTurnUser().getDeck().remove(card);
                showCollection(collectionContent,deckContent);
                showDeck(deckContent,collectionContent);
                updateData();
            });
            deckContent.getChildren().add(imageView);
        }
    }

    public static void saveDeckByAddress(ArrayList<String> nameOfCards) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Gson File");

        // Set extension filters
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Gson Files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(ApplicationController.getStage());

        if (file != null) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(nameOfCards);
            try (PrintWriter pw = new PrintWriter(file)) {
                pw.write(json);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadGameByFile(ArrayList<String> gsonLines) {
        reloadDeck();

        ArrayList<Card> deckCards = new ArrayList<>();

        boolean isSetFactionAndLeader = false;
        for (String gsonLine : gsonLines) {
            if (!isSetFactionAndLeader) {
                //set faction
                isSetFactionAndLeader = canSetFactionAndLeader(gsonLine);
            }

            Card card;
            if (!gsonLine.substring(0, gsonLine.indexOf(':')).equals("Neutral")) {
                card = Card.giveCardByName(gsonLine.substring(gsonLine.lastIndexOf(':') + 1));
                //set Neutral cards
            } else {
                card = Card.giveCardByName(gsonLine.substring(gsonLine.lastIndexOf(':') + 1));
            }

            for (Card card1 : collection) {
                if (card1.getName().equals(card.getName())) {
                    collection.remove(card1);
                    break;
                }
            }
            User.getTurnUser().getDeck().add(Card.giveCardByName(gsonLine.substring(gsonLine.lastIndexOf(':') + 1)));
        }

        updateData();
        showCards();
    }

    private boolean canSetFactionAndLeader(String gsonLines) {
        if (!gsonLines.substring(0, gsonLines.indexOf(':')).equals("Neutral")) {
            User.getTurnUser().setFaction(Faction.giveFactionByName(gsonLines.substring(0, gsonLines.indexOf(':'))));
            //set leader
            User.getTurnUser().setLeader(Leader
                    .giveLeaderByName(gsonLines.substring(gsonLines.indexOf(':') + 1, gsonLines.lastIndexOf(':'))));
            setContents();
            return true;
        }
        return false;
    }

    private void reloadDeck() {
        User.getTurnUser().getDeck().clear();
        totalCard = 0;
        unitCard = 0;
        specialCard = 0;
        heroCard = 0;
        strength = 0;
    }

    public void selectLeaders(ArrayList<ImageView> leaders) {
        for (ImageView imageView : leaders) {
            root.getChildren().remove(imageView);
        }
        userImageView.setImage(new Image(PreGameController.class.getResource
                ("/Leaders/" + User.getTurnUser().getLeader().getName() + ".jpg").toExternalForm()));
        ApplicationController.setEnable(root);
    }

    public void changeTurn() throws Exception {
        if (unitCard < 10) {
            ApplicationController.alert("Low Unit Card", "You should choose at least 22 unit card");
            return;
        } else if (specialCard > 10) {
            ApplicationController.alert("Extra Special Card", "You can't have more than 10 special card");
            return;
        }
        if (User.getTurnUser().equals(User.getLoggedUser())) {
            ArrayList<Card> allCards = new Neutral().getCollection();
            allCards.addAll(User.getTurnUser().getFaction().getCollection());
            collection = allCards;
//            saveLastDeckContent();
            User.setTurnUser(User.getTurnUser().getOpponentUser());
//            System.out.println(User.getTurnUser().getFaction().getName());
            setContents();
            updateData();
        } else {
            startGame();
        }
    }

    private void saveLastDeckContent() {
        Faction faction = User.getTurnUser().getFaction();
        Neutral neutral = new Neutral();
        ArrayList<Card> allCards = neutral.getCollection();
        allCards.addAll(faction.getCollection());
        ArrayList<String> nameOfCards = new ArrayList<>();
        for (Node node : deckContent.getChildren()) {
            if (node instanceof ImageView) {
                for (Card card : allCards) {
                    if ((((ImageView) node).getImage().getUrl().toString().contains("Neutral") && card.getFaction() != null)
                            || (!((ImageView) node).getImage().getUrl().toString().contains("Neutral") && card.getFaction() == null)) {


                    } else if (((ImageView) node).getImage().getUrl().toString().contains("Neutral")) {

                        if (((ImageView) node).getImage().getUrl().toString()
                                .equals(PreGameController.class.getResource("/Cards/Neutral/" + card.getName() + ".jpg").toString())) {

                            nameOfCards.add("Neutral:" + User.getTurnUser().getLeader().getName()
                                    + ":" + card.getName());
                            break;
                        }

                    } else if (((ImageView) node).getImage().getUrl().toString()
                            .equals(PreGameController.class.getResource("/Cards/" + faction.getName() + "/" + card.getName() + ".jpg").toString())) {
                        nameOfCards.add(User.getTurnUser().getFaction().getName() + ":" + User.getTurnUser().getLeader().getName()
                                + ":" + card.getName());
                        break;
                    }
                }
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(nameOfCards);
        try (PrintWriter pw = new PrintWriter("lastDeckContent.json")) {
            pw.write(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void chooseFaction(MouseEvent mouseEvent) {
        ImageView monsters = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Factions/faction_monsters.jpg"))));
        ImageView nilfgaard = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Factions/faction_nilfgaard.jpg"))));
        ImageView northernRealms = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Factions/faction_realms.jpg"))));
        ImageView skellige = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Factions/faction_skellige.jpg"))));
        ImageView scoiaTael = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Factions/faction_scoiatael.jpg"))));
        monsters.setFitHeight(720);
        monsters.setFitWidth(256);
        monsters.setOnMouseClicked(mouseEvent1 -> {
            User.getTurnUser().setFaction(new Monsters());
            User.getTurnUser().setLeader(LeaderBuilder.monsters("KingOfTheWildHunt", User.getTurnUser().getFaction()));
            User.getTurnUser().getDeck().clear();
            ArrayList<Card> allCards = new Neutral().getCollection();
            allCards.addAll(User.getTurnUser().getFaction().getCollection());
            collection = allCards;
            setContents();
            root.getChildren().remove(monsters);
            root.getChildren().remove(nilfgaard);
            root.getChildren().remove(northernRealms);
            root.getChildren().remove(skellige);
            root.getChildren().remove(scoiaTael);
        });
        nilfgaard.setLayoutX(256);
        nilfgaard.setFitHeight(720);
        nilfgaard.setFitWidth(256);
        nilfgaard.setOnMouseClicked(mouseEvent1 -> {
            User.getTurnUser().setFaction(new Nilfgaard());
            User.getTurnUser().setLeader(LeaderBuilder.nilfgaard("EmperorOfNilfgaard", User.getTurnUser().getFaction()));
            ArrayList<Card> allCards = new Neutral().getCollection();
            allCards.addAll(User.getTurnUser().getFaction().getCollection());
            User.getTurnUser().getDeck().clear();
            collection = allCards;
            setContents();
            root.getChildren().remove(monsters);
            root.getChildren().remove(nilfgaard);
            root.getChildren().remove(northernRealms);
            root.getChildren().remove(skellige);
            root.getChildren().remove(scoiaTael);
        });
        northernRealms.setLayoutX(512);
        northernRealms.setFitHeight(720);
        northernRealms.setFitWidth(256);
        northernRealms.setOnMouseClicked(mouseEvent1 -> {
            User.getTurnUser().setFaction(new NorthernRealms());
            User.getTurnUser().setLeader(LeaderBuilder.northernRealms("LordOfCommanderOfTheNorth", User.getTurnUser().getFaction()));
            ArrayList<Card> allCards = new Neutral().getCollection();
            allCards.addAll(User.getTurnUser().getFaction().getCollection());
            User.getTurnUser().getDeck().clear();
            collection = allCards;
            setContents();
            root.getChildren().remove(monsters);
            root.getChildren().remove(nilfgaard);
            root.getChildren().remove(northernRealms);
            root.getChildren().remove(skellige);
            root.getChildren().remove(scoiaTael);

        });
        skellige.setLayoutX(768);
        skellige.setFitHeight(720);
        skellige.setFitWidth(256);
        skellige.setOnMouseClicked(mouseEvent1 -> {
            User.getTurnUser().setFaction(new Skellige());
            User.getTurnUser().setLeader(LeaderBuilder.skellige("CrachAnCraite", User.getTurnUser().getFaction()));
            User.getTurnUser().getDeck().clear();
            ArrayList<Card> allCards = new Neutral().getCollection();
            allCards.addAll(User.getTurnUser().getFaction().getCollection());
            collection = allCards;root.getChildren().remove(monsters);
            setContents();
            root.getChildren().remove(nilfgaard);
            root.getChildren().remove(northernRealms);
            root.getChildren().remove(skellige);
            root.getChildren().remove(scoiaTael);
        });
        scoiaTael.setLayoutX(1024);
        scoiaTael.setFitHeight(720);
        scoiaTael.setFitWidth(256);
        scoiaTael.setOnMouseClicked(mouseEvent1 -> {
            User.getTurnUser().setFaction(new ScoiaTael());
            User.getTurnUser().setLeader(LeaderBuilder.scoiaTael("PurebloodElf", User.getTurnUser().getFaction()));
            ArrayList<Card> allCards = new Neutral().getCollection();
            allCards.addAll(User.getTurnUser().getFaction().getCollection());
            collection = allCards;
            User.getTurnUser().getDeck().clear();
            setContents();
            root.getChildren().remove(monsters);
            root.getChildren().remove(nilfgaard);
            root.getChildren().remove(northernRealms);
            root.getChildren().remove(skellige);
            root.getChildren().remove(scoiaTael);
        });
        root.getChildren().add(monsters);
        root.getChildren().add(nilfgaard);
        root.getChildren().add(northernRealms);
        root.getChildren().add(skellige);
        root.getChildren().add(scoiaTael);
        User.getTurnUser().setDeck(new ArrayList<>());
        updateData();
    }

    public void chooseLeaders(MouseEvent mouseEvent) {
        //TODO put names of leaders
        switch (User.getTurnUser().getFaction().getName()) {
            case "Monsters" -> monsterLeader("KingOfTheWildHunt", "CommanderOfRedRiders", "DestroyerOfWorlds"
                    , "BringerOfDeath", "TheTreacherous");
            case "Skellige" -> skelligeLeader("CrachAnCraite", "KingBran");
            case "Nilfgaard" -> nilfgaardLeader("EmperorOfNilfgaard", "HisImperialMajesty", "TheRelentless"
                    , "InvaderOfNorth", "TheWhiteFlame");
            case "ScoiaTael" -> scoiaTaelLeader("PurebloodElf", "DaisyOfTheValley", "TheBeautiful"
                    , "HopeOfTheAenSeidhe", "QueenOfDolBlathanna");
            default -> northernRealmsLeader("LordOfCommanderOfTheNorth", "KingOfTemperia", "TheSteal-Forged"
                    , "TheSiegemaster", "SunOfMedell");
        }


    }

    private void northernRealmsLeader(String leader1, String leader2, String leader3, String leader4, String leader5) {
        showLeadersOfFactionsWith5Leaders(new NorthernRealms(), leader1, leader2, leader3, leader4, leader5);
    }

    private void nilfgaardLeader(String leader1, String leader2, String leader3, String leader4, String leader5) {
        showLeadersOfFactionsWith5Leaders(new Nilfgaard(), leader1, leader2, leader3, leader4, leader5);
    }

    private void scoiaTaelLeader(String leader1, String leader2, String leader3, String leader4, String leader5) {
        showLeadersOfFactionsWith5Leaders(new ScoiaTael(), leader1, leader2, leader3, leader4, leader5);
    }

    private void skelligeLeader(String leader1, String leader2) {
        showLeadersOfFactionSkellige(new Skellige(), leader1, leader2);
    }

    private void monsterLeader(String leader1, String leader2, String leader3, String leader4, String leader5) {
        showLeadersOfFactionsWith5Leaders(new Monsters(), leader1, leader2, leader3, leader4, leader5);
    }

    private void showLeadersOfFactionSkellige(Faction faction, String leader1, String leader2) {
        ImageView copper = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + leader1 + ".jpg"))));
        ImageView bronze = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + leader2 + ".jpg"))));
        ArrayList<ImageView> all = new ArrayList<>();
        all.add(copper);
        all.add(bronze);
        copper.setFitHeight(415);
        copper.setFitWidth(220);
        copper.setY(150);
        copper.setX(350);
        copper.setOnMouseClicked(mouseEvent1 -> {
            addLeaderToUserLeader(faction, leader1);
            selectLeaders(all);
        });
        bronze.setLayoutX(710);
        bronze.setY(150);
        bronze.setFitHeight(415);
        bronze.setFitWidth(220);
        bronze.setOnMouseClicked(mouseEvent1 -> {
            addLeaderToUserLeader(faction, leader2);
            selectLeaders(all);
        });
        ApplicationController.setDisable(root);
        root.getChildren().add(copper);
        root.getChildren().add(bronze);
    }

    private void showLeadersOfFactionsWith5Leaders(Faction faction, String leader1, String leader2, String leader3, String leader4, String leader5) {
        ImageView copper = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + leader1 + ".jpg"))));
        ImageView bronze = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + leader2 + ".jpg"))));
        ImageView gold = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + leader3 + ".jpg"))));
        ImageView silver = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + leader4 + ".jpg"))));
        ImageView medell = new ImageView(new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + leader5 + ".jpg"))));
        ArrayList<ImageView> all = new ArrayList<>();
        all.add(copper);
        all.add(bronze);
        all.add(gold);
        all.add(silver);
        all.add(medell);
        copper.setFitHeight(415);
        copper.setFitWidth(220);
        copper.setY(150);
        copper.setOnMouseClicked(mouseEvent1 -> {
            addLeaderToUserLeader(faction, leader1);
            selectLeaders(all);
        });
        bronze.setLayoutX(256);
        bronze.setFitHeight(415);
        bronze.setFitWidth(220);
        bronze.setY(150);
        bronze.setOnMouseClicked(mouseEvent1 -> {
            addLeaderToUserLeader(faction, leader2);
            selectLeaders(all);
        });
        gold.setLayoutX(512);
        gold.setFitHeight(415);
        gold.setFitWidth(220);
        gold.setY(150);
        gold.setOnMouseClicked(mouseEvent1 -> {
            addLeaderToUserLeader(faction, leader3);
            selectLeaders(all);
        });
        silver.setLayoutX(768);
        silver.setFitHeight(415);
        silver.setFitWidth(220);
        silver.setY(150);
        silver.setOnMouseClicked(mouseEvent1 -> {
            addLeaderToUserLeader(faction, leader4);
            selectLeaders(all);
        });
        medell.setLayoutX(1024);
        medell.setFitHeight(415);
        medell.setFitWidth(220);
        medell.setY(150);
        medell.setOnMouseClicked(mouseEvent1 -> {
            addLeaderToUserLeader(faction, leader5);
            selectLeaders(all);
        });
        ApplicationController.setDisable(root);
        root.getChildren().add(copper);
        root.getChildren().add(bronze);
        root.getChildren().add(gold);
        root.getChildren().add(silver);
        root.getChildren().add(medell);
    }

    private void addLeaderToUserLeader(Faction faction, String leaderName) {
        if (faction instanceof Monsters) {
            User.getTurnUser().setLeader(LeaderBuilder.monsters(leaderName, faction));
        } else if (faction instanceof Skellige) {
            User.getTurnUser().setLeader(LeaderBuilder.skellige(leaderName, faction));
        } else if (faction instanceof ScoiaTael) {
            User.getTurnUser().setLeader(LeaderBuilder.scoiaTael(leaderName, faction));
        } else if (faction instanceof Nilfgaard) {
            User.getTurnUser().setLeader(LeaderBuilder.nilfgaard(leaderName, faction));
        } else {
            User.getTurnUser().setLeader(LeaderBuilder.northernRealms(leaderName, faction));
        }
    }

    public void uploadDeck(MouseEvent mouseEvent) {
        Faction faction = User.getTurnUser().getFaction();
        Neutral neutral = new Neutral();
        ArrayList<Card> allCards = neutral.getCollection();
        allCards.addAll(faction.getCollection());
        ArrayList<String> nameOfCards = new ArrayList<>();
        for (Node node : deckContent.getChildren()) {
            if (node instanceof ImageView) {
                for (Card card : allCards) {
                    if ((((ImageView) node).getImage().getUrl().toString().contains("Neutral") && card.getFaction() != null)
                            || (!((ImageView) node).getImage().getUrl().toString().contains("Neutral") && card.getFaction() == null)) {


                    } else if (((ImageView) node).getImage().getUrl().toString().contains("Neutral")) {

                        if (((ImageView) node).getImage().getUrl().toString()
                                .equals(PreGameController.class.getResource("/Cards/Neutral/" + card.getName() + ".jpg").toString())) {

                            nameOfCards.add("Neutral:" + User.getTurnUser().getLeader().getName()
                                    + ":" + card.getName());
                            break;
                        }

                    } else if (((ImageView) node).getImage().getUrl().toString()
                            .equals(PreGameController.class.getResource("/Cards/" + faction.getName() + "/" + card.getName() + ".jpg").toString())) {
                        nameOfCards.add(User.getTurnUser().getFaction().getName() + ":" + User.getTurnUser().getLeader().getName()
                                + ":" + card.getName());
                        break;
                    }
                }
            }
        }
        boolean haveCardExceptNeutral = false;
        for (String s : nameOfCards) {
            if (!s.substring(0, s.indexOf(':')).equals("Neutral")) {
                haveCardExceptNeutral = true;
                break;
            }
        }
        if (!haveCardExceptNeutral) {
            ApplicationController.alert("no Unit Cards!", "Choose at least one unit card");
            return;
        }
        saveDeckByAddress(nameOfCards);
    }

    public void downloadDeck(MouseEvent mouseEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JSON File");

        // Set extension filter for JSON files
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(ApplicationController.getStage());

        if (file != null) {
            ArrayList<String> gsonLines = new ArrayList<>();
            Gson gson = new Gson();
            try {
                JsonArray a = gson.fromJson(new FileReader(file), JsonArray.class);
                a.forEach(e -> {
                    try {
                        JsonReader reader = new JsonReader(new StringReader(e.toString()));
                        reader.setLenient(true);
                        Object object = gson.fromJson(reader, Object.class);
                        if (!object.getClass().equals(String.class)){
                            ApplicationController.alert("invalid format!", "choose a valid formatted gsonFile");
                            return;
                        }
                        gsonLines.add((String) object);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for (String line: gsonLines){
                if (!line.matches(".*:.*:.*")){
                    ApplicationController.alert("invalid format!", "choose a valid formatted gsonFile");
                    return;
                }
            }
            loadGameByFile(gsonLines);

        }
    }
//TODO last deck content
    private void loadLastDeckContent() {
        ArrayList<String> arr = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JsonArray a = gson.fromJson(new FileReader("lastDeckContent.json"), JsonArray.class);
            a.forEach(e -> {
                try {
                    JsonReader reader = new JsonReader(new StringReader(e.toString()));
                    reader.setLenient(true);
                    String obj = gson.fromJson(reader, String.class);
                    arr.add(obj);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        loadGameByFile(arr);
    }

}

