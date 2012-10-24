/**
 * 
 */
package com.zenika;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.zenika.business.UserService;

/**
 * @author acogoluegnes
 * @todo ne fonctionne pas bien avec les annotations
 *
 */
@Ignore
public class ApplicationJavaConfigurationIntegrationTest {

	private ConfigurableApplicationContext ctx;
	
	private UserService userService;
	
	@Before public void setUp() {
		ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
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
