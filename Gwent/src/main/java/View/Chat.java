package View;

import Model.chat.Message;
import com.google.gson.Gson;
import javafx.scene.layout.VBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Chat {
    private final String name;
    private VBox vBox;
    private ArrayList<Message> messages = new ArrayList<>();

    public Chat(String name) {
        this.name = name;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
    public static String getTime() {
        return LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getName() {
        return name;
    }

    public VBox getvBox() {
        return vBox;
    }
    public static void updateChat(ArrayList<Object> objects){
        Gson gson = new Gson();
        ArrayList<Message> messages =gson.fromJson(gson.toJson(objects.get(0)),ArrayList.class);
        for (Object object : messages) {
            System.out.println(object);
            Message message = gson.fromJson(gson.toJson(object), Message.class);
            GameMenu.chat.getMessages().add(message);
        }
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
