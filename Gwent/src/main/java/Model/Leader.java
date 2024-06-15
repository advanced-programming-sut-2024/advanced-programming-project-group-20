package Model;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Leader extends Rectangle {
    private Faction faction;
    private String name;

    private boolean isUsed;
    private Image image;
    public void action() {

    }

    public Leader(Faction faction, String name) {
        this.faction = faction;
        this.name = name;
        this.isUsed = false;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getName() {
        return name;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
