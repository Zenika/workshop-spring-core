/**
 * 
 */
package com.zenika;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @author acogoluegnes
 *
 */
// TODO 06 s'assurer que la classe de configuration des transactions est incluse
@Import({
	ComponentsConfiguration.class,
	TransactionConfiguration.class
})
public class TestConfiguration {

	@Bean public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
		.setType(EmbeddedDatabaseType.H2)
		.addScript("classpath:/create-tables.sql")
		.addScript("classpath:/insert-data.sql")
		.build();
	}
	
}
