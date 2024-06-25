package Model;

import java.util.ArrayList;

public class Board {
    private ArrayList<Card> siege =new ArrayList<>();
    private ArrayList<Card> ranged  =new ArrayList<>();
    private ArrayList<Card> closeCombat =new ArrayList<>() ;
    private ArrayList<Card> burnedCard = new ArrayList<>();
    private ArrayList<Card> hand =new ArrayList<>();
    private ArrayList<Card> siegeNext = new ArrayList<>();
    private ArrayList<Card> rangedNext = new ArrayList<>();
    private ArrayList<Card> closeNext = new ArrayList<>();
    private Card spell;
    private Leader leader;
    private boolean hasPlayedOne =false;
    private ArrayList<Card> weather = new ArrayList<>();


    public void addWeather(Card card) {
        weather.add(card);
    }

    public ArrayList<Card> getWeather() {
        return weather;
    }

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

    public ArrayList<Card> getSiegeNext() {
        return siegeNext;
    }

    public void setSiegeNext(ArrayList<Card> siegeNext) {
        this.siegeNext = siegeNext;
    }

    public ArrayList<Card> getRangedNext() {
        return rangedNext;
    }

    public void setRangedNext(ArrayList<Card> rangedNext) {
        this.rangedNext = rangedNext;
    }

    public ArrayList<Card> getCloseNext() {
        return closeNext;
    }

    public void setCloseNext(ArrayList<Card> closeNext) {
        this.closeNext = closeNext;
    }
}
