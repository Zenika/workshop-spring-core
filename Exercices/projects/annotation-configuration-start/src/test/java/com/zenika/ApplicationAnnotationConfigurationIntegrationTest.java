/**
 * 
 */
package com.zenika;

import org.junit.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.zenika.business.UserService;

/**
 * @author acogoluegnes
 *
 */
// TODO 04 remove @Ignore
@Ignore
// TODO 05 configure TestContext framework to use TestConfiguration class
// NB: TestConfiguration imports ComponentsConfiguration
public class ApplicationAnnotationConfigurationIntegrationTest {

	// TODO 06 have UserService injected in the test
	private UserService userService;
		
	@Test public void createAndAuthenticateUser() {
		// TODO 07 run test
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
