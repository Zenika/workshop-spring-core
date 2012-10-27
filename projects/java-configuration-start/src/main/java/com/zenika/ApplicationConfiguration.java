/**
 * 
 */
package com.zenika;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @author acogoluegnes
 *
 */
// TODO 01 ajouter l'annotation appropriée à la classe de configuration
public class ApplicationConfiguration {
	
	// TODO 02 ajouter l'annotation pour que la méthode soit bien prise en compte
	// (pour la création d'un bean Spring)
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
		.setType(EmbeddedDatabaseType.H2)
		.addScript("classpath:/create-tables.sql")
		.addScript("classpath:/insert-data.sql")
		.build();
	}
	
	// TODO 03 déclarer le UserRepository et le UserService
	// (bien penser à initialiser le UserService)

}
