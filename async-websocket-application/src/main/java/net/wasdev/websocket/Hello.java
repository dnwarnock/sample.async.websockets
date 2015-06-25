package net.wasdev.websocket;

public class Hello {
	static final String format = "%-60s - %s";
	
	public static void log(Object source, String message) {
		System.out.println(String.format(format, source == null ? "null" : source.toString(), message));
	}
}

