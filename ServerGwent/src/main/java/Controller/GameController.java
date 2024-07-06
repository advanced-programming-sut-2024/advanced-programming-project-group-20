package Controller;

import Model.*;
import WebConnection.Connection;
import WebConnection.SendingPacket;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.*;


public class GameController {
    public synchronized static void turnStarter(User user1, User user2) {
        user1.setPassed(false);
        user2.setPassed(false);
        if (user1.getFactionName().equals("ScoiaTeal") &&
                !user2.getFactionName().equals("ScoiaTeal")) {
                user1.setTurn(true);
                user2.setTurn(false);
        } else if (!user1.getFactionName().equals("ScoiaTeal") &&
                user2.getFactionName().equals("ScoiaTeal")) {
            user1.setTurn(false);
            user2.setTurn(true);
        } else if ((user1.isTurn() && user2.isTurn()) || (!user2.isTurn() && !user1.isTurn())) {
            Random random = new Random();
            boolean firstUser = random.nextBoolean();
            if (firstUser) {
                user1.setTurn(true);
                user2.setTurn(false);
            } else {
                user1.setTurn(false);
                user2.setTurn(true);
            }
        }

    }

    public static SendingPacket nextTurn(ArrayList<Object> objects) throws InterruptedException, IOException {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        User user2 = User.getUserByName(user.getOppName());
        user2.mergeHashMap(user);
        user.addMove(user.getCards());
        user2.addMove(user2.getCards());
        user.setTurn(false);
        user2.setTurn(true);
        if (user2.isReady()){
            System.out.println(user2.isTurn());
            return null;
        }
        Connection connection = Connection.getConnectionByUserName(user2.getUsername());
        Object[] objects1 = new Object[2];
        objects1[0] = user2;
        objects1[1] = user2.getActiveGame();
        connection.sendOrder(new SendingPacket("GameMenu","getTurn", objects1));
        return null;
    }

    public static SendingPacket pass (ArrayList<Object> objects) throws Exception {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        User user2 = User.getUserByName(user.getOppName());
        user2.mergeHashMap(user);
        user.addMove(user.getCards());
        user2.addMove(user2.getCards());
        user.setTurn(false);
        user2.setTurn(true);
        user.setPassed(true);
        Connection connection = Connection.getConnectionByUserName(user2.getUsername());
        Object[] objects1 = new Object[2];
        objects1[0] = user2;
        objects1[1] = user2.getActiveGame();
        connection.sendOrder(new SendingPacket("GameMenu","getTurn", objects1));
        return null;
    }

    public static SendingPacket update (ArrayList<Object> objects) throws IOException {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        User user2 = User.getUserByName(user.getOppName());
        Connection connection = Connection.getConnectionByUserName(user2.getUsername());
        user2.mergeHashMap(user);
        user.addMove(user.getCards());
        user2.addMove(user2.getCards());
        Object[] objects1 = new Object[1];
        objects1[0] = user2;
        connection.sendOrder(new SendingPacket("GameMenu","waitForNextRound", objects1));
        return null;
    }

    public static SendingPacket endGame (ArrayList<Object> objects) throws IOException {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        GameHistory gameHistory = gson.fromJson(gson.toJson(objects.get(1)), GameHistory.class);
        User user = User.getUserByName(temp.getUsername());
        user.addMove(user.getCards());
        user.setActiveGame(gameHistory);
        user.getActiveGame().setMoves(user.getMoves());
        if (user.getOppName() != null) {
            if (user.getActiveGame().getWinner() == null) {
                System.out.println("Draw");
            } else if (user.getActiveGame().getWinner().equals(user.getUsername())) {
                TournamentController.setResult(user.getUsername(),user.getOppName());
            } else {
                TournamentController.setResult(user.getOppName(),user.getUsername());
            }
            User user2 = User.getUserByName(user.getOppName());
            user2.mergeActiveGame(user);
            user2.mergeHashMap(user);
            user2.setOppName(null);
            user2.setPassed(false);
            Connection connection = Connection.getConnectionByUserName(user2.getUsername());
            ArrayList<Object> objects1 = new ArrayList<>();
            objects1.add(user2);
            objects1.add(user2.getActiveGame());
            connection.sendOrder(endGame(objects1));
        }
        user.setNumberOfGames(user.getNumberOfGames() + 1);
        if (user.getActiveGame().getWinner() == null) {
            user.setNumberOfDraws(user.getNumberOfDraws() + 1);
        } else if (user.getActiveGame().getWinner().equals(user.getUsername())) {
            user.setNumberOfWins(user.getNumberOfWins() + 1);
        } else {
            user.setNumberOfLose(user.getNumberOfLose() + 1);
        }
        user.getGameHistories().add(user.getActiveGame());
        user.setOppName(null);
        user.setPassed(false);
        Object[] objects1 = new Object[2];
        objects1[0] = user;
        objects1[1] = user.getActiveGame();
        ArrayList<Object> objects2 = new ArrayList<>();
        for (User user1 : User.getAllUsers()) {
            objects2.add(user1);
        }
        ApplicationController.saveTheUsersInGson(objects2);
        return new SendingPacket("GameMenu", "endShower",objects1);
    }

    public static SendingPacket goToNextRound(ArrayList<Object> objects) throws InterruptedException, IOException {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        GameHistory gameHistory = gson.fromJson(gson.toJson(objects.get(1)), GameHistory.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        User user2 = User.getUserByName(user.getOppName());
        user.setActiveGame(gameHistory);
        user2.mergeActiveGame(user);
        user2.mergeHashMap(user);
        user.addMove(user.getCards());
        user2.addMove(user2.getCards());
        user.setTurn(false);
        user2.setTurn(false);
        turnStarter(user, user2);
        user2.setPassed(false);
        Object[] objects1 = new Object[2];
        objects1[0] = user2;
        objects1[1] = user2.getActiveGame();
        Connection connection = Connection.getConnectionByUserName(user2.getUsername());
        if (user.isTurn()) {
            connection.sendOrder(new SendingPacket("GameMenu","waitForNextRound", objects1));
            objects1[0] = user;
            objects1[1] = user.getActiveGame();
            return new SendingPacket("GameMenu","getTurn", objects1);
        } else {
            connection.sendOrder(new SendingPacket("GameMenu","getTurn", objects1));
        }
        return null;
    }

    public static SendingPacket getUpdateGame(ArrayList<Object> objects) {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user = User.getUserByName(temp.getUsername());
        if (user.getOppName() == null) {
            return new SendingPacket("Spectator", "endGame", objects);
        }
        Object[] objects1 = new Object[1];
        objects1[0] = user;
        return new SendingPacket("Spectator" , "updateGame", objects1);
    }
}


