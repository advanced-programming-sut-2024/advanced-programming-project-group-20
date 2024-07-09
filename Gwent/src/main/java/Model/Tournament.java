package Model;

import java.util.ArrayList;

public class Tournament {
    private static ArrayList<Tournament> tournaments = new ArrayList<>();
    private static Tournament tournament;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> activeGames = new ArrayList<>();
    private String[] table = new String[28];

    public static ArrayList<Tournament> getTournaments () {
        return tournaments;
    }
    public static Tournament getTournament () {
        if (tournament == null) tournament = new Tournament();
        return tournament;
    }
    public String[] getTable() {
        return table;
    }
    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<String> getActiveGames() {
        return activeGames;
    }

    public static void setTournament(Tournament tournament) {
        Tournament.tournament = tournament;
    }
}
