package Controller;

import Model.*;
import WebConnection.Connection;
import WebConnection.OperationType;
import WebConnection.SendingPacket;
import com.google.gson.Gson;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

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

    public static SendingPacket nextTurn(ArrayList<Object> objects) throws InterruptedException {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        User user2 = User.getUserByName(user.getOppName());
        user2.mergeHashMap(user);
        user.setTurn(false);
        user2.setTurn(true);
        Object[] objects1 = new Object[1];
        while (!user.isTurn()) {
            Thread.sleep(1000);
        }
        objects1[0] = user;
        return new SendingPacket("GameMenu","getTurn", objects1);
    }

    public static SendingPacket pass (ArrayList<Object> objects) throws Exception {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        User user2 = User.getUserByName(user.getOppName());
        user2.mergeHashMap(user);
        user.setTurn(false);
        user2.setTurn(true);
        user.setPassed(true);
        Connection connection = Connection.getConnectionByUserName(user.getUsername());
        Object[] objects1 = new Object[1];
        objects1[0] = user;
        while (user.isPassed()) {
            user.mergeHashMap(user2);
            Thread.sleep(1000);
            connection.sendOrder(new SendingPacket("GameMenu","waitForNextRound",objects1));
        }
        ArrayList<Object> objects2 = new ArrayList<>();
        objects2.add(user);
        Thread.sleep(1000);
        user.mergeHashMap(user2);
        return nextRound(objects2);
    }

    public static SendingPacket nextRound(ArrayList<Object> objects) throws Exception {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user = User.getUserByName(temp.getUsername());
        objects.add(user.getActiveGame());
        if (user.getOppName() == null) return endGame(objects);
        else return startNewRound(user);
    }

    private static SendingPacket startNewRound(User user) throws InterruptedException {
        User user2 = User.getUserByName(user.getOppName());
        while (!user.isTurn()) {
            Thread.sleep(1000);
        }
        Object[] objects1 = new Object[2];
        objects1[0] = user;
        objects1[1] = user.getActiveGame();
        user.mergeHashMap(user2);
        return new SendingPacket("GameMenu","startRound", objects1);
    }

    public static SendingPacket update (ArrayList<Object> objects) {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        System.out.println(user.getCards().get(2));
        return null;
    }


    public static SendingPacket endGame (ArrayList<Object> objects) {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        GameHistory gameHistory = gson.fromJson(gson.toJson(objects.get(1)), GameHistory.class);
        User user = User.getUserByName(temp.getUsername());
        user.setActiveGame(gameHistory);
        if (user.getOppName() != null) {
            User user2 = User.getUserByName(user.getOppName());
            user2.mergeActiveGame(user);
            user2.setOppName(null);
            user2.setPassed(false);
        }
        user.getGameHistories().add(user.getActiveGame());
        user.setOppName(null);
        user.setPassed(false);
        Object[] objects1 = new Object[2];
        objects1[0] = user;
        objects1[1] = user.getActiveGame();
        return new SendingPacket("GameMenu", "endShower",objects1);
    }

    public static SendingPacket goToNextRound(ArrayList<Object> objects) throws InterruptedException {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        GameHistory gameHistory = gson.fromJson(gson.toJson(objects.get(1)), GameHistory.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        User user2 = User.getUserByName(user.getOppName());
        user.setActiveGame(gameHistory);
        user2.mergeActiveGame(user);
        user.setTurn(false);
        user2.setTurn(false);
        turnStarter(user, user2);
        user2.setPassed(false);
        Object[] objects1 = new Object[1];
        objects1[0] = user;
        if (user.isTurn()) {
            return new SendingPacket("GameMenu","getTurn", objects1);
        } else {
            while (!user.isTurn()) {
                Thread.sleep(1000);
            }
            user.mergeHashMap(user2);
            return new SendingPacket("GameMenu","getTurn", objects1);
        }

    }
}


