package WebConnection;

import org.json.JSONPropertyIgnore;

import java.util.ArrayList;
import java.util.Arrays;

public class SendingPacket {

    public OperationType operationType;
    public String className;
    public String methodName;
    public ArrayList<Object> parameters = new ArrayList<>();


    public SendingPacket( String className, String methodName, Object ... parameters) {
        this.className = className;
        this.methodName = methodName;
        this.parameters.addAll(Arrays.asList(parameters));
    }


    public String getMethodName() {
        return methodName;
    }

    public ArrayList<Object> getParameters() {
        return parameters;
    }

}
