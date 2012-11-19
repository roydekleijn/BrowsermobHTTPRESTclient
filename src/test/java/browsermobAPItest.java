import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import httpRestClient.BrowserMobProxy;

@Test
public class browsermobAPItest {

	private BrowserMobProxy proxy;

	@BeforeMethod
	public void setUp() {
		proxy = new BrowserMobProxy("http://localhost", 9090);
	}

	public void createNewProxy() {
		System.out.println(proxy.getPort());
	}

	public void createNewProxyGivenPort() {
		System.out.println(proxy.getPort(9909));
	}
	
	public void createNewHar() throws IOException, InterruptedException {
		int port = proxy.getPort();
		System.out.println(proxy.createNewHar(port, "new HAR"));
	}
	
	public void createNewPageRef() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR");
		System.out.println(proxy.startNewPage(port, "new pagereference"));
	}
	
	public void shutDownProxy() {
		int port = proxy.getPort();
		System.out.println(proxy.shutDownProxy(port));
	}
	
	public void getHar() throws IOException {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR");
		System.out.println(proxy.getHar(port).getLog().getVersion());
	}
	
	public void createPatternToWhitelist() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR");
		System.out.println(proxy.setWhitelist(port, "http://images\\.example.com\\.com/.*", 200));
	}
	
	public void createPatternToBlacklist() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR");
		System.out.println(proxy.setBlacklist(port, "http://images\\.example.com\\.com/.*", 200));
	}
	
	public void setDownstreamLimit() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR");
		System.out.println(proxy.setDownstreamKbps(port, 125));
	}
	
	public void setUpstreamLimit() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR");
		System.out.println(proxy.setUpstreamKbps(port, 125));
	}
	
	public void setLatencyLimit() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR");
		System.out.println(proxy.setLatency(port, 158));
	}
	
	public void enableBandwithLimiter() {
		
	}
	
	public void disableBandwithLimiter() {
		
	}
	
	public void setPayloadPercentage() {
		
	}
	
	public void overrideHttpHeaders() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR");
		proxy.setHeaders(port,
				"{\"Proxy-Authorization\": \"Basic password\"}");
	}
	
	public void setHost() {
		int port = proxy.getPort();
		proxy.setHosts(port, "{\"example.com\": \"1.2.3.4\"}");
	}
	
	public void setUpstreamProxy() {
		System.out.println(proxy.getPortUsingUpstreamProxy("localhost:9090"));
	}
}
