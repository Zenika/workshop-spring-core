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
// TODO 04 remove @Ignore
@Ignore
public class ApplicationJavaConfigurationIntegrationTest {

	UserService userService;

	@Before
	public void setUp() {
		// TODO 05 initialize Spring applicaton context from ApplicationConfiguration class

		// TODO 06 retrieve UserService bean

	}

	@After
	public void tearDown() {
		// TODO 07 call close() on the context at the end of the test

	}
	
	@Test public void createAndAuthenticateUser() {
		// TODO 08 run the test
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
