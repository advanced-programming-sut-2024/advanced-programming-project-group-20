package Controller;

import Model.*;
import WebConnection.Connection;
import WebConnection.SendingPacket;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class PreGameController {
    public static SendingPacket startGame(User user1, User user2) throws Exception {
        GameController.turnStarter(user1, user2);
        Object[] objects = new Object[3];
        user1.getCards().put(15, user2.getCards().get(14));
        while (!user1.isTurn()) {
            Thread.sleep(1000);
        }
        objects[0] = user1;
        objects[1] = user2.getFactionName();
        objects[2] = user2.getLeaderName();
        if (user1.getCards().get(19) != null) System.out.println(user1.getCards().get(19));
        return new SendingPacket("PreGameMenu", "startGame", objects);
    }

    public static SendingPacket ready(ArrayList<Object> objects) throws Exception {
        Gson gson = new Gson();
        User temp = gson.fromJson(gson.toJson(objects.get(0)), User.class);
        User user = User.getUserByName(temp.getUsername());
        user.setCards(temp.getCards());
        user.setFactionName((String) objects.get(1));
        user.setLeaderName((String) objects.get(2));
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

    public static SendingPacket getUserByName(ArrayList<Object> objects) {
        User user = User.getUserByName((String) objects.get(0));
        User user2 = User.getUserByName((String) objects.get(1));
        Connection.getConnections().getLast().setCurrentUser(user);
        user.setOppName(user2.getUsername());
        user2.setOppName(user.getUsername());
        Object[] objects1 = new Object[2];
        objects1[0] = user;
        objects1[1] = user2;
        return new SendingPacket("RegisterMenu", "setLogin", objects1);
    }
}