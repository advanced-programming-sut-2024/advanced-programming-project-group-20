package Model;

import java.util.ArrayList;
import java.util.Date;

public class GameHistory {
    private static ArrayList<GameHistory> gameHistories;
    private User winner;
    private String opponentName;
    private double firstRoundPointMe;
    private double firstRoundPointOpponent;
    private double secondRoundPointMe;
    private double secondRoundPointOpponent;
    private double thirdRoundPointMe;
    private double thirdRoundPointOpponent;
    private double totalPointsMe;
    private double totalPointsOpponent;
    private Date date;

    public GameHistory(User opponent, Date date) {
        this.opponentName = opponent.getUsername();
        this.date = date;
    }

    public void countTotalPoints() {
        totalPointsMe = firstRoundPointMe + secondRoundPointMe + totalPointsMe;
        totalPointsOpponent = firstRoundPointOpponent + secondRoundPointOpponent + totalPointsOpponent;
    }

    public static ArrayList<GameHistory> getGameHistories() {
        return gameHistories;
    }

    private double sumOfPoints(User user) {
        return 0;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public double getFirstRoundPointMe() {
        return firstRoundPointMe;
    }

    public double getFirstRoundPointOpponent() {
        return firstRoundPointOpponent;
    }

    public double getSecondRoundPointMe() {
        return secondRoundPointMe;
    }

    public double getSecondRoundPointOpponent() {
        return secondRoundPointOpponent;
    }

    public double getThirdRoundPointMe() {
        return thirdRoundPointMe;
    }

    public double getThirdRoundPointOpponent() {
        return thirdRoundPointOpponent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }
}
