package Model;

import Model.Factions.*;

public class CardBuilder {
    public static Card factionChooser(String factionName,String cardName){
        return switch (factionName) {
            case "Skellige" -> skellige(cardName);
            case "ScoiaTeal" -> scoiaTeal(cardName);
            case "NorthernRealms" -> northernRealms(cardName);
            case "Nilfgaard" -> nilfgaard(cardName);
            case "Monsters" -> monsters(cardName);
            case "Neutral" -> neutral(cardName);
            default -> null;
        };
    }
    public static Card skellige(String cardName){
        Skellige skellige = new Skellige();
        return switch (cardName) {
            case "Mardroeme" -> new Card(cardName, "spell", "mardoeme",
                    true, -1, skellige, 3);
            case "Berserker" -> new Card(cardName, "closeCombatUnit", "berserker",
                    true, 4, skellige, 1);
            case "Vidkaarl" -> new Card(cardName, "closeCombatUnit", "moralBoost",
                    true, 14, skellige, 1111);  //there is no number for this card in dock;
            case "Svanrige", "Udalryk", "DonarAnHindar" -> new Card(cardName, "closeCombatUnit", null,
                    false, 4, skellige, 1);
            case "ClanAnCraite" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    false, 6, skellige, 3);
            case "MadmanLugos" -> new Card(cardName, "closeCombatUnit", null,
                    false, 6, skellige, 1);
            case "Cerys" -> new Card(cardName, "closeCombatUnit", "hero&muster",
                    true, 10, skellige, 1);
            case "Kambi" -> new Card(cardName, "closeCombatUnit", "hero&transformers",
                    true, 11, skellige, 1);
            case "BirnaBran" -> new Card(cardName, "closeCombatUnit", "medic",
                    false, 2, skellige, 1);
            case "ClanDrummondShieldmaiden" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    true, 4, skellige, 1);
            case "ClanDimunPirate" -> new Card(cardName, "rangedUnit", "scorch",
                    true, 6, skellige, 1);
            case "ClanBrokvarArcher" -> new Card(cardName, "rangedUnit", null,
                    false, 6, skellige, 3);
            case "Ermion" -> new Card(cardName, "rangedUnit", "hero&mardroeme",
                    false, 8, skellige, 1);
            case "Hjalmar" -> new Card(cardName, "rangedUnit", "hero",
                    false, 10, skellige, 1);
            case "YoungBerserker" -> new Card(cardName, "rangedUnit", "berserker",
                    true, 2, skellige, 3);
            case "YoungVidkaarl" -> new Card(cardName, "rangedUnit", "tightBond",
                    true, 8, skellige, 3); //there is no number for this card in dock;
            case "LightLongship" -> new Card(cardName, "rangedUnit", "muster",
                    false, 4, skellige, 3);
            case "WarLongship" -> new Card(cardName, "siegeUnit", "tightBond",
                    false, 6, skellige, 3);
            case "DraigBon-Dhu" -> new Card(cardName, "siegeUnit", "commander'sHorn",
                    false, 2, skellige, 1);
            case "Olaf" -> new Card(cardName, "agileUnit", "moralBoost",
                    false, 12, skellige, 1);
            default -> null;
        };
    }
    public static Card scoiaTeal(String cardName){
        ScoiaTael scoiaTael = new ScoiaTael();
        return switch (cardName) {

            default -> null;
        };
    }
    public static Card northernRealms(String cardName){
        NorthernRealms northernRealms = new NorthernRealms();
        return switch (cardName) {

            default -> null;
        };
    }

    public static Card nilfgaard(String cardName){
        Nilfgaard nilfgaard = new Nilfgaard();
        return switch (cardName) {
            case "ImperaBrigade" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    false, 3, nilfgaard, 4);
            case "StefanSkellen" -> new Card(cardName, "closeCombatUnit", "spy",
                    false, 9, nilfgaard, 1);
            case "YoungEmissary" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    false, 5, nilfgaard, 2);
            case "CahirMawrDyffrynAepCeallach" -> new Card(cardName, "closeCombatUnit", null,
                    false, 6, nilfgaard, 1);
            case "VattierDeRideaux" -> new Card(cardName, "closeCombatUnit", "spy",
                    false, 4, nilfgaard, 1);
            case "Puttkammer" -> new Card(cardName, "rangedUnit", null,
                    false, 3, nilfgaard, 1);
            case "BlackInfantryArcher" -> new Card(cardName, "rangedUnit", null,
                    false, 10, nilfgaard, 2);
            case "TiborEggebracht" -> new Card(cardName, "rangedUnit", "hero",
                    false, 10, nilfgaard, 1);
            case "RenualdAepMatsen" -> new Card(cardName, "rangedUnit", null,
                    false, 5, nilfgaard, 1);
            case "FringillaVigo" -> new Card(cardName, "rangedUnit", null,
                    false, 6, nilfgaard, 1);
            case "RottenMangonel" -> new Card(cardName, "siegeUnit", null,
                    false, 3, nilfgaard, 1);
            case "ZerrikanianFireScorpion" -> new Card(cardName, "siegeUnit", null,
                    false, 5, nilfgaard, 1);
            case "SiegeEngineer" -> new Card(cardName, "siegeUnit", null,
                    false, 6, nilfgaard, 1);
            case "MorvranVoorhis" -> new Card(cardName, "siegeUnit", "hero",
                    false, 10, nilfgaard, 1);
            case "Cynthia" -> new Card(cardName, "rangedUnit", null,
                    false, 4, nilfgaard, 1);
            case "EtolianAuxiliaryArchers" -> new Card(cardName, "rangedUnit", "medic",
                    false, 1, nilfgaard, 2);
            case "MennoCoehoorn" -> new Card(cardName, "closeCombatUnit", "hero&medic",
                    false, 10, nilfgaard, 1);
            case "Morteisen" -> new Card(cardName, "closeCombatUnit", null,
                    false, 3, nilfgaard, 1);
            case "NausicaaCavalryRider" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    false, 2, nilfgaard, 3);
            case "SiegeTechnician" -> new Card(cardName, "siegeUnit", "medic",
                    false, 0, nilfgaard, 1);
            case "Sweers" -> new Card(cardName, "rangedUnit", null,
                    false, 2, nilfgaard, 1);
            case "Vanhemar" -> new Card(cardName, "rangedUnit", null,
                    false, 4, nilfgaard, 1);
            case "Vreemde" -> new Card(cardName, "closeCombatUnit", null,
                    false, 2, nilfgaard, -1);         //there is no number for this card in dock;
            default -> null;
        };
    }
    public static Card monsters(String cardName){
        Monsters monsters = new Monsters();
        return switch (cardName) {

            default -> null;
        };
    }
    public static Card neutral(String cardName){
        return switch (cardName) {

            default -> null;
        };
    }
}
