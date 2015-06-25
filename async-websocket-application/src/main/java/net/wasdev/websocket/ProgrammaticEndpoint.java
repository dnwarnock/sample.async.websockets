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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Example of a WebSocket server endpoint programatically. It needs to be
 * configured using {@link ServerApplicationConfig}.See
 * {@link ExtendedEndpointApplicationConfig}.
 * <p>
 * The extension of the programmatic endpoint must still have a zero-argument
 * constructor, just as the annotated endpoint does. The WebSocket runtime will
 * (by default) create an instance of the programmatic endpoint for each client
 * connection (3.1.7 of JSR 356).
 * </p>
 *
 * @see ExtendedEndpointApplicationConfig
 * @see ServerEndpointConfig.Configurator
 */
public class ProgrammaticEndpoint extends Endpoint {

	@Override
	public void onOpen(final Session session, EndpointConfig ec) {
		// Note a message handler must be added to the session to receive
		// callbacks when new messages arrive.
		session.addMessageHandler(new MessageHandler.Whole<String>() {
			@Override
			public void onMessage(String message) {
				if ("stop".equals(message)) {
					Hello.log(this, "I was asked to stop");
					try {
						session.close();
					} catch (IOException e) {
						Logger.getLogger(ProgrammaticEndpoint.class.getName()).log(Level.SEVERE, null, e);
					}
				} else {
					Hello.log(this, "I got a message: " + message);
					// Send something back to the client for feedback
					try {
	                    session.getBasicRemote().sendText("server received:  " + message);
                    } catch (IOException e) {
                    	// oh well.
                    }
				}
			}
		});

		// (lifecycle) Called when the connection is opened
		Hello.log(this, "I'm open!");
	}

	@Override
	public void onClose(Session session, CloseReason reason) {
		// (lifecycle) Called when the connection is closed
		Hello.log(this, "I'm closed!");
	}

	@Override
	public void onError(Session session, Throwable t) {
		// (lifecycle) Called if/when an error occurs and the connection is
		// disrupted
		Hello.log(this, "oops: " + t);
	}
}
