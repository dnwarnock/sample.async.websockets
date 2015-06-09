package net.wasdev.websocket;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/SimpleAnnotated")
public class SimpleEndpoint {

    @OnOpen
    public void onOpen(Session session, EndpointConfig ec) {
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
    }

    @OnMessage
    public void receiveMessage(String message, Session session) {
        
    }
    
    @OnError
    public void onError(Throwable t) {
    }
}
