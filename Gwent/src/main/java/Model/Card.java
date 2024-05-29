package Model;

public class Card {
    private String name;
    private String type;
    private String position;
    private String ability;
    private boolean description;
    private int power;
    private Faction faction;
    private int numberOfCartInGame=0;

    public Card(String name, String type, String position, String ability, boolean description, int power, Faction faction, int numberOfCartInGame) {
        this.name = name;
        this.type = type;
        this.position = position;
        this.ability = ability;
        this.description = description;
        this.power = power;
        this.faction = faction;
        this.numberOfCartInGame = numberOfCartInGame;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
    public boolean hasAbility(Card card){
        return false;
    }
}
