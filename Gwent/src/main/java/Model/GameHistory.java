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
    private String date;
    private String factionName;
    private String leaderName;
    private String oppFactionName;
    private String oppLeaderName;
    private ArrayList<HashMap<Integer, ArrayList<String>>> moves;

    public ArrayList<HashMap<Integer, ArrayList<String>>> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<HashMap<Integer, ArrayList<String>>> moves) {
        this.moves = moves;
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

    public GameHistory(User opponent, Date date) {
        this.opponentName = opponent.getUsername();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = sdf.format(date);
    }

    public void countTotalPoints() {
        if (firstRoundPointMe < 0) firstRoundPointMe = 0;
        if (secondRoundPointMe < 0) secondRoundPointMe = 0;
        if (thirdRoundPointMe < 0) thirdRoundPointMe = 0;
        if (firstRoundPointOpponent < 0) firstRoundPointOpponent = 0;
        if (secondRoundPointOpponent < 0) secondRoundPointOpponent = 0;
        if (thirdRoundPointOpponent < 0) thirdRoundPointOpponent = 0;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
