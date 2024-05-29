package Model;

public abstract class Leader{
    Faction faction;
    String name;
    boolean isUsed;
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
