package WebConnection;

import Controller.RegisterController;
import com.google.gson.Gson;
import Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isInScoreboard = false;
    private boolean isInMainMenu = false;

    private User currentUser;

    public Connection(Socket socket) throws IOException {
        System.out.println("New connection form: ip=" + socket.getInetAddress().getHostAddress() + " port= " + socket.getPort());
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                ReceivingPacket receivingPacket = new ReceivingPacket(in.readUTF());
                Class<?> controllerClass = Class.forName("Controller." + receivingPacket.getClassName());
                System.out.println(receivingPacket.getMethodName());
                Method controllerMethod = controllerClass.getDeclaredMethod(receivingPacket.getMethodName(), ArrayList.class);
                sendRespond(receivingPacket, controllerMethod);
            } catch (IOException e) {
                System.out.println("Connection \"ip=" + socket.getInetAddress().getHostAddress() + " port=" + socket.getPort() + "\" lost!");
                break;
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                System.out.println("Reflection Problem!");
                throw new RuntimeException(e);
            }
        }
    }

    private void sendRespond(ReceivingPacket receivingPacket, Method controllerMethod) throws IllegalAccessException, InvocationTargetException, IOException {
        SendingPacket sendingPacket;

        SendingPacket result =(SendingPacket)controllerMethod.invoke(null, receivingPacket.getParameters().get(0));
            Field[] fields = SendingPacket.class.getDeclaredFields();
            for (Field field: fields)
                field.setAccessible(true);
            new ObjectOutputStream(out).writeObject(new Gson().toJson(result));
        for (Field field: fields)
            field.setAccessible(false);
        out.writeUTF("");
    }

//    public static void sendNotification(String menuName, String methodName, Connection connection) {
//        String packet = new Gson().toJson(new SendingPacket("command", menuName, methodName));
//        try {
//            new ObjectOutputStream(connection.getOut()).writeObject(packet);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isInScoreboard() {
        return isInScoreboard;
    }

    public void setInScoreboard(boolean inScoreboard) {
        isInScoreboard = inScoreboard;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public boolean isInMainMenu() {
        return isInMainMenu;
    }

    public void setInMainMenu(boolean inMainMenu) {
        isInMainMenu = inMainMenu;
    }
}
