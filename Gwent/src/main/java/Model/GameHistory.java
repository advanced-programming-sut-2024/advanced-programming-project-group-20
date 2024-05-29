package Model;

import java.util.ArrayList;
import java.util.Date;

public class GameHistory {
    private User winner;
    private User opponent;
    private ArrayList<Double> userPoints = new ArrayList<>();
    private ArrayList<Double> opponentPoints = new ArrayList<>();
    private Date date;
    private double sumOfPoints(User user){
        return 0;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public User getOpponent() {
        return opponent;
    }

    public void setOpponent(User opponent) {
        this.opponent = opponent;
    }

    public ArrayList<Double> getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(ArrayList<Double> userPoints) {
        this.userPoints = userPoints;
    }

    public ArrayList<Double> getOpponentPoints() {
        return opponentPoints;
    }

    public void setOpponentPoints(ArrayList<Double> opponentPoints) {
        this.opponentPoints = opponentPoints;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
