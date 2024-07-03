package webConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class serverCommander extends Thread {
    private JSONObject packet;

    public serverCommander(JSONObject packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        try {
            Class<?> menu = Class.forName("Controller." + packet.get("className"));
            Method method = menu.getDeclaredMethod((String) packet.get("methodName"),ArrayList.class);
          ArrayList<Object> parameters = (ArrayList<Object>) ((JSONArray)packet.get("parameters")).toList();
            System.out.println(parameters);
            method.invoke(null,parameters);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
