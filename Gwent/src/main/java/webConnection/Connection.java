package webConnection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection extends Thread {
    private Socket socket;
    private Receiver receiver;
    private DataOutput out;
    //todo set real name
    private String  username;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(socket.getOutputStream());
        this.receiver = new Receiver(socket);
        receiver.start();
    }


    public void doInServer (String className, String methodName, Object... parameters){
        Packet packet = new Packet(OperationType.VOID, className, methodName, parameters);
        sendData(packet);

    }

    private void sendData (Packet packet){
        Field[] fields =Packet.class.getDeclaredFields();
        for (Field field1:fields)
            field1.setAccessible(true);
        String data = new Gson().toJson(packet);
        try {
            out.writeUTF(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Field field1:fields)
            field1.setAccessible(false);
    }


    public String getUsername() {
        return username;
    }

}
