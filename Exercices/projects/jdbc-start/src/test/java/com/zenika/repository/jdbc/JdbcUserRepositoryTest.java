/**
 * 
 */
package com.zenika.repository.jdbc;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
// TODO 03 remove @Ignore
@Ignore
public class JdbcUserRepositoryTest {
	
	private UserRepository userRepository;
	
	private EmbeddedDatabase dataSource;
	
	@Before public void setUp() {
		// TODO 04 study the initialization of the in-memory database
		// it is using a Spring util class
		dataSource = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("classpath:/create-tables.sql")
			.addScript("classpath:/insert-data.sql")
			.build();
		// TODO 05 instantiate the JdbcUserRepository
		
	}
	
	@After public void tearDown() {
		dataSource.shutdown();
	}
	
	@Test public void getByLoginUserExists() {
		// TODO 06 test getByLogin with an existing user (acogoluegnes)
		// make sure the returned User object has the right login
	}
	
	@Test public void getByLoginUserDoesNotExist() {
		// TODO 07 test getByLogin with a user that does not exist
		// make sure the methods returns null
		
		// TODO 08 run the test
	}
	
	@Test public void create() {
		JdbcOperations tpl = new JdbcTemplate(dataSource);
		int initialCount = tpl.queryForObject("select count(1) from users",Integer.class);
		userRepository.create("mmouse", "password");
		Assert.assertEquals(initialCount+1,tpl.queryForObject("select count(1) from users",Integer.class).intValue());
	}
	
	
	
}
