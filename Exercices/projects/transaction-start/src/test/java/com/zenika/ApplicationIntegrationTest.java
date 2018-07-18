/**
 * 
 */
package com.zenika;

import com.zenika.business.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=TestConfiguration.class)
public class ApplicationIntegrationTest {

	@Autowired private UserService userService;
	
	@Test public void createAndAuthenticateUser() {
		// TODO 07 run the test
		// you can make sure that transactions are activated
		// by making the repository transactional, with a propagation level
		// set to mandatory. You just need to comment out the @Transactional
		// on the service and the test should fail.
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
