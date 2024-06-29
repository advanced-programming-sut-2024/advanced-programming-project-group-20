package Model;


import Model.Factions.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Card extends Pane {
    private String name;
    private String type;
    private String ability;
    private boolean description;
    private int power;
    private Faction faction;
    private int numberOfCartInGame;
    transient private Image image;
    transient private Image gameImage;
    private boolean isSelect = false;


    public Card(String name, String type, String ability, boolean description
            , int power, Faction faction, int numberOfCartInGame) {
        this.name = name;
        this.type = type;
        this.ability = ability;
        this.description = description;
        this.power = power;
        this.faction = faction;
        this.numberOfCartInGame = numberOfCartInGame;
        if (faction != null) {
            this.image = new Image(String.valueOf(Card.class.getResource("/Cards/" + faction.getName() + "/" + name + ".jpg")));
        } else {
            this.image = new Image(String.valueOf(Card.class.getResource("/Cards/" + "Neutral" + "/" + name + ".jpg")));
        }
        if (faction != null) {
            this.gameImage = new Image(String.valueOf(Card.class.getResource("/gameCards/" + faction.getName() + "/" + name + ".jpg")));
        } else {
            this.gameImage = new Image(String.valueOf(Card.class.getResource("/gameCards/" + "Neutral" + "/" + name + ".jpg")));
        }
        Rectangle rectangle = new Rectangle(70, 100, new ImagePattern(gameImage));
        this.getChildren().add(rectangle);
        this.getChildren().add(new Label(String.valueOf(this.getPower())));
        ((Label) this.getChildren().get(1)).setFont(new Font("Agency FB Bold", 20));
        ((Label) this.getChildren().get(1)).setTextFill(Color.RED);
        this.setPrefHeight(100);
        this.setPrefWidth(70);
    }


    public void abilityAction() {

    }

    public void DescriptionAction() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public boolean isDescription() {
        return description;
    }

    public void setDescription(boolean description) {
        this.description = description;
    }

    public int getNumberOfCartInGame() {
        return numberOfCartInGame;
    }

    public void setNumberOfCartInGame(int numberOfCartInGame) {
        this.numberOfCartInGame = numberOfCartInGame;
    }

    public Image getGameImage() {
        return gameImage;
    }

    public void setGameImage(Image gameImage) {
        this.gameImage = gameImage;
    }

    public Image getImage() {
        return image;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean hasAbility(Card card) {
        return false;
    }

    public BackgroundImage createBackGroundImage(Image image) {
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    public static Card giveCardByName(String cardName) {
        Neutral neutral = new Neutral();
        for (Card card : neutral.getCollection()) {
            if (card.getName().equals(cardName)) {
                return CardBuilder.neutral(cardName);
            }
        }
        if (User.getTurnUser().getFaction().getName().equals("Skellige")) {
            return CardBuilder.skellige(cardName, new Skellige());
        } else if (User.getTurnUser().getFaction().getName().equals("NorthernRealms")) {
            return CardBuilder.northernRealms(cardName, new NorthernRealms());
        } else if (User.getTurnUser().getFaction().getName().equals("Monsters")) {
            return CardBuilder.monsters(cardName, new Monsters());
        } else if (User.getTurnUser().getFaction().getName().equals("Nilfgaard")) {
            return CardBuilder.nilfgaard(cardName, new Nilfgaard());
        } else {
            return CardBuilder.scoiaTael(cardName, new ScoiaTael());
        }
    }
}
