/**
 * 
 */
package com.zenika.repository.hibernate;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.TestConfiguration;
import com.zenika.domain.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
// TODO 05 enlever @Ignore
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfiguration.class)
public class HibernateUserRepositoryTest {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private DataSource dataSource;
	
	private JdbcOperations tpl;
	
	// TODO 06 lancer le test
	
	@Before public void setUp() {
		tpl = new JdbcTemplate(dataSource);
	}

	@Test public void getByLoginUserExists() {
		String login = "acogoluegnes";
		User user = userRepository.getByLogin(login);
		Assert.assertEquals(login, user.getLogin());
	}
	
	@Test public void getByLoginUserDoesNotExist() {
		String login = "does not exist";
		User user =	userRepository.getByLogin(login);
		Assert.assertNull(user);
	}
	
	@Test public void create() {
		int initialCount = tpl.queryForObject("select count(1) from users",Integer.class);
		userRepository.create("mmouse", "password");
		Assert.assertEquals(initialCount+1,tpl.queryForObject("select count(1) from users",Integer.class).intValue());
	}
	
	
	
}
