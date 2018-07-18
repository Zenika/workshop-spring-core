/**
 * 
 */
package com.zenika;

import javax.sql.DataSource;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.h2.tools.Console;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author acogoluegnes
 *
 */
public class SpringWorkshopApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO 08 start this class. It has a Web container in it.
		// "Production" configuration is used (see profile setting).
		// It also starts H2 admin console.
		
		// TODO 09 go to http://localhost:8080/spring-workshop/list-users
		// The list of users is displayed. To make sure the application is using an external database,
		// go to H2 admin console
		// (URL : jdbc:h2:tcp://localhost/mem:spring_workshop, user=sa, empty password).
		// Insert a new User (SQL query), then refresh the Web page.
		
		
		// this property is generally specified when the application server is started
		// or in web.xml
		System.setProperty("spring.profiles.active", "standalone-db");
		
		Server server = startAndInitServers();
		
		System.out.println("Server started...");
		server.join();
	}
	
	private static Server startAndInitServers() throws Exception {
		Console.main();
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		int port = 8080;
		connector.setPort(port);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/spring-workshop");
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		server.start();
		
		initDatabaseData(wac);
		return server;
	}

	private static void initDatabaseData(WebAppContext wac) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(wac.getServletContext());
		DataSource dataSource = ctx.getBean(DataSource.class);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("/create-tables.sql"));
		populator.addScript(new ClassPathResource("/insert-data.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

}
