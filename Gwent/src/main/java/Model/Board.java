package Model;

import javafx.animation.Timeline;
import javafx.scene.layout.HBox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Board {
    private ArrayList<Card> siege =new ArrayList<>();
    private ArrayList<Card> ranged  =new ArrayList<>();
    private ArrayList<Card> closeCombat =new ArrayList<>() ;
    private ArrayList<Card> burnedCard = new ArrayList<>();
    private ArrayList<Card> hand =new ArrayList<>();
    private Card spell;
    private Leader leader;
    private boolean hasPlayedOne =false;
    private Card weatherCard;


    public ArrayList<Card> getSiege() {
        return siege;
    }

    public void setSiege(ArrayList<Card> siege) {
        this.siege = siege;
    }

    public ArrayList<Card> getRanged() {
        return ranged;
    }

    public void setRanged(ArrayList<Card> ranged) {
        this.ranged = ranged;
    }

    public ArrayList<Card> getCloseCombat() {
        return closeCombat;
    }

    public void setCloseCombat(ArrayList<Card> closeCombat) {
        this.closeCombat = closeCombat;
    }

    public ArrayList<Card> getBurnedCard() {
        return burnedCard;
    }

    public void setBurnedCard(ArrayList<Card> burnedCard) {
        this.burnedCard = burnedCard;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public Card getSpell() {
        return spell;
    }

    public void setSpell(Card spell) {
        this.spell = spell;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public boolean isHasPlayedOne() {
        return hasPlayedOne;
    }

    public void setHasPlayedOne(boolean hasPlayedOne) {
        this.hasPlayedOne = hasPlayedOne;
    }
}
