package View;

import Model.chat.Message;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Alert;
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
        Platform.runLater(()->{
            Gson gson = new Gson();
            ArrayList<Message> messages =gson.fromJson(gson.toJson(objects.get(0)),ArrayList.class);
            if (GameMenu.chat != null) {
                GameMenu.chat.getMessages().clear();
                for (Object object : messages) {
                    Message message = gson.fromJson(gson.toJson(object), Message.class);
                    GameMenu.chat.getMessages().add(message);
                }
                GameMenu.chat.getvBox().getChildren().clear();
                for (Message message1 : GameMenu.chat.getMessages()) {
                    GameMenu.chat.getvBox().getChildren().add(message1.toVBox());
                }
            } else if (Spectator.chat != null) {
                Spectator.chat.getMessages().clear();
                for (Object object : messages) {
                    Message message = gson.fromJson(gson.toJson(object), Message.class);
                    Spectator.chat.getMessages().add(message);
                }
                Spectator.chat.getvBox().getChildren().clear();
                for (Message message1 : Spectator.chat.getMessages()) {
                    Spectator.chat.getvBox().getChildren().add(message1.toVBox());
                }
            }
            });
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
