package net.wasdev.websocket;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

/**
 * The WebSocket runtime will scan the war for implementations of
 * {@link ServerApplicationConfig}. It will register/deploy programmatic Endpoints
 * returned from {@link #getEndpointConfigs(Set)}, and annotated Endpoints returned
 * from {@link #getAnnotatedEndpointClasses(Set).
 * <p>
 * The following quotes from the official javadoc, to help with context.
 * </p><p>
 * If you have no programmatic endpoints, you don't have to provide an instance of this
 * this class. You could use this to filter the annotated classes you want to return
 * (as an example).
 *
 * @see ServerEndpointConfig.Builder
 * @see ServerEndpointConfig.Configurator
 */
public class EndpointApplicationConfig implements ServerApplicationConfig {

	/**
	 * If you define one of these (which you have to for programmatic
	 * endpoints), and have a mix of annotated and programmatic endpoints (which
	 * this example does), make sure you return classes with annotations
	 * here!
	 *
	 * @param scanned
	 *            the set of all the annotated endpoint classes in the archive
	 *            containing the implementation of this interface.
	 * @return the non-null set of annotated endpoint classes to deploy on the
	 *         server, using the empty set to indicate none.
	 * @see ServerApplicationConfig#getAnnotatedEndpointClasses(Set)
	 */
	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		System.out.println(scanned);
		return scanned;
	}

	/**
	 * Create configurations for programmatic endpoints that should be
	 * deployed. 
	 * <p>
	 * The string value passed to the {@link ServerEndpointConfig.Builder} 
	 * is the URI relative to your app’s context root, similar to the 
	 * value provided in the @ServerEndpoint annotation, e.g. the context 
	 * root for this example application is <code>websocket</code>, and the string
	 * provided to build the {@link ServerEndpointConfig} is <code>/ProgrammaticEndpoint</code>, 
	 * which makes the WebSocket URL used to reach the endpoint
	 * <code>ws://localhost/websocket/ProgrammaticEndpoint</code>.
	 * </p><p>
	 * The ServerEndpointConfig can also be used to configure additional
	 * protocols, and extensions.
	 * </p>
	 *
	 * @param endpointClasses
	 *            the set of all the Endpoint classes in the archive containing
	 *            the implementation of this interface.
	 * @return the non-null set of ServerEndpointConfigs to deploy on the
	 *         server, using the empty set to indicate none.
	 * @see ServerApplicationConfig#getEndpointConfigs(Set)
	 * @see ServerEndpointConfig.Builder#create(Class, String)
	 */
	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(
	        Set<Class<? extends Endpoint>> endpointClasses) {
		System.out.println(endpointClasses);

		HashSet<ServerEndpointConfig> set = new HashSet<ServerEndpointConfig>();

		set.add(ServerEndpointConfig.Builder.create(ProgrammaticEndpoint.class,
		        "/ProgrammaticEndpoint").build());

		return set;
	}
}
