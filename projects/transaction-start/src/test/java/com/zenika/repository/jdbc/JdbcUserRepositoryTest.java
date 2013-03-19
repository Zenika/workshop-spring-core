/**
 * 
 */
package com.zenika.repository.jdbc;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zenika.domain.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
public class JdbcUserRepositoryTest {
	
	private UserRepository userRepository;
	
	private EmbeddedDatabase dataSource;
	
	private JdbcOperations tpl;
	@Before public void setUp() {
		dataSource = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("classpath:/create-tables.sql")
			.addScript("classpath:/insert-data.sql")
			.build();
		userRepository = new JdbcUserRepository(dataSource);
		tpl = new JdbcTemplate(dataSource);
	}
	
	@After public void tearDown() {
		dataSource.shutdown();
	}
	
	@Test public void getByLoginUserExists() {
		String login = "acogoluegnes";
		User user = userRepository.getByLogin(login);
		Assert.assertEquals(login, user.getLogin());
	}
	
	@Test public void getByLoginUserDoesNotExist() {
		String login = "mmouse";
		User user =	userRepository.getByLogin(login);
		Assert.assertNull(user);
	}
	
	@Test public void create() {
		int initialCount = tpl.queryForObject("select count(1) from users",Integer.class);
		userRepository.create("mmouse", "password");
		Assert.assertEquals(initialCount+1,tpl.queryForObject("select count(1) from users",Integer.class).intValue());
	}
	
	
	
}
