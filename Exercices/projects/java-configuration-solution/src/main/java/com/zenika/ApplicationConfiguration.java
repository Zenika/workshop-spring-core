/**
 * 
 */
package com.zenika;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zenika.business.UserService;
import com.zenika.business.impl.UserServiceImpl;
import com.zenika.repository.UserRepository;
import com.zenika.repository.jdbc.JdbcUserRepository;

/**
 * @author acogoluegnes
 *
 */
@Configuration
public class ApplicationConfiguration {
	
	@Bean public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
		.setType(EmbeddedDatabaseType.H2)
		.addScript("classpath:/create-tables.sql")
		.addScript("classpath:/insert-data.sql")
		.build();
	}

	@Bean public UserRepository userRepository() {
		return new JdbcUserRepository(dataSource());
	}
	
	@Bean public UserService userService() {
		UserServiceImpl userService = new UserServiceImpl();
		userService.setDigest("sha-256");
		userService.setUserRepository(userRepository());
		userService.init();
		return userService;
	}
	
}
