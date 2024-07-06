package Model;

import java.util.ArrayList;

public class Tournament {
    private static ArrayList<Tournament> tournaments = new ArrayList<>();
    private static Tournament tournament;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> activeGames = new ArrayList<>();
    private String[] table = new String[28];
    private String champion;

    public static ArrayList<Tournament> getTournaments () {
        return tournaments;
    }
    public static Tournament getTournament () {
        if (tournament == null) {tournament = new Tournament();}
        return tournament;
    }
    public String[] getTable() {
        return table;
    }
    public ArrayList<String> getNames() {
        return names;
    }
    public void addPlayer(String name) {
        if (names.contains(name)) return;
        if (names.size() > 8) return;
        names.add(name);
        table[names.size()-1] = name;
    }

    public ArrayList<String> getActiveGames() {
        return activeGames;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public static void setTournament(Tournament tournament) {
        Tournament.tournament = tournament;
    }

}
