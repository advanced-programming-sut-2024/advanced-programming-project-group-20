package Model.Factions;

import Model.CardBuilder;
import Model.Faction;

import java.util.ArrayList;

public class Skellige extends Faction {
    public Skellige() {
        Collection = new ArrayList<>();
        name = "Skellige";
        CreateAllCards();
    }

    private void CreateAllCards() {
        Collection.add(CardBuilder.skellige("Mardroeme", this));
        Collection.add(CardBuilder.skellige("Mardroeme", this));
        Collection.add(CardBuilder.skellige("Mardroeme", this));
        Collection.add(CardBuilder.skellige("Berserker", this));
        Collection.add(CardBuilder.skellige("Svanrige", this));
        Collection.add(CardBuilder.skellige("Udalryk", this));
        Collection.add(CardBuilder.skellige("DonarAnHindar", this));
        Collection.add(CardBuilder.skellige("ClanAnCraite", this));
        Collection.add(CardBuilder.skellige("ClanAnCraite", this));
        Collection.add(CardBuilder.skellige("ClanAnCraite", this));
        Collection.add(CardBuilder.skellige("MadmanLugos", this));
        Collection.add(CardBuilder.skellige("Cerys", this));
        Collection.add(CardBuilder.skellige("Kambi", this));
        Collection.add(CardBuilder.skellige("BirnaBran", this));
        Collection.add(CardBuilder.skellige("ClanDrummondShieldmaiden", this));
        Collection.add(CardBuilder.skellige("ClanDimunPirate", this));
        Collection.add(CardBuilder.skellige("ClanBrokvarArcher", this));
        Collection.add(CardBuilder.skellige("ClanBrokvarArcher", this));
        Collection.add(CardBuilder.skellige("ClanBrokvarArcher", this));
        Collection.add(CardBuilder.skellige("Ermion", this));
        Collection.add(CardBuilder.skellige("Hjalmar", this));
        Collection.add(CardBuilder.skellige("YoungBerserker", this));
        Collection.add(CardBuilder.skellige("YoungBerserker", this));
        Collection.add(CardBuilder.skellige("YoungBerserker", this));
        Collection.add(CardBuilder.skellige("YoungVidkaarl", this));
        Collection.add(CardBuilder.skellige("YoungVidkaarl", this));
        Collection.add(CardBuilder.skellige("YoungVidkaarl", this));
        Collection.add(CardBuilder.skellige("LightLongship", this));
        Collection.add(CardBuilder.skellige("LightLongship", this));
        Collection.add(CardBuilder.skellige("LightLongship", this));
        Collection.add(CardBuilder.skellige("WarLongship", this));
        Collection.add(CardBuilder.skellige("WarLongship", this));
        Collection.add(CardBuilder.skellige("WarLongship", this));
        Collection.add(CardBuilder.skellige("DraigBon-Dhu", this));
        Collection.add(CardBuilder.skellige("Olaf", this));

    }
}
