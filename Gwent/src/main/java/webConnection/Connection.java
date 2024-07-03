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
        username = "ali";
    }

    public Socket getSocket() {
        return socket;
    }

    public DataOutput getOut() {
        return out;
    }

//    public Message checkAction(String className, String methodName, Object... parameters){
//        Packet packet = new Packet(OperationType.CHECK_ACTION, className, methodName, parameters);
//        sendData(packet);
//        return getRespond();
//    }
//    public Object getData(String className, String methodName, Object... parameters){
//        Packet packet = new Packet(OperationType.GET_DATA, className, methodName, parameters);
//        sendData(packet);
//        return receiveData().get("value");
//    }
//
//    public ArrayList getArrayData(String className, String methodName, Object... parameters){
//        Packet packet = new Packet(OperationType.GET_ARRAY_DATA, className, methodName, parameters);
//        sendData(packet);
//        return new Gson().fromJson(receiveArrayData().toString(),new TypeToken<ArrayList>(){}.getType());
//    }
//
//    public JSONObject getJSONData(String className, String methodName, Object... parameters){
//        Packet packet = new Packet(OperationType.GET_DATA, className, methodName, parameters);
//        sendData(packet);
//        return (JSONObject) receiveData().get("value");
//    }
//
//    public JSONArray getJSONArrayData(String className, String methodName, Object... parameters){
//        Packet packet = new Packet(OperationType.GET_ARRAY_DATA, className, methodName, parameters);
//        sendData(packet);
//        return receiveArrayData();
//    }

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

    private JSONObject receiveData(){
        if (receiver.isReceivedPacketAccessible()) {
            receiver.setReceivedPacketAccessible(false);
            return new JSONObject(receiver.getReceivedPacket());
        }
        else {
            try {
                sleep(10);
                return receiveData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    //    public String receiveJsonData(String className, String methodName, Object... parameters){
//        Packet packet = new Packet(OperationType.GET_DATA, className, methodName, parameters);
//        sendData(packet);
//        String input;
//        while (true) {
//            if (receiver.isReceivedPacketAccessible()) {
//                receiver.setReceivedPacketAccessible(false);
//                input = receiver.getReceivedPacket();
//                break;
//            } else {
//                try {
//                    sleep(10);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//        Pattern pattern = Pattern.compile("\\{\"value\":(?<value>.+),\"type\".+");
//        Matcher matcher = pattern.matcher(input);
//        matcher.matches();
//        return matcher.group("value");
//    }

//    private Message getRespond(){
//        return Message.valueOf((String) receiveData().get("value"));
//    }
//    private JSONArray receiveArrayData(){
//        return (JSONArray) receiveData().get("value");
//    }
}
