package Controller;

import Model.*;
import Model.Factions.*;
import View.GameMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.Random;

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

    @FXML
    public void initialize() {
        updateData();
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
            if (card.getFaction() == null) {
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
        User.setTurnUser(User.getLoggedUser());
        GameMenu gameMenu = new GameMenu();
        gameMenu.start(ApplicationController.getStage());
    }

    public void showCards() {
        Neutral neutral = new Neutral();
        Faction faction = User.getTurnUser().getFaction();
        TilePane collectionContent = new TilePane(5, 5);
        collectionContent.setPrefWidth(418);
        collectionContent.setMinHeight(600);
        collectionContent.setStyle("-fx-background-color: #c0f305");
        TilePane deckContent = new TilePane(5, 5);
        deckContent.setMinHeight(600);
        deckContent.setPrefWidth(418);
        deckContent.setStyle("-fx-background-color: #05f3b4");
        ArrayList<Card> allCards = neutral.getCollection();
        allCards.addAll(faction.getCollection());
        for (Card card : allCards) {
            ImageView imageView = new ImageView(card.getImage());
            imageView.setFitHeight(300);
            imageView.setFitWidth(135);
            imageView.setOnMouseClicked(mouseEvent -> {
                if (collectionContent.getChildren().contains(imageView)) {
                    collectionContent.getChildren().remove(imageView);
                    deckContent.getChildren().add(imageView);
                    User.getTurnUser().getDeck().add(card);
                    updateData();
                } else if (deckContent.getChildren().contains(imageView)) {
                    collectionContent.getChildren().add(imageView);
                    deckContent.getChildren().remove(imageView);
                    User.getTurnUser().getDeck().remove(card);
                    updateData();
                }
            });
            collectionContent.getChildren().add(imageView);
        }
        collectionPane.setContent(collectionContent);
        deckPane.setContent(deckContent);
    }


    public static void showInformation() {
    }

    public static void saveDeckByAddress() {
    }


    public static String loadGameByName() {
        return null;
    }

    public static String loadGameByFile() {
        return null;
    }


    public void selectLeaders(ArrayList<ImageView> leaders) {
        for (ImageView imageView : leaders) {
            root.getChildren().remove(imageView);
        }
        userImageView.setImage(new Image(PreGameController.class.getResource
                ("/Leaders/" + User.getTurnUser().getLeader().getName() + ".jpg").toExternalForm()));
        ApplicationController.setEnable(root);
    }

    public static void addCardToDeck(String cardName, int count) {
    }

    public static void delete(String cardName, int count) {
    }

    public void changeTurn() throws Exception {
        if (unitCard < 22) {
            ApplicationController.alert("Low Unit Card","You should choose at least 22 unit card");
            return;
        } else if (specialCard > 10) {
            ApplicationController.alert("Extra Special Card", "You can't have more than 10 special card");
            return;
        }
        if (User.getTurnUser().equals(User.getLoggedUser())) {
            User.setTurnUser(User.getTurnUser().getOpponentUser());
            setContents();
            updateData();
        } else {
            startGame();
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
            User.getTurnUser().setLeader(new Leader(new Monsters(), "monsters_eredin_copper"));
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
            User.getTurnUser().setLeader(new Leader(new Nilfgaard(), "nilfgaard_emhyr_copper"));
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
            User.getTurnUser().setLeader(new Leader(new NorthernRealms(), "realms_foltest_copper"));
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
            User.getTurnUser().setLeader(new Leader(new Skellige(), "skellige_crach_an_craite"));
            setContents();
            root.getChildren().remove(monsters);
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
            User.getTurnUser().setLeader(new Leader(new ScoiaTael(), "scoiatael_francesca_gold"));
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
            case "Monsters" -> monsterLeader("monsters_eredin_copper", "monsters_eredin_bronze", "monsters_eredin_gold"
                    , "monsters_eredin_silver", "monsters_eredin_the_treacherous");
            case "Skellige" -> skelligeLeader("skellige_crach_an_craite", "skellige_king_bran");
            case "Nilfgaard" ->
                    nilfgaardLeader("nilfgaard_emhyr_bronze", "nilfgaard_emhyr_copper", "nilfgaard_emhyr_gold"
                            , "nilfgaard_emhyr_invader_of_the_north", "nilfgaard_emhyr_silver");
            case "ScoiaTael" ->
                    scoiaTaelLeader("scoiatael_francesca_bronze", "scoiatael_francesca_copper", "scoiatael_francesca_gold"
                            , "scoiatael_francesca_hope_of_the_aen_seidhe", "scoiatael_francesca_silver");
            default -> northernRealmsLeader("realms_foltest_copper", "realms_foltest_bronze", "realms_foltest_gold"
                    , "realms_foltest_silver", "realms_foltest_son_of_medell");
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
            User.getTurnUser().setLeader(new Leader(new Monsters(), leaderName));
        } else if (faction instanceof Skellige) {
            User.getTurnUser().setLeader(new Leader(new Skellige(), leaderName));
        } else if (faction instanceof ScoiaTael) {
            User.getTurnUser().setLeader(new Leader(new ScoiaTael(), leaderName));
        } else if (faction instanceof Nilfgaard) {
            User.getTurnUser().setLeader(new Leader(new Nilfgaard(), leaderName));
        } else {
            User.getTurnUser().setLeader(new Leader(new NorthernRealms(), leaderName));
        }
    }

}
