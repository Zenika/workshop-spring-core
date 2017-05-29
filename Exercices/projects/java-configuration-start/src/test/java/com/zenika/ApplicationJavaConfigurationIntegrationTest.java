/**
 * 
 */
package com.zenika;

import com.zenika.business.UserService;
import org.junit.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.zenika.business.UserService;

/**
 * @author acogoluegnes
 *
 */
// TODO 04 enlever @Ignore
@Ignore
public class ApplicationJavaConfigurationIntegrationTest {

	UserService userService;

	@Before
	public void setUp() {
		// TODO 05 initialiser le contexte Spring à partir de la classe ApplicationConfiguration

		// TODO 06 récupérer le UserService

	}

	@After
	public void tearDown() {
		// TODO 07 appeler close sur le contexte à la fin du test

	}
	
	@Test public void createAndAuthenticateUser() {
		// TODO 08 lancer le test
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
