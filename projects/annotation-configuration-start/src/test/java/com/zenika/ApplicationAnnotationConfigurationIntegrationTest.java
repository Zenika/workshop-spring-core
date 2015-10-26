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
// TODO 04 supprimer @Ignore
@Ignore
// TODO 05 configurer le TestContext Framework pour utiliser la classe TestConfiguration
// NB: la classe TestConfiguration importe la classe ComponentsConfiguration
public class ApplicationAnnotationConfigurationIntegrationTest {

	// TODO 06 faire en sorte que le UserService soit injecté dans le test
	private UserService userService;
		
	@Test public void createAndAuthenticateUser() {
		// TODO 07 lancer le test
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
