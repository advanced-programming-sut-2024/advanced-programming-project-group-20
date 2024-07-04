package Model;

import Model.Factions.Nilfgaard;

import java.io.*;
import java.util.ArrayList;
import java.util.Formattable;
import java.util.HashMap;

public class User {
     private ArrayList<GameHistory> gameHistories = new ArrayList<>();
    transient public Board board = new Board();
    transient private GameHistory activeGame;
    private String username;
    private String password;
    private String nickName;
    private String secureQuestion;
    private String secureAnswer;
     private ArrayList<String> friends = new ArrayList<>();
    transient private Faction faction;
    transient private Leader leader;
    transient private User opponentUser;
    transient private ArrayList<ArrayList<Card>> decks = new ArrayList<>();
    transient private ArrayList<ArrayList<Card>> decksByName = new ArrayList<>();
    transient private ArrayList<ArrayList<Card>> decksByAddress = new ArrayList<>();
    transient private ArrayList<Card> deck = new ArrayList<>();
    private int numberOfDraws;
    private int numberOfLose;
    private int numberOfWins;
    private int numberOfGames;
    private String email;
    private String answer;
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User loggedUser;
    private static User turnUser;
    private int maxPoint;
    private ArrayList<String> gameRequests = new ArrayList<>();
    private boolean isPassed = false;
    private boolean isFullHealth = true;
    private boolean firstTurn = true;
    private boolean isReady;
    private boolean turn;
    private int rank;
    private HashMap<Integer, ArrayList<String>> cards;
    private String oppName;
    private String lastSeen = "longtime ago";
    private String factionName;
    private String leaderName;
    private ArrayList<String> friendRequests =new ArrayList<>();
    private boolean privateGame = true;

