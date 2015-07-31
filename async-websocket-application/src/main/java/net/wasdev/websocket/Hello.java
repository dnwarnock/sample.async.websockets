package net.wasdev.websocket;

import javax.websocket.Session;

public class Hello {
	static final String log_format = "%-30s - %s";
	
	public static void log(Object source, String message) {
		System.out.println(String.format(log_format, source == null ? "null" : source.getClass().getSimpleName(), message));
	}
}

