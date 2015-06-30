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

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Example of a simple POJO defining a WebSocket server endpoint using
 * annotations. When a message is received, the payload is decoded using the {@link EchoDecoder}.
 * The resulting {@link EchoObject} is then braodcast to all connected sessions (including back
 * to the sender), using the {@link EchoEncoder} to serialize the outbound payload.
 * 
 * <p>
 * The value is the URI relative to your app’s context root,  e.g. the context
 * root for this application is <code>websocket</code>, which makes the
 * WebSocket URL used to reach this endpoint
 * <code>ws://localhost/websocket/EchoEncoderEndpoint</code>.
 * </p>
 * <p>
 * The methods below are annotated for lifecycle (onOpen, onClose, onError), or
 * message (onMessage) events.
 * </p>
 * <p>
 * By default, a new instance of server endpoint is instantiated for each client
 * connection (section 3.1.7 of JSR 356 specification).
 * </p>
 */
@ServerEndpoint(value = "/EchoEncoderEndpoint", decoders = EchoDecoder.class, encoders = EchoEncoder.class)
public class EchoEncoderEndpoint {

	@OnOpen
	public void onOpen(Session session, EndpointConfig ec) {
		// (lifecycle) Called when the connection is opened
		Hello.log(this, "I'm open!");
	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		Hello.log(this, "I'm closed!");
	}

	/**
	 * Called when a message is received. The WebSocket container will take 
	 * data from the socket, and will transform it into the parameter EchoObject
	 * using the {@link EchoDecoder}.
	 * @param o Parameters converted into an EchoObject via the <code>EchoDecoder</code>
	 * @param session The session associated with this message
	 * @throws IOException
	 * @throws EncodeException
	 */
	@OnMessage
	public void receiveMessage(EchoObject o, Session session)
	        throws IOException, EncodeException {
		// Called when a message is received. 
		// Single endpoint per connection by default --> @OnMessage methods are single threaded!
		// Endpoint/per-connection instances can see each other through sessions.

		if (o.stopRequest()) {
			session.close();
		} else {
			// Simple broadcast
			for (Session s : session.getOpenSessions()) {
				s.getBasicRemote().sendObject(o);
			}
		}
	}

	@OnError
	public void onError(Throwable t) {
		// (lifecycle) Called if/when an error occurs and the connection is disrupted
		Hello.log(this, "oops: " + t);
	}
}
