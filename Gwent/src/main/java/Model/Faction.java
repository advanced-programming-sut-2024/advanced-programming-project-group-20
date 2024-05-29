package Model;

public abstract class Faction {
    private String name;

    public static void getFactionByName(String name){}
    public void ability(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
