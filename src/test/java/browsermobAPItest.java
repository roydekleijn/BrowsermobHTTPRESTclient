import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.Random;

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
	
	public void createSecondPage() {
		int port = proxy.getPort();
		System.out.println(proxy.createNewHar(port, "new HAR", true, false));
		System.out.println(proxy.startNewPage(port, "new pagereference"));
		System.out.println(proxy.startNewPage(port, "new pagereference"));
	}

	public void createNewProxy() {
		assertThat(proxy.getPort(), is(Integer.class));
	}

	public void createNewProxyGivenPort() {
		Random random = new Random();
		int randomInt = random.nextInt(9999) + 1000;
		assertThat(proxy.getPort(randomInt), is(Integer.class));
	}

	public void createNewHar() throws IOException, InterruptedException {
		int port = proxy.getPort();
		assertThat(proxy.createNewHar(port, "newHAR", true, false), is(true));
	}

	public void createNewPageRef() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.startNewPage(port, "new pagereference"), is(true));
	}

	public void shutDownProxy() {
		int port = proxy.getPort();
		assertThat(proxy.shutDownProxy(port), is(true));
	}

	public void getHar() throws IOException {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.getHar(port).getLog().getVersion(), is(String.class));
	}

	public void createPatternToWhitelist() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.setWhitelist(port,
				"http://images\\.example.com\\.com/.*", 200), is(true));
	}

	public void createPatternToBlacklist() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.setBlacklist(port,
				"http://images\\.example.com\\.com/.*", 200), is(true));
	}

	public void setDownstreamLimit() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.setDownstreamKbps(port, 125), is(true));
	}

	public void setUpstreamLimit() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.setUpstreamKbps(port, 125), is(true));
	}

	public void setLatencyLimit() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.setLatency(port, 158), is(true));
	}

	public void enableBandwithLimiter() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.setLimit(port, true), is(true));
	}

	public void disableBandwithLimiter() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.setLimit(port, false), is(true));
	}

	public void setPayloadPercentage() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.setPayloadPercentage(port, 80), is(true));
	}

	public void overrideHttpHeaders() {
		int port = proxy.getPort();
		proxy.createNewHar(port, "new HAR", true, false);
		assertThat(proxy.setHeaders(port,
				"{\"Proxy-Authorization\": \"Basic password\"}"), is(true));
	}

	public void setHost() {
		int port = proxy.getPort();
		proxy.setHosts(port, "{\"example.com\":");
	}

	public void setUpstreamProxy() {
		assertThat(proxy.getPortUsingUpstreamProxy("localhost:9090"),
				is(Integer.class));
	}
}
