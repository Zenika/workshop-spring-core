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
		// propriété généralement positionnée au lancement du serveur d'applications
		// ou dans le web.xml
		System.setProperty("spring.profiles.active", "standalone-db");
		
		Console.main(args);
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
		
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(wac.getServletContext());
		DataSource dataSource = ctx.getBean(DataSource.class);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("/create-tables.sql"));
		populator.addScript(new ClassPathResource("/insert-data.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource);
		
		System.out.println("Serveur démarré...");
		server.join();
	}

}
