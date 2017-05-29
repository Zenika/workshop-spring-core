/**
 * 
 */
package com.zenika;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zenika.business.UserService;
import com.zenika.business.impl.UserServiceImpl;
import com.zenika.repository.UserRepository;
import com.zenika.repository.jdbc.JdbcUserRepository;

/**
 * @author acogoluegnes
 *
 */
@Configuration
public class ComponentsConfiguration {
	
	@Autowired DataSource dataSource;

	@Bean public UserRepository userRepository() {
		return new JdbcUserRepository(dataSource);
	}
	
	@Bean public UserService userService() {
		UserServiceImpl userService = new UserServiceImpl();
		userService.setDigest("sha-256");
		userService.setUserRepository(userRepository());
		userService.init();
		return userService;
	}
	
}
