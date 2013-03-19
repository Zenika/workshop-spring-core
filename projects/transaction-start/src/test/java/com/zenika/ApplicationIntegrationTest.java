/**
 * 
 */
package com.zenika;

import org.junit.Assert;

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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-application-configuration.xml")
public class ApplicationIntegrationTest {

	@Autowired private UserService userService;
	
	@Test public void createAndAuthenticateUser() {
		// TODO 07 lancer le test
		// on peut s'assurer que les transactions sont bien activées
		// en rendant le repository transactionnel, avec un niveau de propagation
		// à mandatory. Il suffit ensuite de commenter les annotations @Transactional
		// du service et le test doit échouer.
		String login = "mmouse";
		String password = "this is a test";
		userService.create(login,password);
		Assert.assertNotNull(userService.authenticate(login, password));
	}
	
}
