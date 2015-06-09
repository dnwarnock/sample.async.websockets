package net.wasdev.websocket;

import java.io.StringReader;
import java.util.concurrent.atomic.AtomicInteger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

public class EchoObject {
    static final AtomicInteger count = new AtomicInteger();
    
    final JsonObject obj;
    
    public EchoObject(String msg)  {
        JsonReader r = Json.createReader(new StringReader(msg));
        JsonObject in = r.readObject();
        
        JsonObjectBuilder b = Json.createObjectBuilder();
        b.add("count", count.getAndIncrement());
        b.add("content", in.getString("content", "none provided"));
        obj = b.build();
    }
    
    public String toString() {
        return obj.toString();
    }

    public EchoObject(Exception e) {
        JsonObjectBuilder b = Json.createObjectBuilder();
        b.add("content", e.toString());
        b.add("count", -1);
        obj = b.build();
    }
    
    public boolean stopRequest() {
        return "stop".equals(obj.getString("content"));
    }
}
