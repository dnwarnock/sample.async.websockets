package net.wasdev.websocket.async.it.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

/**
 * <p>This abstract base class will use the {@link Parameterized} JUnit runner to test various websocket. Subclasses must:</p>
 * <ul>
 * <li>Provide websocket relative to <code>http://localhost:{port}/websocket/</code> via a <code>@Parameters</code> annotated method</li>
 * <li>Implement the {@link #assertResponseStringCorrect(String)} template method to check the response string from a given websocket</li>
 * </ul>
 */
@ClientEndpoint
public class WebsocketIT {

    private Session session;
    private static String response = null;
    private static String error = null;
    
    // We need a no-arg constructor because Jetty attempts to create an instance of this class using reflection.
    public WebsocketIT() {
    	
    }
    
    @Before
    public void cleanTestVars() {
    	// Reset the static variables we use to test that the responses are correct.
    	WebsocketIT.response = null;
    	WebsocketIT.error = null;
    }
    
    @Test
    public void testSimpleAnnotatedWebSocket() throws Exception {
    	testWebsocket("SimpleAnnotated", "server received:  This is a test from SimpleAnnotated", false);
    }
    
    @Test
    public void testProgrammaticEndpointWebSocket() throws Exception {
    	testWebsocket("ProgrammaticEndpoint", "server received:  This is a test from ProgrammaticEndpoint", false);
    }
    
    @Test
    public void testEchoEndpointWebSocket() throws Exception {
    	testWebsocket("EchoEndpoint", "[ep=3, msg=0]: This is a test from EchoEndpoint", false);
    }
    
    @Test
    public void testEchoAsyncEndpointWebSocket() throws Exception {
    	testWebsocket("EchoAsyncEndpoint", "[ep=2, msg=0]: This is a test from EchoAsyncEndpoint", false);
    }
    
    @Test
    public void testEchoEncoderEndpointWebSocket() throws Exception {
    	testWebsocket("EchoEncoderEndpoint", "{\"count\":0,\"content\":\"This is a test from EchoEncoderEndpoint\"}", true);
    }
    
    /**
     * The main test method. This accepts the websocket endpoint to connect to, and sends a request through to the server.
     * It then waits for a response and check that it is the correct response.
     * @param websocketEndpoint - The websocket endpoint name. This is constructed into a full ws://... url
     * @param expectedResponse - The message we expect back from the websocket
     * @param sendObject - A boolean indicating whether we need to put the request String in JSON or not.
     * @throws Exception
     */
    public void testWebsocket(String websocketEndpoint, String expectedResponse, boolean sendObject) throws Exception {
    	try {
    		// Create a WebSocket client, and build up the websocket URL to use.
    		WebSocketContainer c = ContainerProvider.getWebSocketContainer();
			String port = System.getProperty("liberty.test.port");
	        String websocketURL = "ws://localhost:" + port + "/websocket/" + websocketEndpoint;
	        URI uriServerEP = URI.create(websocketURL);
			// Connect to the websocket
	        session = c.connectToServer(WebsocketIT.class, uriServerEP);
			
	        // Set a default text to send. If we need to send an object, create the JSON String instead.
	        String textToSend = "This is a test from " + websocketEndpoint;
			if (sendObject) {
				textToSend = "{\"content\": \"This is a test from " + websocketEndpoint + "\"}";
			} 
    		
			// Send the text string to the server
			session.getBasicRemote().sendText(textToSend);
			
			// Now wait for up to 5 secs to get the response back.
    		int count = 0;
    		while (WebsocketIT.response == null && count < 10) {
    			count++;
    			Thread.sleep(500);
    		}
    		
    		// Run the Test asserts to ensure we have received the correct string, and that we haven't hit any errors.
    		assertEquals("Message sent from the server doesn't match expected String", expectedResponse, WebsocketIT.response);
    		assertNull("There was an unexpected error during test: " + WebsocketIT.error, WebsocketIT.error);
    		
    	} finally {
    		// Finally if we have created a session, close it off.
    		if (session != null) {
    			try {
    				session.close();
    			} catch(Exception ioe) {
    				ioe.printStackTrace();
    			}
    		}
    	}
    }
    
	/**
	 * This method is triggered when the server sends a message across the websocket. We store this away in a static var
	 * so that we can check that the response was as expected.
	 * @param message - A String returned from the server.
	 * @param session - The session that we are using for the connection.
	 * @throws IOException
	 */
	@OnMessage
	public void receiveMessage(String message, Session session) throws IOException {
		WebsocketIT.response = message;
	}
	
	/**
	 * This method is triggered when the server sends an error across the websocket. We store this away in a static var
	 * so that we can check whether it was expected or not.
	 * @param t - A Throwable object
	 */
	@OnError
	public void onError(Throwable t) {
		WebsocketIT.error = t.getMessage();
	}
}
