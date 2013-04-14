import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import httpRestClient.BrowserMobProxy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

@Test
public class browsermobAPItest {

	private BrowserMobProxy proxy;

	@BeforeMethod
	public void setUp() {
		proxy = new BrowserMobProxy("http://localhost", 9090);
		proxy.setPort(proxy.getPort());
	}

	public void testSetUpstreamProxy() {
		assertThat(proxy.getPortUsingUpstreamProxy("localhost:9090"),
				is(Integer.class));
	}

	public void testHeaders() throws JsonGenerationException,
			JsonMappingException, IOException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("cccc", "cccc");
		headers.put("xxxx", "xxxx");
		assertThat(proxy.addHeader(headers), Matchers.equalTo(true));
	}

	public void testBasicAuthentication() throws JsonGenerationException,
			JsonMappingException, UniformInterfaceException,
			ClientHandlerException, IOException {
		assertThat(proxy.autoBasicAuthorization("www.httpwatch.com", "tester",
				"tester"), Matchers.equalTo(true));
	}

	public void testBlacklist() {
		assertThat(proxy.blacklistRequests("[^abc]", 204),
				Matchers.equalTo(true));
	}

	public void testClearDNSCache() {
		assertThat(proxy.clearDNSCache(), Matchers.equalTo(true));
	}

	public void testGetHar() throws IOException {
		proxy.newHar("new har", true, true, true);
		assertThat(proxy.getHar().getLog().getVersion(),
				Matchers.is(String.class));
	}

	public void testGetPort() {
		assertThat(proxy.getPort(), Matchers.is(Integer.class));
	}

	public void testGetGivenPort() {
		assertThat(proxy.getPort(5555), Matchers.equalTo(5555));
	}

	public void testEnableLimiter() {
		assertThat(proxy.enableLimiter(false), Matchers.equalTo(true));
	}

	public void testDownStreamProxy() {
		assertThat(proxy.setDownstreamKbps(245), Matchers.equalTo(true));
	}

	public void testLatency() {
		assertThat(proxy.setLatency(852), Matchers.equalTo(true));
	}

	public void testMaxBitPerSecond() {
		assertThat(proxy.setMaxBitsPerSecondThreshold(80),
				Matchers.equalTo(true));
	}

	public void testPayLoadPercentage() {
		assertThat(proxy.setPayloadPercentage(25), Matchers.equalTo(true));
	}

	public void testUpstreamKBPS() {
		assertThat(proxy.setUpstreamKbps(50), Matchers.equalTo(true));
	}

	public void testNewHar() {
		assertThat(proxy.newHar("newhar", false, true, false),
				Matchers.equalTo(true));
	}

	public void testNewPage() {
		proxy.newHar("newhar", false, true, false);
		assertThat(proxy.newPage("new Page"), Matchers.equalTo(true));
	}

	public void testRemapHosts() throws JsonGenerationException,
			JsonMappingException, UniformInterfaceException,
			ClientHandlerException, IOException {
		Map<String, String> hosts = new HashMap<String, String>();
		hosts.put("cccc", "cccc");
		hosts.put("xxxx", "xxxx");
		assertThat(proxy.remapHost(hosts), Matchers.equalTo(true));
	}

	public void testRewriteUrl() {
		assertThat(proxy.rewriteUrl("yy", "xxx"), Matchers.equalTo(true));
	}

	public void testConnectionTimeout() {
		assertThat(proxy.setConnectionTimeout(5), Matchers.equalTo(true));
	}

	public void testDnsCacheTimeout() {
		assertThat(proxy.setDNSCacheTimeout(8), Matchers.equalTo(true));
	}

	public void testRequestTimeout() {
		assertThat(proxy.setRequestTimeout(8), Matchers.equalTo(true));
	}

	public void testRetryCount() {
		assertThat(proxy.setRetryCount(8), Matchers.equalTo(true));
	}

	public void testSocketOperationTimeout() {
		assertThat(proxy.setSocketOperationTimeout(8), Matchers.equalTo(true));
	}

	public void testStop() {
		assertThat(proxy.stop(), Matchers.equalTo(true));
	}

	public void testWaitNetworkTraffic() {
		assertThat(proxy.waitForNetworkTrafficToStop(50, 1000),
				Matchers.equalTo(true));
	}

	public void testWhitelist() {
		assertThat(proxy.whitelistRequests("bf, gn", 500),
				Matchers.equalTo(true));
	}
}
