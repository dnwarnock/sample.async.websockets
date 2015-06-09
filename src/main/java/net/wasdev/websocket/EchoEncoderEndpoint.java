package net.wasdev.websocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/EchoEncoderEndpoint", 
                decoders = EchoDecoder.class,
                encoders = EchoEncoder.class)
public class EchoEncoderEndpoint {
    
    @OnOpen
    public void onOpen(Session session, EndpointConfig ec) {
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
    }
    
    @OnMessage
    public void receiveMessage(EchoObject o, Session session) throws IOException, EncodeException {
        if (o.stopRequest() ) {
            session.close();
        } else {
            for (Session s : session.getOpenSessions() ) {
                s.getBasicRemote().sendObject(o);
            }
        }
    }
    
    @OnError
    public void onError(Throwable t) {
    }
}
