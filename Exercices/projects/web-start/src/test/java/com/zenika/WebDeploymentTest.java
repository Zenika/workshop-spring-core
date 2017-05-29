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
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author acogoluegnes
 * 
 */
// TODO 05 enlever @Ignore
@Ignore
public class WebDeploymentTest {

	@Before
	public void setUp() {
		// TODO 06 activer le profil in-memory-db
		
	}

	@After
	public void tearDown() {
		System.clearProperty("spring.profiles.active");
	}

	@Test
	public void webDeployment() throws Exception {
		// TODO 07 lancer le test
		Server server = initServer();
		int port = server.getConnectors()[0].getPort();

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
	
	private Server initServer() {
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
		return server;
	}
}
