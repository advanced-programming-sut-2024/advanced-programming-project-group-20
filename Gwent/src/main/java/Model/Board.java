package Model;

import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Board {
    private HBox siege ;
    private HBox ranged ;
    private HBox closeCombat ;
    private HBox burnedCard ;
    private ArrayList<Card> hand =new ArrayList<>();
    private Card spell;
    private Leader leader;
    private Card weatherCard;


    public HBox getSiege() {
        return siege;
    }

    public void setSiege(HBox siege) {
        this.siege = siege;
    }

    public HBox getRanged() {
        return ranged;
    }

    public void setRanged(HBox ranged) {
        this.ranged = ranged;
    }

    public HBox getCloseCombat() {
        return closeCombat;
    }

    public void setCloseCombat(HBox closeCombat) {
        this.closeCombat = closeCombat;
    }

    public HBox getBurnedCard() {
        return burnedCard;
    }

    public void setBurnedCard(HBox burnedCard) {
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

}
