package Model;

import Model.Factions.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class Leader extends Rectangle {
    private Faction faction;
    private String name;
    private boolean isUsed;

    public abstract void action(User user);

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
        if (User.getLoggedUser().getFaction().getName().equals("Nilfgaard")) {
            return LeaderBuilder.nilfgaard(leaderName, new Nilfgaard());

        } else if (User.getLoggedUser().getFaction().getName().equals("NorthernRealms")) {
            return LeaderBuilder.northernRealms(leaderName, new NorthernRealms());

        } else if (User.getLoggedUser().getFaction().getName().equals("Skellige")) {
            return LeaderBuilder.skellige(leaderName, new Skellige());

        } else if (User.getLoggedUser().getFaction().getName().equals("Monsters")) {
            return LeaderBuilder.monsters(leaderName, new Monsters());

        } else {
            return LeaderBuilder.scoiaTael(leaderName, new ScoiaTael());
        }
    }
    public static Leader giveLeaderByNameAndFaction(String leaderName, Faction faction) {
        if (faction.getName().equals("Nilfgaard")) {
            return LeaderBuilder.nilfgaard(leaderName, faction);

        } else if (faction.getName().equals("NorthernRealms")) {
            return LeaderBuilder.northernRealms(leaderName,faction);

        } else if (faction.getName().equals("Skellige")) {
            return LeaderBuilder.skellige(leaderName, faction);

        } else if (faction.getName().equals("Monsters")) {
            return LeaderBuilder.monsters(leaderName, faction);

        } else {
            return LeaderBuilder.scoiaTael(leaderName,faction);
        }
    }
}
