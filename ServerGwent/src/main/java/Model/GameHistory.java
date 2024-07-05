package Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GameHistory {
    private String winnerName;
    private String opponentName;
    private int firstRoundPointMe = -1;
    private int firstRoundPointOpponent = -1;
    private int secondRoundPointMe = -1;
    private int secondRoundPointOpponent = -1;
    private int thirdRoundPointMe = -1;
    private int thirdRoundPointOpponent = -1;
    private int totalPointsMe;
    private int totalPointsOpponent;
    private String factionName;
    private String leaderName;
    private String oppFactionName;
    private String oppLeaderName;
    private String date;
    private ArrayList<HashMap<Integer,ArrayList<String>>> moves = new ArrayList<>();

    public ArrayList<HashMap<Integer, ArrayList<String>>> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<HashMap<Integer, ArrayList<String>>> moves) {
        this.moves = moves;
    }

    public GameHistory(User opponent, Date date) {
        this.opponentName = opponent.getUsername();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = sdf.format(date);
    }

    public void countTotalPoints() {
        totalPointsMe = firstRoundPointMe + secondRoundPointMe + thirdRoundPointMe;
        totalPointsOpponent = firstRoundPointOpponent + secondRoundPointOpponent + thirdRoundPointOpponent;
    }

    public String getWinner() {
        return winnerName;
    }

    public void setWinner(String winner) {
        this.winnerName = winner;
    }

    public int getFirstRoundPointMe() {
        return firstRoundPointMe;
    }

    public int getFirstRoundPointOpponent() {
        return firstRoundPointOpponent;
    }

    public int getSecondRoundPointMe() {
        return secondRoundPointMe;
    }

    public int getSecondRoundPointOpponent() {
        return secondRoundPointOpponent;
    }

    public int getThirdRoundPointMe() {
        return thirdRoundPointMe;
    }

    public int getThirdRoundPointOpponent() {
        return thirdRoundPointOpponent;
    }

    public void setThirdRoundPointOpponent(int thirdRoundPointOpponent) {
        this.thirdRoundPointOpponent = thirdRoundPointOpponent;
    }

    public void setFirstRoundPointMe(int firstRoundPointMe) {
        this.firstRoundPointMe = firstRoundPointMe;
    }

    public void setFirstRoundPointOpponent(int firstRoundPointOpponent) {
        this.firstRoundPointOpponent = firstRoundPointOpponent;
    }

    public void setSecondRoundPointMe(int secondRoundPointMe) {
        this.secondRoundPointMe = secondRoundPointMe;
    }

    public void setSecondRoundPointOpponent(int secondRoundPointOpponent) {
        this.secondRoundPointOpponent = secondRoundPointOpponent;
    }

    public void setThirdRoundPointMe(int thirdRoundPointMe) {
        this.thirdRoundPointMe = thirdRoundPointMe;
    }

    public int getTotalPointsMe() {
        return totalPointsMe;
    }

    public int getTotalPointsOpponent() {
        return totalPointsOpponent;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getOppLeaderName() {
        return oppLeaderName;
    }

    public void setOppLeaderName(String oppLeaderName) {
        this.oppLeaderName = oppLeaderName;
    }

    public String getOppFactionName() {
        return oppFactionName;
    }

    public void setOppFactionName(String oppFactionName) {
        this.oppFactionName = oppFactionName;
    }
}
