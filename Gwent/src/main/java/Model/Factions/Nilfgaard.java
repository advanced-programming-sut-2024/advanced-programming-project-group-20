package Model.Factions;

import Model.CardBuilder;
import Model.Faction;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collection;

public class Nilfgaard extends Faction {
    public Nilfgaard() {
        Collection = new ArrayList<>();
        name = "Nilfgaard";
        CreateAllCards();
    }

    private void CreateAllCards() {
        Collection.add(CardBuilder.nilfgaard("ImperaBrigadeGuard", this));
        Collection.add(CardBuilder.nilfgaard("ImperaBrigadeGuard", this));
        Collection.add(CardBuilder.nilfgaard("ImperaBrigadeGuard", this));
        Collection.add(CardBuilder.nilfgaard("ImperaBrigadeGuard", this));
        Collection.add(CardBuilder.nilfgaard("StefanSkellen", this));
        Collection.add(CardBuilder.nilfgaard("YoungEmissary", this));
        Collection.add(CardBuilder.nilfgaard("YoungEmissary", this));
        Collection.add(CardBuilder.nilfgaard("CahirMawrDyffrynAepCeallach", this));
        Collection.add(CardBuilder.nilfgaard("VattierDeRideaux", this));
        Collection.add(CardBuilder.nilfgaard("Puttkammer", this));
        Collection.add(CardBuilder.nilfgaard("BlackInfantryArcher", this));
        Collection.add(CardBuilder.nilfgaard("BlackInfantryArcher", this));
        Collection.add(CardBuilder.nilfgaard("TiborEggebracht", this));
        Collection.add(CardBuilder.nilfgaard("RenualdAepMatsen", this));
        Collection.add(CardBuilder.nilfgaard("FringillaVigo", this));
        Collection.add(CardBuilder.nilfgaard("RottenMangonel", this));
        Collection.add(CardBuilder.nilfgaard("ZerrikanianFireScorpion", this));
        Collection.add(CardBuilder.nilfgaard("SiegeEngineer", this));
        Collection.add(CardBuilder.nilfgaard("MorvranVoorhis", this));
        Collection.add(CardBuilder.nilfgaard("Cynthia", this));
        Collection.add(CardBuilder.nilfgaard("EtolianAuxiliaryArchers", this));
        Collection.add(CardBuilder.nilfgaard("EtolianAuxiliaryArchers", this));
        Collection.add(CardBuilder.nilfgaard("MennoCoehoorn", this));
        Collection.add(CardBuilder.nilfgaard("Morteisen", this));
        Collection.add(CardBuilder.nilfgaard("NausicaaCavalryRider", this));
        Collection.add(CardBuilder.nilfgaard("NausicaaCavalryRider", this));
        Collection.add(CardBuilder.nilfgaard("NausicaaCavalryRider", this));
        Collection.add(CardBuilder.nilfgaard("SiegeTechnician", this));
        Collection.add(CardBuilder.nilfgaard("Sweers", this));
        Collection.add(CardBuilder.nilfgaard("Vanhemar", this));

    }
}
