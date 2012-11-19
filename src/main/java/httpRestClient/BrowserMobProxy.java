/*
 * 
 */
package httpRestClient;

import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.browsermob.core.har.Har;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class BrowserMobProxy.
 */
public class BrowserMobProxy {

	/** The service. */
	private WebResource service;

	/** The data. */
	private String data;

	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Instantiates a new browser mob proxy.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 */
	public BrowserMobProxy(String host, int port) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service = client.resource(host + ":" + port + "/proxy");
	}

	/**
	 * Gets the port.
	 * 
	 * @return the port
	 */
	public int getPort() {
		ClientResponse response = service.post(ClientResponse.class);
		int port = Integer.parseInt(response.getEntity(String.class).substring(
				8, 12));
		return port;
	}

	/**
	 * Gets the port.
	 * 
	 * @param port
	 *            the port
	 * @return the port
	 */
	public int getPort(int port) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("port", Integer.toString(port));
		ClientResponse response = service.post(ClientResponse.class, formData);
		int newPort = Integer.parseInt(response.getEntity(String.class)
				.substring(8, 12));
		return newPort;
	}

	/**
	 * Gets the port using upstream proxy.
	 * 
	 * @param upStreamProxyServer
	 *            the up stream proxy server
	 * @return the port using upstream proxy
	 */
	public int getPortUsingUpstreamProxy(String upStreamProxyServer) {
		ClientResponse response = service.queryParam("httpProxy",
				upStreamProxyServer).post(ClientResponse.class);
		int port = Integer.parseInt(response.getEntity(String.class).substring(
				8, 12));
		return port;
	}

	/**
	 * Creates the new har. Creates a new HAR attached to the proxy and returns
	 * the HAR content if there was a previous HAR. Supports the following
	 * parameters:
	 * 
	 * initialPageRef - the string name of the first page ref that should be
	 * used in the HAR. Defaults to "Page 1".
	 * 
	 * 
	 * @param port
	 *            the port
	 * @param initialPageRef
	 *            the initial page ref
	 * @return the int
	 */
	public boolean createNewHar(int port, String initialPageRef) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		if (!initialPageRef.isEmpty()) {
			formData.add("initialPageRef", initialPageRef);
		}
		ClientResponse response = service.path(Integer.toString(port))
				.path("har").put(ClientResponse.class, formData);
		if (response.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Start new page. Starts a new page on the existing HAR. Supports the
	 * following parameters:
	 * 
	 * pageRef - the string name of the first page ref that should be used in
	 * the HAR. Defaults to "Page N" where N is the next page number.
	 * 
	 * 
	 * @param port
	 *            the port
	 * @param pageRef
	 *            the page ref
	 * @return the string
	 */
	public boolean startNewPage(int port, String pageRef) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		if (!pageRef.isEmpty()) {
			formData.add("pageRef", pageRef);
		}
		ClientResponse response = service.path(Integer.toString(port))
				.path("har").path("pageRef")
				.put(ClientResponse.class, formData);
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Shut down proxy. Shuts down the proxy and closes the port
	 * 
	 * @param port
	 *            the port
	 * @return the string
	 */
	public boolean shutDownProxy(int port) {
		ClientResponse response = service.path(Integer.toString(port)).delete(
				ClientResponse.class);
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the har. Returns the JSON/HAR content representing all the HTTP
	 * traffic passed through the proxy
	 * 
	 * @param port
	 *            the port
	 * @return the har
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Har getHar(int port) throws IOException {
		ClientResponse response = service.path(Integer.toString(port))
				.path("har").get(ClientResponse.class);
		return mapper.readValue(response.getEntity(String.class), Har.class);
	}

	/**
	 * Sets the whitelist. Sets a list of URL patterns to whitelist. Takes the
	 * following parameters:
	 * 
	 * regex - a comma separated list of regular expressions status - the HTTP
	 * status code to return for URLs that do not match the whitelist
	 * 
	 * 
	 * @param port
	 *            the port
	 * @param regex
	 *            the regex
	 * @param status
	 *            the status
	 * @return the string
	 */
	public boolean setWhitelist(int port, String regex, int status) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("regex", regex);
		formData.add("status", Integer.toString(status));
		ClientResponse response = service.path(Integer.toString(port))
				.path("whitelist").put(ClientResponse.class, formData);
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the blacklist. Set a URL to blacklist. Takes the following
	 * parameters:
	 * 
	 * regex - the blacklist regular expression status - the HTTP status code to
	 * return for URLs that are blacklisted
	 * 
	 * 
	 * @param port
	 *            the port
	 * @param regex
	 *            the regex
	 * @param status
	 *            the status
	 * @return the string
	 */
	public boolean setBlacklist(int port, String regex, int status) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("regex", regex);
		formData.add("status", Integer.toString(status));
		ClientResponse response = service.path(Integer.toString(port))
				.path("blacklist").put(ClientResponse.class, formData);
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the downstream kbps. Sets an artificial kilobits-per-second value
	 * for all data transfered.
	 * 
	 * @param port
	 *            the port
	 * @param downstreamKbps
	 *            the downstream kbps
	 * @return the string
	 */
	public boolean setDownstreamKbps(int port, long downstreamKbps) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("downstreamKbps", Long.toString(downstreamKbps));
		ClientResponse response = service.path(Integer.toString(port))
				.path("limit").put(ClientResponse.class, formData);
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the upstream kbps. Sets an artificial kilobits-per-second value for
	 * all data. Typically this value should be less than the downstream value
	 * to reflect a real internet user.
	 * 
	 * @param port
	 *            the port
	 * @param upstreamKbps
	 *            the upstream kbps
	 * @return the string
	 */
	public boolean setUpstreamKbps(int port, long upstreamKbps) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("upstreamKbps", Long.toString(upstreamKbps));
		ClientResponse response = service.path(Integer.toString(port))
				.path("limit").put(ClientResponse.class, formData);
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the latency. Sets an artificial latency simulation for sending one
	 * full MTU (~1500 bytes on the internet). For high speed connections, such
	 * as FIOS, 1ms is fine. To simulate a slower ADSL connection, 50ms might
	 * make sense. For really slow dial-up connections, 200ms or more may be
	 * appropriate.
	 * 
	 * @param port
	 *            the port
	 * @param latency
	 *            the latency
	 * @return the string
	 */
	public boolean setLatency(int port, long latency) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("latency", Long.toString(latency));
		ClientResponse response = service.path(Integer.toString(port))
				.path("limit").put(ClientResponse.class, formData);
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the headers. Set and override HTTP Request headers. For example
	 * setting a custom User-Agent.
	 * 
	 * Payload data should be json encoded set of headers (not url-encoded)
	 * 
	 * 
	 * @param port
	 *            the port
	 * @param headers
	 *            the headers
	 * @return the string
	 */
	public boolean setHeaders(int port, String headers) {
		ClientResponse response = service.path(Integer.toString(port))
				.path("headers").post(ClientResponse.class, headers);
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the hosts. Overrides normal DNS lookups and remaps the given hosts
	 * with the associated IP address
	 * 
	 * Payload data should be json encoded set of name/value pairs (ex:
	 * {"example.com": "1.2.3.4"})
	 * 
	 * 
	 * @param port
	 *            the port
	 * @param hosts
	 *            the hosts
	 * @return the string
	 */
	public void setHosts(int port, String hosts) {
		ClientResponse response = service.path(Integer.toString(port))
				.path("hosts").put(ClientResponse.class, hosts);
		System.out.println(response.getStatus());
	}

}