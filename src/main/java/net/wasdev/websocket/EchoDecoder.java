package net.wasdev.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class EchoDecoder implements Decoder.Text<EchoObject> {

    @Override
    public EchoObject decode(String msg) throws DecodeException {
        EchoObject o;
        try {
            o = new EchoObject(msg);
        } catch(Exception e) {
            o = new EchoObject(e);
        }
        System.out.println("Decoded " + msg + " -> " + o);
        return o;
    }

    @Override
    public boolean willDecode(String msg) {
        return true;
    }

    @Override
    public void init(EndpointConfig ec) {}

    @Override
    public void destroy() {}
}
