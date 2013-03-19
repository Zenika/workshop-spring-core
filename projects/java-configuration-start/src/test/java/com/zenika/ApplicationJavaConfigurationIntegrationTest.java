/**
 * 
 */
package com.zenika;

import org.junit.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.business.UserService;

/**
 * @author acogoluegnes
 *
 */
// TODO 04 enlever @Ignore
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
// TODO 05 configurer @ContextConfiguration pour utiliser ApplicationConfiguration
@ContextConfiguration
public class ApplicationJavaConfigurationIntegrationTest {

	@Autowired private UserService userService;
	
	@Test public void createAndAuthenticateUser() {
		// TODO 06 lancer le test
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
