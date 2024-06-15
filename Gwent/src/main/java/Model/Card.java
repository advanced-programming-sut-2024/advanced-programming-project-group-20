package Model;

import Controller.PreGameController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Card extends Rectangle {
    private String name;
    private String type;
    private String ability;
    private boolean description;
    private int power;
    private Faction faction;
    private int numberOfCartInGame;
    private Image image;
    private boolean isSelect =false;
    private boolean isInDeck = true;


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
        this.setFill(new ImagePattern(image));
        this.setHeight(100);
        this.setWidth(70);
    }



    public void abilityAction(){

    }
    public void DescriptionAction(){

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

    public Image getImage() {
        return image;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isInDeck() {
        return isInDeck;
    }

    public void setInDeck(boolean inDeck) {
        isInDeck = inDeck;
    }

    public boolean hasAbility(Card card){
        return false;
    }
}
