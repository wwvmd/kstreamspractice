package serializers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JsonSerializer<T> implements Serializer<T> {

    private Gson gson;

    public JsonSerializer() {
        GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(Id.class, new IdTypeAdapter());
        gson = builder.create();
    }


    public void configure(Map map, boolean b) {

    }

    public byte[] serialize(String s, T t) {
        byte[] bytes = null;
        try {
            bytes = gson.toJson(t).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public void close() {

    }
}
