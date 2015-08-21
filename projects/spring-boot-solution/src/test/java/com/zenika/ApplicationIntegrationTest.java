/**
 * 
 */
package com.zenika;

import com.zenika.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestOperations;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
@WebIntegrationTest("server.port:0")
public class ApplicationIntegrationTest {

	@Value("${local.server.port}") int port;

	RestOperations tpl = new TestRestTemplate();

	@Test public void listUsers() {
		User[] users = tpl.getForObject(baseUrl() + "users", User[].class);
		Assert.assertEquals(2,users.length);
	}

	String baseUrl() {
		return "http://localhost:"+port+"/";
	}

}
