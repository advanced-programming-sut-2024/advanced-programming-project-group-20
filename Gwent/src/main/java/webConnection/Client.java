package webConnection;

import Model.Card;
import Model.CardNames;
import View.RegisterMenu;

import java.net.Socket;

public class Client {
    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = new Connection(new Socket("localhost", 15551));
            CardNames.fillArrayLists();
            RegisterMenu.main(args);
                    } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return Client.connection;
    }

//    public static String getUsername() {
//        return username;
//    }
//
//    public static void setUsername(String username) {
//        Client.username = username;
//    }
}
