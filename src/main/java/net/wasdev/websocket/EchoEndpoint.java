package net.wasdev.websocket;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/EchoEndpoint")
public class EchoEndpoint {

    @OnOpen
    public void onOpen(Session session, EndpointConfig ec) {
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
    }

    int count = 0;
    
    @OnMessage
    public void receiveMessage(String message, Session session) throws IOException {
        if ( "stop".equals(message) ) {
            session.close();
        } else {
            int id = count++;
            for (Session s : session.getOpenSessions() ) {
                s.getBasicRemote().sendText("Echo " + id + ":  " + message);
            }
        }
    }
    
    @OnError
    public void onError(Throwable t) {
    }
}
