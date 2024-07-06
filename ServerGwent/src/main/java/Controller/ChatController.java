package Controller;

import Model.User;
import Model.chat.Chat;
import Model.chat.Message;
import WebConnection.Connection;
import WebConnection.SendingPacket;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ChatController {
    private static final int MAX_MESSAGE_NUMBER = 100;
    public static HashMap<String, Chat> chats = new HashMap<>();

    public static SendingPacket getMessages(ArrayList<Object> objects) {
        Gson gson = new Gson();
        System.out.println(objects.get(0).toString());
        Message message = gson.fromJson(gson.toJson(objects.get(0)), Message.class);
        String username = (String) objects.get(1);
        ArrayList<Message> messages;
String opponentName = User.getUserByName(username).getOppName();
        if (chats.containsKey(username)) {
        messages= chats.get(username).getMessages();
            messages.add(message);
            if (messages.size() > MAX_MESSAGE_NUMBER)
                messages.remove(0);
        } else if (chats.containsKey(opponentName)) {
            messages= chats.get(opponentName).getMessages();
            messages.add(message);
            if (messages.size() > MAX_MESSAGE_NUMBER)
                messages.remove(0);
        } else {
            chats.put(username, new Chat(username));
            messages = new ArrayList<>();
            messages.add(message);
        }

        ArrayList<Object> objects1 = new ArrayList<>();
        objects1.add(messages);
        try {
            Connection.getConnectionByUserName(opponentName).sendOrder(new SendingPacket("Chat","updateChat",messages));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new SendingPacket("Chat", "updateChat", objects1.toArray());


    }
}
