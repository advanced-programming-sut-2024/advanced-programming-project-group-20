package Model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Faction {
    protected String name;
    protected ArrayList<Card> Collection = new ArrayList<>();

    public void ability(){}

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCollection() {
        return Collection;
    }
}
