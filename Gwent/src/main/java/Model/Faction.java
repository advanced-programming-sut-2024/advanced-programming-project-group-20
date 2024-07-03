package Model;

import Model.Factions.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Faction {

    protected String name;
    transient protected ArrayList<Card> Collection = new ArrayList<>();

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCollection() {
        return Collection;
    }

    public void createAllCards() {

    }

    public static Faction giveFactionByName(String nameOfFaction) {
        if (nameOfFaction.equals("Neutral")) {
            return null;
        } else if (nameOfFaction.equals("Nilfgaard")) {
            return new Nilfgaard();
        } else if (nameOfFaction.equals("Skellige")) {
            return new Skellige();
        } else if (nameOfFaction.equals("NorthernRealms")) {
            return new NorthernRealms();
        } else if (nameOfFaction.equals("Monsters")) {
            return new Monsters();
        }
        return new ScoiaTael();

    }
}
