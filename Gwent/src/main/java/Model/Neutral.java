package Model;

import java.util.ArrayList;

public class Neutral {
    private ArrayList<Card> collection;

    public Neutral() {
        collection = new ArrayList<>();
        CreateAllCards();
    }
    public ArrayList<Card> getCollection() {
        return collection;
    }

    private void CreateAllCards() {
        collection.add(CardBuilder.neutral("BitingFrost"));
        collection.add(CardBuilder.neutral("BitingFrost"));
        collection.add(CardBuilder.neutral("BitingFrost"));
        collection.add(CardBuilder.neutral("ImpenetrableFog"));
        collection.add(CardBuilder.neutral("ImpenetrableFog"));
        collection.add(CardBuilder.neutral("ImpenetrableFog"));
        collection.add(CardBuilder.neutral("TorrentialRain"));
        collection.add(CardBuilder.neutral("TorrentialRain"));
        collection.add(CardBuilder.neutral("TorrentialRain"));
        collection.add(CardBuilder.neutral("SkelligeStorm"));
        collection.add(CardBuilder.neutral("SkelligeStorm"));
        collection.add(CardBuilder.neutral("SkelligeStorm"));
        collection.add(CardBuilder.neutral("ClearWeather"));
        collection.add(CardBuilder.neutral("ClearWeather"));
        collection.add(CardBuilder.neutral("ClearWeather"));
        collection.add(CardBuilder.neutral("Scorch"));
        collection.add(CardBuilder.neutral("Scorch"));
        collection.add(CardBuilder.neutral("Scorch"));
        collection.add(CardBuilder.neutral("Commander’shorn"));
        collection.add(CardBuilder.neutral("Commander’shorn"));
        collection.add(CardBuilder.neutral("Commander’shorn"));
        collection.add(CardBuilder.neutral("Decoy"));
        collection.add(CardBuilder.neutral("Decoy"));
        collection.add(CardBuilder.neutral("Decoy"));
        collection.add(CardBuilder.neutral("Dandelion"));
        collection.add(CardBuilder.neutral("EmielRegis"));
        collection.add(CardBuilder.neutral("GaunterO,Dimm"));
        collection.add(CardBuilder.neutral("GaunterO’DimmDarkness"));
        collection.add(CardBuilder.neutral("GaunterO’DimmDarkness"));
        collection.add(CardBuilder.neutral("GaunterO’DimmDarkness"));
        collection.add(CardBuilder.neutral("MysteriousElf"));
        collection.add(CardBuilder.neutral("OlgierdVonEverc"));
        collection.add(CardBuilder.neutral("TrissMerigold"));
        collection.add(CardBuilder.neutral("Villentretenmerth"));
        collection.add(CardBuilder.neutral("YenneferofVengerberg"));
        collection.add(CardBuilder.neutral("ZoltanChivay"));
        collection.add(CardBuilder.neutral("Cow"));
        collection.add(CardBuilder.neutral("GeraltofRivia"));
        collection.add(CardBuilder.neutral("Vesemir"));

    }
}
