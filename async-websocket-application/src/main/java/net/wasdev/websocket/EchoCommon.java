package net.wasdev.websocket;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.Session;

/**
 * Common class that helps identify endpoints when running the sample and 
 * observing output.
 */
public class EchoCommon {
	static AtomicInteger endpointId = new AtomicInteger(0);
	static String format = "[ep=%d, msg=%d]: %s";

	/** Instance id -- used to identify this endpoint in the logs */
	int endptId = endpointId.incrementAndGet();

	
	/**
	 * Simple text based broadcast. 
	 * This does some additional munging of the message text, to make it more
	 * obvious where the message originated (is an endpoint getting its own
	 * message back, or has it been forwarded from another endpoint).
	 * 
	 * @param session
	 * @param id
	 * @param message
	 */
	void broadcast(Session session, int id, String message) {
		
		// Look, Ma! Broadcast!
		// Easy as pie to send the same data around to different sessions.
		for (Session s : session.getOpenSessions()) {
			try {
				// Do some munging of the message... 
				// If the message is heading for a different session/endpoint than it started from, 
				// indicate it is a forwarded message by appending " (forwarded)"
				// Note that it can be easier to code clients to always react to messages as they 
				// are returned over the socket, rather than dealing separately with messages they
				// sent. If you don't want messages to go back to the sender, you can use the same check
				// to skip routing back to the sender entirely.
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
