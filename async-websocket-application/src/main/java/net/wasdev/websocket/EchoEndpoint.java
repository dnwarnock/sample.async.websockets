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
	public void receiveMessage(String message, Session session)
	        throws IOException {
		// Called when a message is received
		// Single endpoint per connection by default: @OnMessage methods are
		// single threaded! Endpoint/per-connection instances can see each
		// other through sessions, specifically, Endpoints are not 
		// thread safe, but Sessions are

		if ("stop".equals(message)) {
			session.close();
		} else {
			// Look, Ma! Broadcast!
			// Easy as pie to send the same data around to different sessions.
			int id = count++;
			for (Session s : session.getOpenSessions()) {
				s.getBasicRemote().sendText("Echo " + id + ":  " + message);
			}
		}
	}

	@OnError
	public void onError(Throwable t) {
	}
}
