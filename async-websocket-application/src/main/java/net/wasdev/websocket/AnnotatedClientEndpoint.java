/*******************************************************************************
 * Copyright (c) 2015 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package net.wasdev.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ClientEndpointConfig.Builder;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * Example of an annotated WebSocket client endpoint. This looks very similar to an
 * annotated server endpoint (no surprise), with the same lifecycle methods and 
 * interaction patterns.
 * 
 * Creating a {@link ClientEndpoint} can feel a little strange. You add the 
 * annotation, but still need to actually create the endpoint, as shown in 
 * {@link #connect(String)}.
 *
 */
@ClientEndpoint
public class AnnotatedClientEndpoint {
	
	public static final String NEW_CLIENT = "client:";
	static AtomicInteger clientId = new AtomicInteger(0);

	// try to stick to one client connection for this sample.
	private static AtomicReference<Session> clientConnection = new AtomicReference<>(null);
	
    public static ClientEndpointConfig getDefaultConfig() {
        Builder b = ClientEndpointConfig.Builder.create();
        return b.build();
    }
    
    public static void connect(String clientString) throws IOException {
    	if (clientString.startsWith(NEW_CLIENT) ) {
    		clientString = clientString.substring(NEW_CLIENT.length());

        	WebSocketContainer c = ContainerProvider.getWebSocketContainer();
    		Hello.log(AnnotatedClientEndpoint.class, "Starting the client for " + clientString);

    		URI uriServerEP = URI.create(clientString);
    		try {
    			Session s = c.connectToServer(AnnotatedClientEndpoint.class, uriServerEP);

    			// we're just going to maintain one client at a time, so reading the output
    			// can be somewhat sane.. Set the new session, and close the old one.
    			s = clientConnection.getAndSet(s);
    			if ( s != null )
    				s.close();
    		} catch (DeploymentException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
	int id = clientId.incrementAndGet();


	@OnOpen
	public void onOpen(Session session, EndpointConfig ec) {
		// (lifecycle) Called when the connection is opened
		Hello.log(this, "Client "+ id +" open!");
		
        try {
			session.getBasicRemote().sendText("Client "+ id +" started");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		// (lifecycle) Called when the connection is closed
		Hello.log(this, "Client "+ id +" closed!");
		
		// remove this session if it was the client session we were keeping around
		clientConnection.compareAndSet(session, null);
	}

	@OnMessage
	public void receiveMessage(String message, Session session) throws IOException {
		// Called when a message is received. 
		// Single endpoint per connection by default --> @OnMessage methods are single threaded!
		// Endpoint/per-connection instances can see each other through sessions.

		if ("stop".equals(message)) {
			Hello.log(this, "Client "+ id +" stopped, " + this);
			session.close();
		} else if (message.contains("client ") && message.contains("forwarded")) {
			Hello.log(this, "Client "+ id +" received: " + message);
			message = message.replace(" (forwarded)", ""); // strip the forwarded bit off.
			int pos = message.indexOf("client "); // strip the client bit off
			session.getBasicRemote().sendText("Client "+ id +" echo: " + message.substring(pos + 7));
		} else {
			Hello.log(this, "Client "+ id +" received: " + message);
		}
	}

	@OnError
	public void onError(Throwable t) {
		// (lifecycle) Called if/when an error occurs and the connection is disrupted
		Hello.log(this, "oops: " + t);
	}
}
