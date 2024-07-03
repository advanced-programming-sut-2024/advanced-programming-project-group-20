package Model;

import java.util.ArrayList;

public class User {
    private ArrayList<GameHistory> gameHistories = new ArrayList<>();
    transient private GameHistory activeGame;
    private String username;
    private String password;
    private String nickName;
    private String secureQuestion;
    private String secureAnswer;
    transient private User opponentUser;

     private ArrayList<String > friends = new ArrayList<>();
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

    private boolean isPassed = false;
    private boolean isFullHealth = true;
    private boolean firstTurn = true;
    private int rank;
    private ArrayList<String> friendRequests =new ArrayList<>();

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

    public static User getUserByName(String username) {
        for (User user: allUsers) {
            if (username.equals(user.getUsername())){
                return user;
            }
        }
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

    public User getOpponentUser() {
        return opponentUser;
    }

    public void setOpponentUser(User opponentUser) {
        this.opponentUser = opponentUser;
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

    public static User getTurnUser() {
        return turnUser;
    }

    public static void setTurnUser(User turnUser) {
        User.turnUser = turnUser;
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

    public ArrayList<String > getFriends() {
        return friends;
    }


    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<String> friendRequests) {
        this.friendRequests = friendRequests;
    }
}

