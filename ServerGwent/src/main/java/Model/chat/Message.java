package Model.chat;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Message {
    private String content;
    private final String senderName;
    private final String sentTime;
//    private final ArrayList<Emoji> emojis = new ArrayList<>();

    public Message(String content, String senderName, String sentTime) {
        this.sentTime = sentTime;
        this.content = content;
        this.senderName = senderName;
    }


    public String getContent() {
        return content;
    }

    private String getSenderName() {
        return senderName;
    }

    private String getSentTime() {
        return sentTime;
    }


    public VBox toVBox() {
        Label content = initLabel(getContent());
        Label sentTime = initLabel(getSentTime());
        Label senderName = initLabel(getSenderName());
        System.out.println(getContent());
        HBox hBox = new HBox(5,content);
        HBox hBox1 = new HBox(5, senderName, sentTime);
        return new VBox(hBox1,hBox);
    }

    private Label initLabel(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setTextFill(Color.BLACK);
        return label;
    }


}

