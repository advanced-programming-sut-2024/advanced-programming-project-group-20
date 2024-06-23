package Model;

import Model.Factions.Nilfgaard;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class Leader extends Rectangle {
    private Faction faction;
    private String name;
    private boolean isUsed;
    private Image image;
    public abstract void action();

    public Leader(Faction faction, String name) {
        this.faction = faction;
        this.name = name;
        this.isUsed = false;
        image = new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + name + ".jpg")));
        this.setFill(new ImagePattern(new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + name + ".jpg")))));

    }

    public Image getImage() {
        return image;
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
