package httpclient;

import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class BrowserMobProxy {

	private WebResource service;

	private String data;

	public BrowserMobProxy(String host, int port) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service = client.resource(host + ":" + port + "/proxy");
	}

	public String getPort() {
		ClientResponse response = service.post(ClientResponse.class);
		String port = response.getEntity(String.class).substring(8, 12);
		return port;
	}

	public String getPortUsingUpstreamProxy(String upStreamProxyServer) {
		ClientResponse response = service.queryParam("httpProxy",
				upStreamProxyServer).post(ClientResponse.class);
		String port = response.getEntity(String.class).substring(8, 12);
		return port;
	}

	public int createNewHar(int port, String initialPageRef) {
		if (!initialPageRef.isEmpty()) {
			data = "initialPageRef= " + initialPageRef;
		}
		ClientResponse response = service.path(Integer.toString(port))
				.path("har").put(ClientResponse.class, data);
		return response.getStatus();
	}

	public String startNewPage(int port, String pageRef) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		if (!pageRef.isEmpty()) {
			formData.add("pageRef", pageRef);
		}
		ClientResponse response = service.path(Integer.toString(port))
				.path("har").path("pageRef")
				.put(ClientResponse.class, formData);
		return response.getEntity(String.class);
	}

	public String shutDownProxy(int port) {
		ClientResponse response = service.path(Integer.toString(port)).delete(
				ClientResponse.class);
		return response.getEntity(String.class);
	}

	public String getHar(int port) throws IOException {
		ClientResponse response = service.path(Integer.toString(port))
				.path("har").accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		return response.getEntity(String.class);
	}

	public String setWhitelist(int port, String regex, String status) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("regex", regex);
		formData.add("status", status);
		ClientResponse response = service.path(Integer.toString(port))
				.path("whitelist").put(ClientResponse.class, formData);
		return response.getEntity(String.class);
	}

	public String setBlacklist(int port, String regex, String status) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("regex", regex);
		formData.add("status", status);
		ClientResponse response = service.path(Integer.toString(port))
				.path("blacklist").put(ClientResponse.class, formData);
		return response.getEntity(String.class);
	}

	public String setDownstreamKbps(int port, long downstreamKbps) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("downstreamKbps", Long.toString(downstreamKbps));
		ClientResponse response = service.path(Integer.toString(port))
				.path("limit").put(ClientResponse.class, formData);
		return response.getEntity(String.class);
	}

	public String setUpstreamKbps(int port, long upstreamKbps) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("upstreamKbps", Long.toString(upstreamKbps));
		ClientResponse response = service.path(Integer.toString(port))
				.path("limit").put(ClientResponse.class, formData);
		return response.getEntity(String.class);
	}

	public String setLatency(int port, long latency) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("latency", Long.toString(latency));
		ClientResponse response = service.path(Integer.toString(port))
				.path("limit").put(ClientResponse.class, formData);
		return response.getEntity(String.class);
	}

	public String setHeaders(int port, String headers) {
		ClientResponse response = service.path(Integer.toString(port))
				.path("headers").post(ClientResponse.class, headers);
		return response.getEntity(String.class);
	}

	public String setHosts(int port, String hosts) {
		ClientResponse response = service.path(Integer.toString(port))
				.path("hosts").put(ClientResponse.class, hosts);
		return response.getEntity(String.class);
	}

}