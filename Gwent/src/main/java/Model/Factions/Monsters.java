package Model.Factions;

import Model.CardBuilder;
import Model.Faction;

import java.util.ArrayList;

public class Monsters extends Faction {

    public Monsters() {
        Collection = new ArrayList<>();
        name = "Monsters";
        CreateAllCards();
    }

    private void CreateAllCards() {
        Collection.add(CardBuilder.monsters("Draug", this));
        Collection.add(CardBuilder.monsters("Leshen", this));
        Collection.add(CardBuilder.monsters("Kayran", this));
        Collection.add(CardBuilder.monsters("Toad", this));
        Collection.add(CardBuilder.monsters("ArachasBehemoth", this));
        Collection.add(CardBuilder.monsters("CroneWeavess", this));
        Collection.add(CardBuilder.monsters("CroneWhispess", this));
        Collection.add(CardBuilder.monsters("EarthElemental", this));
        Collection.add(CardBuilder.monsters("Fiend", this));
        Collection.add(CardBuilder.monsters("FireElemental", this));
        Collection.add(CardBuilder.monsters("Forktail", this));
        Collection.add(CardBuilder.monsters("GraveHag", this));
        Collection.add(CardBuilder.monsters("Griffin", this));
        Collection.add(CardBuilder.monsters("IceGiant", this));
        Collection.add(CardBuilder.monsters("PlagueMaiden", this));
        Collection.add(CardBuilder.monsters("VampireKatakan", this));
        Collection.add(CardBuilder.monsters("Werewolf", this));
        Collection.add(CardBuilder.monsters("Arachas", this));
        Collection.add(CardBuilder.monsters("Arachas", this));
        Collection.add(CardBuilder.monsters("Arachas", this));
        Collection.add(CardBuilder.monsters("VampireBruxa", this));
        Collection.add(CardBuilder.monsters("VampireEkimmara", this));
        Collection.add(CardBuilder.monsters("VampireFleder", this));
        Collection.add(CardBuilder.monsters("VampireGarkain", this));
        Collection.add(CardBuilder.monsters("Cockatrice", this));
        Collection.add(CardBuilder.monsters("Endrega", this));
        Collection.add(CardBuilder.monsters("Foglet", this));
        Collection.add(CardBuilder.monsters("Harpy", this));
        Collection.add(CardBuilder.monsters("Nekker", this));
        Collection.add(CardBuilder.monsters("Nekker", this));
        Collection.add(CardBuilder.monsters("Nekker", this));
        Collection.add(CardBuilder.monsters("Wyvern", this));
        Collection.add(CardBuilder.monsters("Ghoul", this));
        Collection.add(CardBuilder.monsters("Ghoul", this));
        Collection.add(CardBuilder.monsters("Ghoul", this));

    }
}
