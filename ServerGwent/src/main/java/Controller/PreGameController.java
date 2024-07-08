package Controller;

import Model.*;
import WebConnection.Connection;
import WebConnection.SendingPacket;
import com.google.gson.Gson;
import java.util.ArrayList;


public class PreGameController {
    public static SendingPacket startGame(User user1, User user2) throws Exception {
        GameController.turnStarter(user1, user2);
        Object[] objects = new Object[3];
        user1.getCards().put(15, user2.getCards().get(14));
        Thread.sleep(1500);
        while (!user1.isTurn()) {
            Thread.sleep(1000);
        }
        objects[0] = user1;
        objects[1] = user2.getFactionName();
        objects[2] = user2.getLeaderName();
        user1.getActiveGame().setOppLeaderName(user2.getLeaderName());
        user1.getActiveGame().setOppFactionName(user2.getFactionName());
        Thread.sleep(1000);
        user1.setReady(false);
        return new SendingPacket("PreGameMenu", "startGame", objects);
    }

    public static SendingPacket ready(ArrayList<Object> objects) throws Exception {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        GameHistory gameHistory = gson.fromJson(gson.toJson(objects.get(1)), GameHistory.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        user.setFactionName(gameHistory.getFactionName());
        user.setLeaderName(gameHistory.getLeaderName());
        user.setActiveGame(gameHistory);
        User user2 = User.getUserByName(user.getOppName());
        user.setReady(true);
        if (user2.isReady()) {
            return startGame(user, user2);
        } else {
            while (!user2.isReady()) {
                Thread.sleep(1000);
            }
        }
        return startGame(user, user2);

    }



}