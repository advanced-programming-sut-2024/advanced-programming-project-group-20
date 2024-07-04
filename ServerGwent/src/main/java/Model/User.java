package Model;


import Controller.ApplicationController;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private ArrayList<GameHistory> gameHistories = new ArrayList<>();
    transient private GameHistory activeGame;
    private String username;
    private String password;
    private String nickName;
    private String secureQuestion;
    private String secureAnswer;
    private ArrayList<String > friends = new ArrayList<>();
    private int numberOfDraws;
    private int numberOfLose;
    private int numberOfWins;
    private int numberOfGames;
    private String email;
    private String answer;
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User loggedUser;
    private int maxPoint;
    private HashMap<Integer, ArrayList<String>> cards;
    private boolean isPassed = false;
    private boolean isFullHealth = true;
    private boolean firstTurn = true;
    private boolean isReady;
    private int rank;
    private boolean turn;
    private String oppName;
    private String factionName;
    private String leaderName;
    private String lastSeen = "longtime ago";
    private ArrayList<String> friendRequests =new ArrayList<>();
    private boolean search;
    private ArrayList<String> gameRequests = new ArrayList<>();
    private boolean privateGame = true;

    public boolean isPrivateGame() {
        return privateGame;
    }

    public void setPrivateGame(boolean privateGame) {
        this.privateGame = privateGame;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        User.loggedUser = loggedUser;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }


    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
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

    public void mergeHashMap (User opponent) {
        cards = new HashMap<>();
        cards.put(0,opponent.cards.get(5));
        cards.put(1,opponent.cards.get(4));
        cards.put(2,opponent.cards.get(3));
        cards.put(3,opponent.cards.get(2));
        cards.put(4,opponent.cards.get(1));
        cards.put(5,opponent.cards.get(0));
        cards.put(6,opponent.cards.get(7));
        cards.put(7,opponent.cards.get(6));
        cards.put(8,opponent.cards.get(13));
        cards.put(9,opponent.cards.get(12));
        cards.put(10,opponent.cards.get(11));
        cards.put(11,opponent.cards.get(10));
        cards.put(12,opponent.cards.get(9));
        cards.put(13,opponent.cards.get(8));
        cards.put(14,opponent.cards.get(15));
        cards.put(15,opponent.cards.get(14));
        cards.put(16,opponent.cards.get(17));
        cards.put(17,opponent.cards.get(16));
        cards.put(18,opponent.cards.get(19));
        cards.put(19,opponent.cards.get(18));
        cards.put(20,opponent.cards.get(21));
        cards.put(21,opponent.cards.get(20));
    }

    public HashMap<Integer, ArrayList<String>> getCards() {
        return cards;
    }

    public void setCards(HashMap<Integer, ArrayList<String>> cards) {
        this.cards = cards;
    }

    public String getOppName() {
        return oppName;
    }

    public void setOppName(String oppName) {
        this.oppName = oppName;
    }

    public static User getUserByName(String username) {
        for (User user: allUsers) {
            System.out.println("user ha:" +user.getUsername());
            if (username.equals(user.getUsername())){
                return user;
            }
        }
        System.out.println("null shod");
        return null;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
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

    public ArrayList<String > getFriends() {
        return friends;
    }


    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<String> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public ArrayList<String> getGameRequests() {
        return gameRequests;
    }
}


