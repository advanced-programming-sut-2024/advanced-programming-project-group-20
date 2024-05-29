package Model;

import java.util.ArrayList;

public class Board {
    private ArrayList<Card> siege =new ArrayList<>();
    private ArrayList<Card> ranged =new ArrayList<>();
    private ArrayList<Card> closeCombat =new ArrayList<>();
    private ArrayList<Card> burnedCart =new ArrayList<>();
    private ArrayList<Card> hand =new ArrayList<>();
    private Card spell;
    private Leader leader;
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

    public ArrayList<Card> getBurnedCart() {
        return burnedCart;
    }

    public void setBurnedCart(ArrayList<Card> burnedCart) {
        this.burnedCart = burnedCart;
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
