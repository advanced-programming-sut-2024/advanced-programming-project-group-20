package Controller;

import Model.User;
import WebConnection.Connection;
import WebConnection.SendingPacket;

import java.util.ArrayList;
import java.util.Collection;

public class MainController {


    public static SendingPacket searchForOpponent(ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        for (User user1 : User.getAllUsers()) {
            if (user1.isSearch()) {
                Connection connection = Connection.getConnectionByUserName(user1.getUsername());
                user1.setSearch(false);
                user1.setOppName(user.getUsername());
                user.setOppName(user1.getUsername());
                Object[] objects1 = new Object[1];
                objects1[0] = user.getUsername();
                connection.sendOrder(new SendingPacket("MainMenu","goToPreGame",objects1));
                objects1[0] = user1.getUsername();
                return new SendingPacket("MainMenu","goToPreGame",objects1);
            }
        }
        user.setSearch(true);
        return null;
    }
    public static SendingPacket cancelSearch(ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        user.setSearch(false);
        return null;
    }

    public static SendingPacket playWithFriend (ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        User friend = User.getUserByName((String) objects.get(1));
        if (user.getFriends().contains(friend.getUsername())) {
            Connection connection = Connection.getConnectionByUserName(friend.getUsername());
            if (connection == null) {
                return new SendingPacket("MainMenu","offline",new Object[1]);
            } else if (friend.getOppName() != null) {
                return new SendingPacket("MainMenu","inGame",new Object[1]);
            }
            Object[] objects1 = new Object[1];
            objects1[0] = user.getUsername();
            connection.sendOrder(new SendingPacket("MainMenu","gameRequest",objects1));
        } else {
            return new SendingPacket("MainMenu","wrongFriend",new Object[1]);
        }
        return new SendingPacket("MainMenu","waitFriend",new Object[1]);
    }

    public static SendingPacket acceptFriend (ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        Connection connection = Connection.getConnectionByUserName(user.getUsername());
        Object[] objects1 = new Object[1];
        objects1[0] = (String) objects.get(1);
        connection.sendOrder(new SendingPacket("MainMenu","goToPreGame",objects1));
        return null;
    }
    public static SendingPacket rejectFriend (ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        Connection connection = Connection.getConnectionByUserName(user.getUsername());
        connection.sendOrder(new SendingPacket("MainMenu","rejectRequest",new Object[1]));
        return null;
    }
}
