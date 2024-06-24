package View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

public class PrivateFieldAdapter<User> extends TypeAdapter<User> {
        private final Gson gson = new Gson();
        @Override
        public void write(JsonWriter out, User value) throws IOException {
            out.beginObject();
            Field[] fields = value.getClass().getDeclaredFields();
            for (Field field : fields) {
//                System.out.println(field.getName());
                field.setAccessible(true);
                Object fieldValue;
                try {
                    fieldValue = field.get(value);
                    System.out.println("fffff");
                } catch (IllegalAccessException e) {
                    fieldValue = null;
                }
                out.name(field.getName());
                gson.toJson(fieldValue, fieldValue.getClass(), out);
            }
            out.endObject();
        }

        @Override
        public User read(JsonReader in) throws IOException {
            JsonObject jsonObject = gson.fromJson(in, JsonObject.class);
            try {
                User instance = (User) jsonObject.getClass().newInstance();
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    Field field = instance.getClass().getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    Object fieldValue = gson.fromJson(entry.getValue(), field.getType());
                    field.set(instance, fieldValue);
                }
                return instance;
            } catch (IllegalAccessException | InstantiationException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }
}
