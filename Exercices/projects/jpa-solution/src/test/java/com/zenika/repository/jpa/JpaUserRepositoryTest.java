/**
 * 
 */
package com.zenika.repository.jpa;

import com.zenika.TestConfiguration;
import com.zenika.domain.User;
import com.zenika.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=TestConfiguration.class)
public class JpaUserRepositoryTest {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private DataSource dataSource;
	
	private JdbcOperations tpl;
	
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
		try {
			userRepository.getByLogin(login);
			Assert.fail("L'utilisateur n'existe pas, une exception aurait du être lancée");
		} catch(EmptyResultDataAccessException e) {
			// OK
		}
	}
	
	@Test public void create() {
		int initialCount = tpl.queryForObject("select count(1) from users",Integer.class);
		userRepository.create("mmouse", "password");
		Assert.assertEquals(initialCount+1,tpl.queryForObject("select count(1) from users",Integer.class).intValue());
	}
	
	
	
}
