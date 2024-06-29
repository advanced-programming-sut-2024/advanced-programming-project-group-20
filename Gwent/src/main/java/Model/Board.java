package Model;


import javafx.animation.Timeline;
import javafx.css.FontFace;
import javafx.scene.layout.HBox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Board {
    private ArrayList<Card> siege = new ArrayList<>();
    private ArrayList<Card> ranged = new ArrayList<>();
    private ArrayList<Card> closeCombat = new ArrayList<>();
    private ArrayList<Card> burnedCard = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<Card> siegeNext = new ArrayList<>();
    private ArrayList<Card> rangedNext = new ArrayList<>();
    private ArrayList<Card> closeNext = new ArrayList<>();
    private Leader leader;
    private boolean hasPlayedOne = false;
    private ArrayList<Card> weather = new ArrayList<>();
    public boolean[] leaderBoost = {false, false, false, false, false};


    public void addWeather(Card card) {
        weather.add(card);
    }

    public ArrayList<Card> getWeather() {
        return weather;
    }

    public ArrayList<Card> getSiege() {
        return siege;
    }

    public ArrayList<Card> getRanged() {
        return ranged;
    }

    public ArrayList<Card> getCloseCombat() {
        return closeCombat;
    }

    public ArrayList<Card> getBurnedCard() {
        return burnedCard;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public boolean isHasPlayedOne() {
        return hasPlayedOne;
    }

    public void setHasPlayedOne(boolean hasPlayedOne) {
        this.hasPlayedOne = hasPlayedOne;
    }

    public ArrayList<Card> getSiegeNext() {
        return siegeNext;
    }

    public ArrayList<Card> getRangedNext() {
        return rangedNext;
    }

    public ArrayList<Card> getCloseNext() {
        return closeNext;
    }

}
