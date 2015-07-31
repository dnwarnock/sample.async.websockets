package net.wasdev.websocket;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.Session;

public class EchoCommon {
	static AtomicInteger endpointId = new AtomicInteger(0);
	static String format = "[ep=%d, msg=%d]: %s";

	/** Instance id -- used to identify this endpoint in the logs */
	int endptId = endpointId.incrementAndGet();

	
	void broadcast(Session session, int id, String message) {
		
		// Look, Ma! Broadcast!
		// Easy as pie to send the same data around to different sessions.
		for (Session s : session.getOpenSessions()) {
			try {
				// Do some munging of the message... 
				// If the message is heading for a different session than it started from, indicate
				// it is a forwarded message by appending " (forwarded)"
				String m = ( s != session ) ? message + " (forwarded)" : message;
				
				// Now format it consistently
				m = String.format(format, endptId, id, m);

				if ( s.isOpen()) { 
					// double check: ensure session is still open, and log and send
					Hello.log(this, "--> ep=" + s.getUserProperties().get("endptId") + ": " + m);
					s.getBasicRemote().sendText(m);
				}
			} catch (IOException e) {
				try {
					s.close();
				} catch (IOException e1) {
				}
			}
		}
	}
}