    public String getFactionName() {
        return factionName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public boolean isPrivateGame() {
        return privateGame;
    }

    public void setPrivateGame(boolean privateGame) {
        this.privateGame = privateGame;
    }

    public String getOppName() {
        return oppName;
    }

    public void setOppName(String oppName) {
        this.oppName = oppName;
    }

    public User(String username, String password, String nickName, String email, String secureQuestion, String secureAnswer) {
        this.activeGame = null;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.secureQuestion = secureQuestion;
        this.secureAnswer = secureAnswer;
        allUsers.add(this);
        numberOfLose = numberOfWins = numberOfDraws = numberOfGames = maxPoint = 0;
    }
    public User(String username) {
        this.username = username;
    }

    public HashMap<Integer, ArrayList<String>> getCards() {
        return cards;
    }

    public void setCards(HashMap<Integer, ArrayList<String>> cards) {
        this.cards = cards;
    }

    public void hashMapMaker() {
        cards = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        for (Card card : this.getBoard().getSiege()) {
            names.add(card.getName());
        }
        cards.put(0,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getBoard().getRanged()) {
            names.add(card.getName());
        }
        cards.put(1,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getBoard().getCloseCombat()) {
            names.add(card.getName());
        }
        cards.put(2,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getBoard().getCloseCombat()) {
            names.add(card.getName());
        }
        cards.put(3,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getBoard().getRanged()) {
            names.add(card.getName());
        }
        cards.put(4,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getBoard().getSiege()) {
            names.add(card.getName());
        }
        cards.put(5,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getBoard().getWeather()) {
            names.add(card.getName());
        }
        cards.put(6,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getBoard().getWeather()) {
            names.add(card.getName());
        }
        cards.put(7,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getBoard().getSiegeNext()) {
            names.add(card.getName());
        }
        cards.put(8,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getBoard().getRangedNext()) {
            names.add(card.getName());
        }
        cards.put(9,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getBoard().getCloseNext()) {
            names.add(card.getName());
        }
        cards.put(10,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getBoard().getCloseNext()) {
            names.add(card.getName());
        }
        cards.put(11,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getBoard().getRangedNext()) {
            names.add(card.getName());
        }
        cards.put(12,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getBoard().getSiegeNext()) {
            names.add(card.getName());
        }
        cards.put(13,new ArrayList<>(names));
        names.clear();
        for (Card card : deck) {
            names.add(card.getName());
        }
        cards.put(14,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getDeck()) {
            names.add(card.getName());
        }
        cards.put(15,new ArrayList<>(names));
        names.clear();
        if (this.getLeader().isUsed()) names.add("true");
        else names.add("false");
        for (Boolean b : this.getBoard().leaderBoost) {
            if (b) names.add("true");
            else names.add("false");
        }
        if (this.isPassed) names.add("true");
        else names.add("false");
        if (this.isFullHealth) names.add("true");
        else names.add("false");
        cards.put(16,new ArrayList<>(names));
        names.clear();
        if (this.getOpponentUser().getLeader().isUsed()) names.add("true");
        else names.add("false");
        for (Boolean b : this.getOpponentUser().getBoard().leaderBoost) {
            if (b) names.add("true");
            else names.add("false");
        }
        if (this.getOpponentUser().isPassed) names.add("true");
        else names.add("false");
        if (this.getOpponentUser().isFullHealth) names.add("true");
        else names.add("false");
        cards.put(17,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getBoard().getHand()) {
            names.add(card.getName());
        }
        cards.put(18,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getBoard().getHand()) {
            names.add(card.getName());
        }
        cards.put(19,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getBoard().getBurnedCard()) {
            names.add(card.getName());
        }
        cards.put(20,new ArrayList<>(names));
        names.clear();
        for (Card card : this.getOpponentUser().getBoard().getBurnedCard()) {
            names.add(card.getName());
        }
        cards.put(21,new ArrayList<>(names));
    }

    public void boardMaker() {
        this.board = new Board();
        this.deck = new ArrayList<>();
        this.getOpponentUser().board = new Board();
        this.getOpponentUser().deck = new ArrayList<>();
        if (cards.get(0) != null) {
            for (String name : cards.get(0)) {
                this.getBoard().getSiege().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(1) != null) {
            for (String name : cards.get(1)) {
                this.getBoard().getRanged().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(2) != null) {
            for (String name : cards.get(2)) {
                this.getBoard().getCloseCombat().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(5) != null) {
            for (String name : cards.get(5)) {
                this.getOpponentUser().getBoard().getSiege().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(4) != null) {
            for (String name : cards.get(4)) {
                this.getOpponentUser().getBoard().getRanged().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(3) != null) {
            for (String name : cards.get(3)) {
                this.getOpponentUser().getBoard().getCloseCombat().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(6) != null) {
            for (String name : cards.get(6)) {
                this.getBoard().getWeather().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(7) != null) {
            for (String name : cards.get(7)) {
                this.getOpponentUser().getBoard().getWeather().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(8) != null) {
            for (String name : cards.get(8)) {
                this.getBoard().getSiegeNext().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(9) != null) {
            for (String name : cards.get(9)) {
                this.getBoard().getRangedNext().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(10) != null) {
            for (String name : cards.get(10)) {
                this.getBoard().getCloseNext().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(13) != null) {
            for (String name : cards.get(13)) {
                this.getOpponentUser().getBoard().getSiegeNext().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(12) != null) {
            for (String name : cards.get(12)) {
                this.getOpponentUser().getBoard().getRangedNext().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(11) != null) {
            for (String name : cards.get(11)) {
                this.getOpponentUser().getBoard().getCloseNext().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(14) != null) {
            for (String name : cards.get(14)) {
                this.getDeck().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(15) != null) {
            for (String name : cards.get(15)) {
                this.getOpponentUser().getDeck().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(16) != null) {
            ArrayList<String > strings = cards.get(16);
            if (strings.get(0).equals("true")) this.getLeader().setUsed(true);
            else if (strings.get(0).equals("false")) this.getLeader().setUsed(false);
            for (int i = 1; i < 6; i++) {
                this.getBoard().leaderBoost[i - 1] = strings.get(i).equals("true");
            }
            this.setPassed(strings.get(6).equals("true"));
            this.setFullHealth(strings.get(7).equals("true"));
        }
        if (cards.get(17) != null) {
            ArrayList<String > strings = cards.get(17);
            if (strings.get(0).equals("true")) this.getOpponentUser().getLeader().setUsed(true);
            else if (strings.get(0).equals("false")) this.getOpponentUser().getLeader().setUsed(false);
            for (int i = 1; i < 6; i++) {
                this.getOpponentUser().getBoard().leaderBoost[i - 1] = strings.get(i).equals("true");
            }
            this.getOpponentUser().setPassed(strings.get(6).equals("true"));
            this.getOpponentUser().setFullHealth(strings.get(7).equals("true"));
        }
        if (cards.get(18) != null) {
            for (String name : cards.get(18)) {
                this.getBoard().getHand().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(19) != null) {
            for (String name : cards.get(19)) {
                this.getOpponentUser().getBoard().getHand().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(20) != null) {
            for (String name : cards.get(20)) {
                this.getBoard().getBurnedCard().add(Card.giveCardByName2(name));
            }
        }
        if (cards.get(21) != null) {
            for (String name : cards.get(21)) {
                this.getOpponentUser().getBoard().getBurnedCard().add(Card.giveCardByName2(name));
            }
        }
    }

    public void readyForGame() {
        this.board = new Board();
        this.faction = new Nilfgaard();
        this.leader = LeaderBuilder.nilfgaard("EmperorOfNilfgaard",this.faction);
        this.deck = new ArrayList<>();
        isPassed = false;
        isFullHealth = true;
        firstTurn = true;
    }
    public GameHistory getActiveGame() {
        return activeGame;
    }

    public void setActiveGame(GameHistory activeGame) {
        this.activeGame = activeGame;
    }

    public static User giveUserByUsername(String username) {
        for (User user : allUsers){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public static User getUserByName(String username) {
        for (User user: allUsers) {
            if (username.equals(user.getUsername())){
                return user;
            }
        }
        return null;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public User getOpponentUser() {
        return opponentUser;
    }

    public void setOpponentUser(User opponentUser) {
        this.opponentUser = opponentUser;
        if (opponentUser == null) return;
        this.oppName = opponentUser.getUsername();
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public ArrayList<GameHistory> getGameHistories() {
        return gameHistories;
    }

    public int getNumberOfDraws() {
        return numberOfDraws;
    }

    public void setNumberOfDraws(int numberOfDraws) {
        this.numberOfDraws = numberOfDraws;
    }

    public int getNumberOfLose() {
        return numberOfLose;
    }

    public void setNumberOfLose(int numberOfLose) {
        this.numberOfLose = numberOfLose;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        User.loggedUser = loggedUser;
    }


    public double getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(int maxPoint) {
        this.maxPoint = maxPoint;
    }

    public String getSecureQuestion() {
        return secureQuestion;
    }

    public String getSecureAnswer() {
        return secureAnswer;
    }

    public void setSecureAnswer(String secureAnswer) {
        this.secureAnswer = secureAnswer;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public boolean isFullHealth() {
        return isFullHealth;
    }

    public void setFullHealth(boolean fullHealth) {
        isFullHealth = fullHealth;
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(boolean firstTurn) {
        this.firstTurn = firstTurn;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public void mergeActiveGame(User user) {
        if (activeGame == null) activeGame = user.getActiveGame();
        activeGame.setFirstRoundPointMe(user.activeGame.getFirstRoundPointOpponent());
        activeGame.setSecondRoundPointMe(user.activeGame.getSecondRoundPointOpponent());
        activeGame.setThirdRoundPointMe(user.activeGame.getThirdRoundPointOpponent());
        activeGame.setFirstRoundPointOpponent(user.activeGame.getFirstRoundPointMe());
        activeGame.setSecondRoundPointOpponent(user.activeGame.getSecondRoundPointMe());
        activeGame.setThirdRoundPointOpponent(user.activeGame.getThirdRoundPointMe());
        if (user.activeGame.getWinner() != null) activeGame.setWinner(user.activeGame.getWinner());
        activeGame.countTotalPoints();
    }
}
