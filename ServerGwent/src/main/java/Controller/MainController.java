package Controller;

import Model.User;
import WebConnection.Connection;
import WebConnection.SendingPacket;

import java.util.ArrayList;
import java.util.Collection;

public class MainController {


    public static SendingPacket searchForOpponent(ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        Boolean bool = (Boolean) objects.get(1);
        user.setPrivateGame(bool);
        user.setSearch(true);
        return null;
    }

    public static SendingPacket cancelSearch(ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        user.setSearch(false);
        return null;
    }

    public static SendingPacket getRandomGames(ArrayList<Object> objects) throws Exception {
        ArrayList<String> randomGames = new ArrayList<>();
        for (User user : User.getAllUsers()) {
            if (user.isSearch()) {
                String gameMode;
                if (user.isPrivateGame()) gameMode = " private";
                else gameMode = " public";
                randomGames.add(user.getUsername() + gameMode);
            }
        }
        Object[] object = new Object[1];
        object[0] = randomGames;
        return new SendingPacket("MainMenu", "showRandomGames", object);
    }

    public static SendingPacket startRandomGame(ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        User opponent = User.getUserByName((String) objects.get(1));
        Boolean bool = (Boolean) objects.get(2);
        user.setPrivateGame(bool);
        opponent.setSearch(false);
        user.setOppName(opponent.getUsername());
        opponent.setOppName(user.getUsername());
        Connection connection = Connection.getConnectionByUserName(opponent.getUsername());
        Object[] object = new Object[1];
        object[0] = user.getUsername();
        connection.sendOrder(new SendingPacket("MainMenu", "goToPreGame", object));
        object[0] = opponent.getUsername();
        return new SendingPacket("MainMenu", "goToPreGame", object);
    }

    public static SendingPacket playWithFriend(ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        User friend = User.getUserByName((String) objects.get(1));
        Boolean bool = (Boolean) objects.get(2);
        user.setPrivateGame(bool);
        if (friend != null && user.getFriends().contains(friend.getUsername())) {
            Connection connection = Connection.getConnectionByUserName(friend.getUsername());
            if (connection == null) {
                return new SendingPacket("MainMenu", "offline", new Object[1]);
            } else if (friend.getOppName() != null) {
                return new SendingPacket("MainMenu", "inGame", new Object[1]);
            }
            Object[] objects1 = new Object[1];
            objects1[0] = user.getUsername();
            connection.sendOrder(new SendingPacket("MainMenu", "gameRequest", objects1));
        } else {
            return new SendingPacket("MainMenu", "wrongFriend", new Object[1]);
        }
        return new SendingPacket("MainMenu", "waitFriend", new Object[1]);
    }

    public static SendingPacket acceptFriend(ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        User self = User.getUserByName((String) objects.get(1));
        self.setPrivateGame(user.isPrivateGame());
        Connection connection = Connection.getConnectionByUserName(user.getUsername());
        user.getGameRequests().add((String) objects.get(1) + " Accept");
        Object[] objects1 = new Object[1];
        objects1[0] = self.getUsername();
        connection.sendOrder(new SendingPacket("MainMenu", "goToPreGame", objects1));
        objects1[0] = user.getUsername();
        return new SendingPacket("MainMenu", "goToPreGame", objects1);
    }

    public static SendingPacket rejectFriend(ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        user.getGameRequests().add((String) objects.get(1) + " Reject");
        Connection connection = Connection.getConnectionByUserName(user.getUsername());
        connection.sendOrder(new SendingPacket("MainMenu", "rejectRequest", new Object[1]));
        return null;
    }

    public static SendingPacket getGameRequests(ArrayList<Object> objects) throws Exception {
        User user = User.getUserByName((String) objects.get(0));
        ArrayList<String> gameRequests = user.getGameRequests();
        if (gameRequests == null) gameRequests = new ArrayList<>();
        Object[] objects1 = new Object[1];
        objects1[0] = gameRequests;
        return new SendingPacket("MainMenu", "showGameRequests", objects1);
    }


}
