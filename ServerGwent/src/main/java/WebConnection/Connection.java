package WebConnection;

import com.google.gson.Gson;
import Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Connection extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private User sessionId;
    private static ArrayList<Connection> connections = new ArrayList<>();

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        connections.add(this);
    }

    @Override
    public void run() {
        while (true) {
            try {
                ReceivingPacket receivingPacket = new ReceivingPacket(in.readUTF());
                Class<?> controllerClass = Class.forName("Controller." + receivingPacket.getClassName());
                Method controllerMethod = controllerClass.getDeclaredMethod(receivingPacket.getMethodName(), ArrayList.class);
                sendRespond(receivingPacket, controllerMethod);
            } catch (IOException e) {
                if (sessionId ==null)
                    break;
                sessionId.setLastSeen(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ofPattern("HH:mm")));
                connections.remove(this);
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

        if (this.sessionId != null)
            receivingPacket.getParameters().add(this.sessionId);
        SendingPacket result = (SendingPacket) controllerMethod.invoke(null, receivingPacket.getParameters());
        if (receivingPacket.getMethodName().equals("logout")){
            sessionId.setLastSeen(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ofPattern("HH:mm")));
            this.sessionId = null;
        }
        if (result == null)
            return;
        if (result.getParameters().isEmpty()) return;
        DataOutputStream sendOut = out;
        if (result.getMethodName().equals("loginToMainMenu")) {
            this.sessionId = User.getUserByName((String) result.getParameters().get(0));
            sessionId.setLastSeen("online");
        }
        if (result.getParameters().get(0) instanceof Connection) {
            sendOut = new DataOutputStream(((Connection) result.getParameters().get(0)).socket.getOutputStream());
            result.getParameters().set(0, null);
        }

        Field[] fields = SendingPacket.class.getDeclaredFields();
        for (Field field : fields)
            field.setAccessible(true);
        new ObjectOutputStream(sendOut).writeObject(new Gson().toJson(result));
        for (Field field : fields)
            field.setAccessible(false);
    }

    public void sendOrder(SendingPacket sendingPacket) throws IOException {
        Field[] fields = SendingPacket.class.getDeclaredFields();
        for (Field field : fields)
            field.setAccessible(true);
        new ObjectOutputStream(out).writeObject(new Gson().toJson(sendingPacket));
        for (Field field : fields)
            field.setAccessible(false);
    }



    public DataOutputStream getOut() {
        return out;
    }


    public static Connection getConnectionByUserName(String username) {
        for (Connection connection : connections) {
            if (connection.sessionId != null && connection.sessionId.getUsername().equals(username))
                return connection;
        }
        return null;

    }

}
