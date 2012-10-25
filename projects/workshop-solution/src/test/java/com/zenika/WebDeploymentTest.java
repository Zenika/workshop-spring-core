/**
 * 
 */
package com.zenika;

import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author acogoluegnes
 * 
 */
public class WebDeploymentTest {

	@Before
	public void setUp() {
		System.setProperty("spring.profiles.active", "in-memory-db");
	}

	@After
	public void tearDown() {
		System.clearProperty("spring.profiles.active");
	}

	@Test
	public void webDeployment() throws Exception {
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		int port = TestUtils.getAvailablePort();
		connector.setPort(port);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/spring-workshop");
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		try {
			server.start();
			URL listUsersUrl = new URL("http://127.0.0.1:" + port + "/spring-workshop/list-users");
			HttpURLConnection connection = (HttpURLConnection) listUsersUrl.openConnection();
			Assert.assertEquals(200, connection.getResponseCode());
		} finally {
			if (server.isStarted()) {
				server.stop();
			}
		}
	}
}
