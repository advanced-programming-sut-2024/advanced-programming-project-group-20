package Model;

import Model.Factions.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class Leader extends Rectangle {
    private Faction faction;
    private String name;
    private boolean isUsed;

    public abstract void action();

    public Leader(Faction faction, String name) {
        this.faction = faction;
        this.name = name;
        this.isUsed = false;
        this.setFill(new ImagePattern(new Image(String.valueOf(Nilfgaard.class.getResource("/Leaders/" + name + ".jpg")))));

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

    public static Leader giveLeaderByName(String leaderName) {
        if (User.getTurnUser().getFaction().getName().equals("Nilfgaard")) {
            return LeaderBuilder.nilfgaard(leaderName, new Nilfgaard());

        } else if (User.getTurnUser().getFaction().getName().equals("NorthernRealms")) {
            return LeaderBuilder.northernRealms(leaderName, new NorthernRealms());

        } else if (User.getTurnUser().getFaction().getName().equals("Skellige")) {
            return LeaderBuilder.skellige(leaderName, new Skellige());

        } else if (User.getTurnUser().getFaction().getName().equals("Monsters")) {
            return LeaderBuilder.monsters(leaderName, new Monsters());

        } else {
            return LeaderBuilder.scoiaTael(leaderName, new ScoiaTael());
        }
    }
}
