package net.wasdev.websocket;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class ExtendedEndpoint extends Endpoint {

    @Override
    public void onOpen(Session session, EndpointConfig ec) {
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
            }
        });
    }
    
    @Override
    public void onClose(Session session, CloseReason reason) {
    }
    
    @Override
    public void onError(Session session, Throwable t) {
    }
}
