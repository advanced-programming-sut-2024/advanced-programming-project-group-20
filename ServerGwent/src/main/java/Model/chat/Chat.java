package Model.chat;

import Model.User;
import WebConnection.Connection;

import java.util.ArrayList;

public  class Chat {
    protected transient final ArrayList<Message > messages = new ArrayList<>();

    protected transient final String  ownerName;
//    protected transient final ArrayList<Connection> connections = new ArrayList<>();

    public Chat(String  owner) {
        this.ownerName = owner;

    }



    public String  getOwnerName() {
        return ownerName;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }



//    public void addConnection(Connection connection) {
//        connections.add(connection);
//    }

//    public void removeConnection(Connection connection) {
//        connections.remove(connection);
//    }




    public void removeMessage(Message message) {
        messages.remove(message);
    }
}
