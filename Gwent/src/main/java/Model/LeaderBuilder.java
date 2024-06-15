package Model;

public class LeaderBuilder {
    public static Leader nilfgaard(String cardName, Faction faction) {
        return switch (cardName) {
            case "realms_foltest_copper" -> new Leader(faction,"realms_foltest_copper");
            case "realms_foltest_bronze" -> new Leader(faction,"realms_foltest_bronze");
            case "realms_foltest_gold" -> new Leader(faction,"realms_foltest_gold");
            case "realms_foltest_silver" -> new Leader(faction,"realms_foltest_silver");
            case "realms_foltest_son_of_medell" -> new Leader(faction,"realms_foltest_son_of_medell");

            default -> null;
        };
    }

    public static Leader northernRealms(String cardName, Faction faction) {
        return switch (cardName) {
            case "realms_foltest_copper" -> new Leader(faction,"realms_foltest_copper");
            case "realms_foltest_bronze" -> new Leader(faction,"realms_foltest_bronze");
            case "realms_foltest_gold" -> new Leader(faction,"realms_foltest_gold");
            case "realms_foltest_silver" -> new Leader(faction,"realms_foltest_silver");
            case "realms_foltest_son_of_medell" -> new Leader(faction,"realms_foltest_son_of_medell");

            default -> null;
        };
    }

    public static Leader monsters(String cardName, Faction faction) {
        return switch (cardName) {
            case "realms_foltest_copper" -> new Leader(faction,"realms_foltest_copper");
            case "realms_foltest_bronze" -> new Leader(faction,"realms_foltest_bronze");
            case "realms_foltest_gold" -> new Leader(faction,"realms_foltest_gold");
            case "realms_foltest_silver" -> new Leader(faction,"realms_foltest_silver");
            case "realms_foltest_son_of_medell" -> new Leader(faction,"realms_foltest_son_of_medell");

            default -> null;
        };
    }

    public static Leader scoiaTael(String cardName, Faction faction) {
        return switch (cardName) {
            case "realms_foltest_copper" -> new Leader(faction,"realms_foltest_copper");
            case "realms_foltest_bronze" -> new Leader(faction,"realms_foltest_bronze");
            case "realms_foltest_gold" -> new Leader(faction,"realms_foltest_gold");
            case "realms_foltest_silver" -> new Leader(faction,"realms_foltest_silver");
            case "realms_foltest_son_of_medell" -> new Leader(faction,"realms_foltest_son_of_medell");

            default -> null;
        };
    }

    public static Leader skellige(String cardName, Faction faction) {
        return switch (cardName) {
            case "realms_foltest_copper" -> new Leader(faction,"realms_foltest_copper");
            case "realms_foltest_bronze" -> new Leader(faction,"realms_foltest_bronze");
            case "realms_foltest_gold" -> new Leader(faction,"realms_foltest_gold");
            case "realms_foltest_silver" -> new Leader(faction,"realms_foltest_silver");
            case "realms_foltest_son_of_medell" -> new Leader(faction,"realms_foltest_son_of_medell");

            default -> null;
        };
    }
}
