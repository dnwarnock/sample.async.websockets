package net.wasdev.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class EchoEncoder implements Encoder.Text<EchoObject> {

    @Override
    public String encode(EchoObject o) throws EncodeException {
        System.out.println("Encoding " + o);
        return o.toString();
    }

    @Override
    public void init(EndpointConfig ec) {}

    @Override
    public void destroy() {}
}
