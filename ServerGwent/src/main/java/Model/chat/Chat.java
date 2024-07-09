package Model.chat;

import Model.User;
import WebConnection.Connection;

import java.util.ArrayList;

public  class Chat {
    protected transient final ArrayList<Message > messages = new ArrayList<>();

    protected transient final String  ownerName;

    public Chat(String  owner) {
        this.ownerName = owner;

    }



    public String  getOwnerName() {
        return ownerName;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

}
