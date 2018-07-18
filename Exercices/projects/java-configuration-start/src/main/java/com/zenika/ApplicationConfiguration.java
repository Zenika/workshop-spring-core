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
// TODO 01 add the right annotation for a configuration class
public class ApplicationConfiguration {
	
	// TODO 02 add the right annotation, so that the method is taken into account
	// (for the creation of a Spring bean)
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
		.setType(EmbeddedDatabaseType.H2)
		.addScript("classpath:/create-tables.sql")
		.addScript("classpath:/insert-data.sql")
		.build();
	}
	
	// TODO 03 declare the UserRepository and UserService
	// (make sure you initialize the UserService)

}
