/**
 * 
 */
package com.zenika;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zenika.business.UserService;

/**
 * @author acogoluegnes
 *
 */
public class ApplicationIntegrationTest {

	private ConfigurableApplicationContext ctx;
	
	private UserService userService;
	
	@Before public void setUp() {
		ctx = new ClassPathXmlApplicationContext(
			"classpath:/test-application-configuration.xml"
		);
		userService = ctx.getBean(UserService.class);
	}
	
	@After public void tearDown() {
		ctx.close();
	}
	
	@Test public void createAndAuthenticateUser() {
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
