/**
 * 
 */
package com.zenika;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zenika.business.UserService;

/**
 * @author acogoluegnes
 *
 */
// TODO 05 remove @Ignore
@Ignore
public class ApplicationIntegrationTest {

	private ConfigurableApplicationContext ctx;
	
	private UserService userService;
	
	@Before public void setUp() {
		// TODO 06 initialize Spring context from test-application-configuration.xml

		// TODO 07 retrieve UserService

	}
	
	@After public void tearDown() {
		// TODO 08 call close() on the context at the en of the test

	}
	
	@Test public void createAndAuthenticateUser() {
		// TODO 09 run the test!
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
