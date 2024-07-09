package webConnection;

import org.json.JSONPropertyIgnore;

import java.util.ArrayList;
import java.util.Arrays;

public class Packet {

    public OperationType operationType;
    public String className;
    public String methodName;
    public ArrayList<Object> parameters = new ArrayList<>();

    public Packet(OperationType operationType, String className, String methodName, Object[] parameters) {
        this.operationType = operationType;
        this.className = className;
        this.methodName = methodName;
        this.parameters.addAll(Arrays.asList(parameters));
    }

    public Packet( String className, String methodName, Object[] parameters) {
        this.className = className;
        this.methodName = methodName;
        this.parameters.addAll(Arrays.asList(parameters));
    }

}
