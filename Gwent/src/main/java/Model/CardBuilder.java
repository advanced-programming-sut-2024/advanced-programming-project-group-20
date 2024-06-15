package Model;


public class CardBuilder {


    public static Card skellige(String cardName, Faction faction) {
        return switch (cardName) {
            case "Mardroeme" -> new Card(cardName, "spell", "mardoeme",
                    true, 0, faction, 3);
            case "Berserker" -> new Card(cardName, "closeCombatUnit", "berserker",
                    true, 4, faction, 1);
            case "Vidkaarl" -> new Card(cardName, "closeCombatUnit", "moralBoost",
                    true, 14, faction, 1111);  //there is no number for this card in dock;
            case "Svanrige", "Udalryk", "DonarAnHindar" -> new Card(cardName, "closeCombatUnit", null,
                    false, 4, faction, 1);
            case "ClanAnCraite" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    false, 6, faction, 3);
            case "MadmanLugos" -> new Card(cardName, "closeCombatUnit", null,
                    false, 6, faction, 1);
            case "Cerys" -> new Card(cardName, "closeCombatUnit", "hero&muster",
                    true, 10, faction, 1);
            case "Kambi" -> new Card(cardName, "closeCombatUnit", "hero&transformers",
                    true, 11, faction, 1);
            case "BirnaBran" -> new Card(cardName, "closeCombatUnit", "medic",
                    false, 2, faction, 1);
            case "ClanDrummondShieldmaiden" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    true, 4, faction, 1);
            case "ClanDimunPirate" -> new Card(cardName, "rangedUnit", "scorch",
                    true, 6, faction, 1);
            case "ClanBrokvarArcher" -> new Card(cardName, "rangedUnit", null,
                    false, 6, faction, 3);
            case "Ermion" -> new Card(cardName, "rangedUnit", "hero&mardroeme",
                    false, 8, faction, 1);
            case "Hjalmar" -> new Card(cardName, "rangedUnit", "hero",
                    false, 10, faction, 1);
            case "YoungBerserker" -> new Card(cardName, "rangedUnit", "berserker",
                    true, 2, faction, 3);
            case "YoungVidkaarl" -> new Card(cardName, "rangedUnit", "tightBond",
                    true, 8, faction, 3); //there is no number for this card in dock;
            case "LightLongship" -> new Card(cardName, "rangedUnit", "muster",
                    false, 4, faction, 3);
            case "WarLongship" -> new Card(cardName, "siegeUnit", "tightBond",
                    false, 6, faction, 3);
            case "DraigBon-Dhu" -> new Card(cardName, "siegeUnit", "commander'sHorn",
                    false, 2, faction, 1);
            case "Olaf" -> new Card(cardName, "agileUnit", "moralBoost",
                    false, 12, faction, 1);
            default -> null;
        };
    }

    public static Card scoiaTael(String cardName, Faction faction) {
        return switch (cardName) {
            case "ElvenSkirmisher" -> new Card(cardName, "rangedUnit", "muster",
                    false, 2, faction, 3);
            case "Yaevinn" -> new Card(cardName, "agileUnit", null,
                    false, 6, faction, 1);
            case "Ciaranaep" -> new Card(cardName, "agileUnit", null,
                    false, 3, faction, 1);
            case "DennisCranmer" -> new Card(cardName, "closeCombatUnit", null,
                    false, 6, faction, 1);
            case "DolBlathannaScout" -> new Card(cardName, "agileUnit", null,
                    false, 6, faction, 3);
            case "DolBlathannaArcher" -> new Card(cardName, "rangedUnit", null,
                    false, 4, faction, 3);
            case "DwarvenSkirmisher" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 3, faction, 3);
            case "HavekarHealer" -> new Card(cardName, "rangedUnit", "medic",
                    false, 0, faction, 3);
            case "HavekarSmuggler" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 5, faction, 3);
            case "IdaEmeanaep" -> new Card(cardName, "rangedUnit", null,
                    false, 6, faction, 1);
            case "Riordain" -> new Card(cardName, "rangedUnit", null,
                    false, 1, faction, 1);
            case "Toruviel" -> new Card(cardName, "rangedUnit", null,
                    false, 2, faction, 1);
            case "VriheddBrigadeRecruit" -> new Card(cardName, "rangedUnit", null,
                    false, 4, faction, 1);
            case "VriheddBrigadeVeteran" -> new Card(cardName, "agileUnit", null,
                    false, 5, faction, 2);
            case "Milva" -> new Card(cardName, "rangedUnit", "moralBoost",
                    false, 10, faction, 1);
            case "Seasenthessis", "Eithne" -> new Card(cardName, "rangedUnit", "hero"
                    , false, 10, faction, 1);
            case "Schirru" -> new Card(cardName, "siegeUnit", "scorch",
                    true, 8, faction, 1);
            case "IsengrimFaoiltiarna" ->
                    new Card(cardName, "closeCombatUnit", "hero&moralBoost",
                            false, 10, faction, 1);


            default -> null;
        };
    }

    public static Card northernRealms(String cardName, Faction faction) {
        return switch (cardName) {
            case "Ballista" -> new Card(cardName, "siegeUnit", null,
                    false, 6, faction, 2);
            case "Catapult" -> new Card(cardName, "siegeUnit", "tightBond",
                    false, 8, faction, 2);
            case "DragonHunter" -> new Card(cardName, "rangedUnit", "tightBond",
                    false, 5, faction, 3);
            case "DunBannerMedic" -> new Card(cardName, "siegeUnit", "medic",
                    false, 5, faction, 1);
            case "EsteradThyssen", "JohnNatalis", "VernonRoche" ->
                    new Card(cardName, "closeCombatUnit", "hero",
                            false, 10, faction, 1);
            case "KaedweniSiegeExpert" -> new Card(cardName, "siegeUnit", "moralBoost",
                    false, 1, faction, 3);
            case "PhilippaEilhart" -> new Card(cardName, "rangedUnit", "hero",
                    false, 10, faction, 1);
            case "PoorFuckingInfantry" ->
                    new Card(cardName, "closeCombatUnit", "tightBond",
                            false, 1, faction, 4);
            case "PrinceStennis" -> new Card(cardName, "closeCombatUnit", "spy",
                    false, 5, faction, 1);
            case "RedanianFootSoldier" -> new Card(cardName, "closeCombatUnit", null,
                    false, 1, faction, 2);
            case "SabrinaGlevissing" -> new Card(cardName, "rangedUnit", null,
                    false, 4, faction, 1);
            case "SiegeTower" -> new Card(cardName, "siegeUnit", null,
                    false, 6, faction, 1);
            case "SiegfriedofDenesle", "Ves" ->
                    new Card(cardName, "closeCombatUnit", null,
                            false, 5, faction, 1);
            case "SigismundDijkstra" -> new Card(cardName, "closeCombatUnit", "spy",
                    false, 4, faction, 1);
            case "SíledeTansarville" -> new Card(cardName, "rangedUnit", null,
                    false, 5, faction, 1);
            case "Thaler" -> new Card(cardName, "siegeUnit", "spy",
                    false, 1, faction, 1);
            case "YarpenZirgrin" -> new Card(cardName, "closeCombatUnit", null,
                    false, 2, faction, 1);
            default -> null;
        };
    }

    public static Card nilfgaard(String cardName, Faction faction) {
        return switch (cardName) {
            case "ImperaBrigadeGuard" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    false, 3, faction, 4);
            case "StefanSkellen" -> new Card(cardName, "closeCombatUnit", "spy",
                    false, 9, faction, 1);
            case "YoungEmissary" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    false, 5, faction, 2);
            case "CahirMawrDyffrynAepCeallach" -> new Card(cardName, "closeCombatUnit", null,
                    false, 6, faction, 1);
            case "VattierDeRideaux" -> new Card(cardName, "closeCombatUnit", "spy",
                    false, 4, faction, 1);
            case "Puttkammer" -> new Card(cardName, "rangedUnit", null,
                    false, 3, faction, 1);
            case "BlackInfantryArcher" -> new Card(cardName, "rangedUnit", null,
                    false, 10, faction, 2);
            case "TiborEggebracht" -> new Card(cardName, "rangedUnit", "hero",
                    false, 10, faction, 1);
            case "RenualdAepMatsen" -> new Card(cardName, "rangedUnit", null,
                    false, 5, faction, 1);
            case "FringillaVigo" -> new Card(cardName, "rangedUnit", null,
                    false, 6, faction, 1);
            case "RottenMangonel" -> new Card(cardName, "siegeUnit", null,
                    false, 3, faction, 1);
            case "ZerrikanianFireScorpion" -> new Card(cardName, "siegeUnit", null,
                    false, 5, faction, 1);
            case "SiegeEngineer" -> new Card(cardName, "siegeUnit", null,
                    false, 6, faction, 1);
            case "MorvranVoorhis" -> new Card(cardName, "siegeUnit", "hero",
                    false, 10, faction, 1);
            case "Cynthia" -> new Card(cardName, "rangedUnit", null,
                    false, 4, faction, 1);
            case "EtolianAuxiliaryArchers" -> new Card(cardName, "rangedUnit", "medic",
                    false, 1, faction, 2);
            case "MennoCoehoorn" -> new Card(cardName, "closeCombatUnit", "hero&medic",
                    false, 10, faction, 1);
            case "Morteisen" -> new Card(cardName, "closeCombatUnit", null,
                    false, 3, faction, 1);
            case "NausicaaCavalryRider" -> new Card(cardName, "closeCombatUnit", "tightBond",
                    false, 2, faction, 3);
            case "SiegeTechnician" -> new Card(cardName, "siegeUnit", "medic",
                    false, 0, faction, 1);
            case "Sweers" -> new Card(cardName, "rangedUnit", null,
                    false, 2, faction, 1);
            case "Vanhemar" -> new Card(cardName, "rangedUnit", null,
                    false, 4, faction, 1);
            case "Vreemde" -> new Card(cardName, "closeCombatUnit", null,
                    false, 2, faction, -1);         //there is no number for this card in dock;
            default -> null;
        };
    }

    public static Card monsters(String cardName, Faction faction) {
        return switch (cardName) {
            case "Draug" -> new Card(cardName, "closeCombatUnit", "hero",
                    false, 10, faction, 1);
            case "Leshen" -> new Card(cardName, "closeCombatUnit", "hero",
                    false, 10, faction, 1);
            case "Kayran" -> new Card(cardName, "agile", "MoraleBoostAndHero",
                    false, 8, faction, 1);
            case "Toad" -> new Card(cardName, "rangedUnit", "Scorch",
                    true, 7, faction, 1);
            case "ArachasBehemoth" -> new Card(cardName, "siegeUnit", "muster",
                    false, 6, faction, 1);
            case "CroneWeavess" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 6, faction, 1);
            case "CroneWhispess" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 6, faction, 1);
            case "EarthElemental" -> new Card(cardName, "siegeUnit", null,
                    false, 6, faction, 1);
            case "Fiend" -> new Card(cardName, "closeCombatUnit", null,
                    false, 6, faction, 1);
            case "FireElemental" -> new Card(cardName, "siegeUnit", null,
                    false, 6, faction, 1);
            case "Forktail" -> new Card(cardName, "closeCombatUnit", null,
                    false, 5, faction, 1);
            case "GraveHag" -> new Card(cardName, "rangedUnit", null,
                    false, 5, faction, 1);
            case "Griffin" -> new Card(cardName, "closeCombatUnit", null,
                    false, 5, faction, 1);
            case "IceGiant" -> new Card(cardName, "siegeUnit", null,
                    false, 5, faction, 1);
            case "PlagueMaiden" -> new Card(cardName, "closeCombatUnit", null,
                    false, 5, faction, 1);
            case "VampireKatakan" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 5, faction, 1);
            case "Werewolf" -> new Card(cardName, "closeCombatUnit", null,
                    false, 5, faction, 1);
            case "Arachas" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 4, faction, 3);
            case "VampireBruxa" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 4, faction, 1);
            case "VampireEkimmara" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 4, faction, 1);
            case "VampireFleder" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 4, faction, 1);
            case "VampireGarkain" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 4, faction, 1);
            case "Cockatrice" -> new Card(cardName, "rangedUnit", null,
                    false, 2, faction, 1);
            case "Endrega" -> new Card(cardName, "rangedUnit", null,
                    false, 2, faction, 1);
            case "Foglet" -> new Card(cardName, "closeCombatUnit", null,
                    false, 2, faction, 1);
            case "Harpy" -> new Card(cardName, "agile", null,
                    false, 2, faction, 1);
            case "Nekker" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 2, faction, 3);
            case "Wyvern" -> new Card(cardName, "rangedUnit", null,
                    false, 2, faction, 1);
            case "Ghoul" -> new Card(cardName, "closeCombatUnit", "muster",
                    false, 1, faction, 3);
            default -> null;
        };
    }

    public static Card neutral(String cardName) {
        return switch (cardName) {
            case "BitingFrost" -> new Card(cardName, "weather", null,
                    true, 0, null, 3);
            case "ImpenetrableFog" -> new Card(cardName, "weather", null,
                    true, 0, null, 3);
            case "TorrentialRain" -> new Card(cardName, "weather", null,
                    true, 0, null, 3);
            case "Decoy" -> new Card(cardName, "spell", null,
                    true, 0, null, 3);
            case "Dandelion" -> new Card(cardName, "closeCombatUnit", "Commander’sHorn",
                    true, 2, null, 1);
            case "EmielRegis" -> new Card(cardName, "closeCombatUnit", null,
                    false, 5, null, 1);
            case "GaunterO,Dimm" -> new Card(cardName, "siegeUnit", "muster",
                    false, 2, null, 1);
            case "GaunterO’DimmDarkness" -> new Card(cardName, "rangedUnit", "muster",
                    false, 4, null, 3);
            case "MysteriousElf" -> new Card(cardName, "closeCombat", "spy&Hero",
                    false, 0, null, 1);
            case "OlgierdVonEverc" -> new Card(cardName, "agileUnit", "moralBoost",
                    false, 6, null, 1);
            case "TrissMerigold" -> new Card(cardName, "closeCombatUnit", "hero",
                    false, 7, null, 1);
            case "Villentretenmerth" -> new Card(cardName, "closeCombatUnit", "scorch",
                    true, 7, null, 1);
            case "YenneferofVengerberg" -> new Card(cardName, "rangedUnit", "hero&medic",
                    false, 7, null, 1);
            case "ZoltanChivay" -> new Card(cardName, "closeCombatUnit", null,
                    false, 5, null, 1);
            default -> null;
        };
    }
}
