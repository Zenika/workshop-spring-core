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
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zenika.business.UserService;

/**
 * @author acogoluegnes
 *
 */
// TODO 05 enlever @Ignore
@Ignore
public class ApplicationIntegrationTest {

	private ConfigurableApplicationContext ctx;
	
	private UserService userService;
	
	@Before public void setUp() {
		// TODO 06 initialiser le contexte Spring à partir du fichier test-application-configuration.xml

		// TODO 07 récupérer le UserService

	}
	
	@After public void tearDown() {
		// TODO 08 appeler close sur le contexte à la fin du test

	}
	
	@Test public void createAndAuthenticateUser() {
		// TODO 09 lancer le test !
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
