package Model;

import java.util.ArrayList;
import java.util.Date;

public class GameHistory {
    private User winner;
    private String opponentName;
    private double firstRoundPointMe = -1;
    private double firstRoundPointOpponent = -1;
    private double secondRoundPointMe = -1;
    private double secondRoundPointOpponent = -1;
    private double thirdRoundPointMe = -1;
    private double thirdRoundPointOpponent = -1;
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

    public void setThirdRoundPointOpponent(double thirdRoundPointOpponent) {
        this.thirdRoundPointOpponent = thirdRoundPointOpponent;
    }

    public void setFirstRoundPointMe(double firstRoundPointMe) {
        this.firstRoundPointMe = firstRoundPointMe;
    }

    public void setFirstRoundPointOpponent(double firstRoundPointOpponent) {
        this.firstRoundPointOpponent = firstRoundPointOpponent;
    }

    public void setSecondRoundPointMe(double secondRoundPointMe) {
        this.secondRoundPointMe = secondRoundPointMe;
    }

    public void setSecondRoundPointOpponent(double secondRoundPointOpponent) {
        this.secondRoundPointOpponent = secondRoundPointOpponent;
    }

    public void setThirdRoundPointMe(double thirdRoundPointMe) {
        this.thirdRoundPointMe = thirdRoundPointMe;
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
