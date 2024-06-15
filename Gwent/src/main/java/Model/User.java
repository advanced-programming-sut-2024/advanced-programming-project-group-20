package Model;

import java.util.ArrayList;

public class User {
    private Board board;
    private String username;
    private String password;
    private String nickName;
    private int numberOfGames;
    private int numberOfWins;
    private Faction faction;
    private Leader leader;
    private User opponentUser;
    private ArrayList<Card> totalCards= new ArrayList<>();
    private ArrayList<ArrayList<Card>> decks = new ArrayList<>();
    private ArrayList<ArrayList<Card>> decksByName = new ArrayList<>();
    private ArrayList<ArrayList<Card>> decksByAddress = new ArrayList<>();
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<GameHistory> gameHistories = new ArrayList<>();
    private int numberOfDraws;
    private int numberOfLose;
    private String email;
    private int questionNumber;
    private String answer;
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User loggedUser;
    private static User turnUser;
    private double maxPoint;

    public User(String username, String password, String nickName, String email) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
//        this.faction = faction;
        this.email = email;
    }

    public static User giveUserByUsername(String username) {
        for (User user : allUsers){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public int getRank() {
        return 0;
    }

    public int getSoldiers() {
        return 0;
    }

    public int numberOfHeroes() {
        return 0;

    }
    public double strengthOfCards(){
        return 0;
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
    }

    public ArrayList<Card> getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(ArrayList<Card> totalCards) {
        this.totalCards = totalCards;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    public ArrayList<ArrayList<Card>> getDecksByName() {
        return decksByName;
    }

    public void setDecksByName(ArrayList<ArrayList<Card>> decksByName) {
        this.decksByName = decksByName;
    }

    public ArrayList<ArrayList<Card>> getDecksByAddress() {
        return decksByAddress;
    }

    public void setDecksByAddress(ArrayList<ArrayList<Card>> decksByAddress) {
        this.decksByAddress = decksByAddress;
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

    public void setGameHistories(ArrayList<GameHistory> gameHistories) {
        this.gameHistories = gameHistories;
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

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
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

    public static User getTurnUser() {
        return turnUser;
    }

    public static void setTurnUser(User turnUser) {
        User.turnUser = turnUser;
    }

    public double getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(double maxPoint) {
        this.maxPoint = maxPoint;
    }
}
