package webConnection;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {
    private String receivedPacket;
    private boolean isReceivedPacketAccessible = false;
    private final Socket socket;
    private DataInputStream in;

    public Receiver(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                receivedPacket = (String) new ObjectInputStream(in).readObject();
                if (receivedPacket.equals("check"))
                    continue;
                System.out.println(receivedPacket);
                isReceivedPacketAccessible = false;

                JSONObject packet = new JSONObject(receivedPacket);
                new serverCommander(packet).start();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
