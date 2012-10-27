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
		// TODO 08 lancer cette classe. Elle démarre un conteneur web.
		// La configuration "de prod" de l'application utilisée (cf. positionnement profil).
		// Elle lance aussi l'interface d'administration de H2.
		
		// TODO 09 ouvrir la page http://localhost:8080/spring-workshop/list-users
		// Les utilisateurs s'affichent. Pour vérifier que l'application utilise
		// bien une base de données externe, se connecter à l'interface d'admin de H2
		// (URL : jdbc:h2:tcp://localhost/mem:spring_workshop, user : sa, mot de passe vide).
		// Insérer un User en SQL puis réactualiser la page web.
		
		
		// propriété généralement positionnée au lancement du serveur d'applications
		// ou dans le web.xml
		System.setProperty("spring.profiles.active", "standalone-db");
		
		Server server = startAndInitServers();
		
		System.out.println("Serveur démarré...");
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
