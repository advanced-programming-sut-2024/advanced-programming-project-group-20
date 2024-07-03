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
import java.util.Date;

public class Connection extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isInScoreboard = false;
    private boolean isInMainMenu = false;

    private User currentUser;
    public static ArrayList<Connection> connections = new ArrayList<>();

    public Connection(Socket socket) throws IOException {
        System.out.println("New connection form: ip=" + socket.getInetAddress().getHostAddress() + " port= " + socket.getPort());
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        connections.add(this);
    }

    @Override
    public void run() {
        //todo set real User
//        if (User.getUserByName("ali")==null){
//        currentUser = new User("ali",null,null,null,null,null);
//        User.getAllUsers().add(currentUser);}
//        else{
//            if (User.getUserByName("reza")==null)
//                currentUser = new User("reza",null,null,null,null,null);
//        }
        while (true) {
            try {
                ReceivingPacket receivingPacket = new ReceivingPacket(in.readUTF());
                Class<?> controllerClass = Class.forName("Controller." + receivingPacket.getClassName());
                System.out.println("methodName"+receivingPacket.getMethodName());
                Method controllerMethod = controllerClass.getDeclaredMethod(receivingPacket.getMethodName(), ArrayList.class);
                sendRespond(receivingPacket, controllerMethod);
            } catch (IOException e) {
                System.out.println("Connection \"ip=" + socket.getInetAddress().getHostAddress() + " port=" + socket.getPort() + "\" lost!");
                break;
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                System.out.println("Reflection Problem!");
              e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void sendRespond(ReceivingPacket receivingPacket, Method controllerMethod) throws IllegalAccessException, InvocationTargetException, IOException {
        SendingPacket sendingPacket;
receivingPacket.getParameters().add(this.currentUser);
        SendingPacket result =(SendingPacket)controllerMethod.invoke(null, receivingPacket.getParameters());
        if (result == null)
            return;
        if (result.getParameters().isEmpty()) return;
        DataOutputStream sendOut = out;
        if (result.getParameters().get(0) instanceof Connection) {
             sendOut = new DataOutputStream(((Connection) result.getParameters().get(0)).socket.getOutputStream());
            result.getParameters().set(0,null);
        }

            Field[] fields = SendingPacket.class.getDeclaredFields();
            for (Field field: fields)
                field.setAccessible(true);
            new ObjectOutputStream(sendOut).writeObject(new Gson().toJson(result));
        for (Field field: fields)
            field.setAccessible(false);
    }

    private void sendRespondToAnother(ReceivingPacket receivingPacket, Method controllerMethod) throws IllegalAccessException, InvocationTargetException, IOException {
        SendingPacket sendingPacket;

        SendingPacket result =(SendingPacket)controllerMethod.invoke(null, receivingPacket.getParameters());
        Field[] fields = SendingPacket.class.getDeclaredFields();
        for (Field field: fields)
            field.setAccessible(true);
        new ObjectOutputStream(out).writeObject(new Gson().toJson(result));
        for (Field field: fields)
            field.setAccessible(false);
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
    public static Connection getConnectionByUserName(String username){
        for (Connection connection : connections ){
            if (connection.currentUser.getUsername().equals(username))
                return connection;
        }
        return null;

    }
}
