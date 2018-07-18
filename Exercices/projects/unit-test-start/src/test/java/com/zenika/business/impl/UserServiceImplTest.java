/**
 * 
 */
package com.zenika.business.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.zenika.domain.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
// TODO 02 remove @Ignore
@Ignore
public class UserServiceImplTest {

	private UserRepository userRepository;
	
	private UserServiceImpl userService;
	
	@Before public void setUp() {
		// TODO 03 initialize the mock
		// the mock is then injected in the service
		
		userService = new UserServiceImpl();
		userService.setUserRepository(userRepository);
		userService.init();
	}
	
	@Test public void getByLoginNoUser() {
		String login = "test";
		// TODO 04 configure the ock so that it returns null when getByLogin is called

		// TODO 05 make sure UserService.authenticate returns null
		
		// verify that the method has been called
		verify(userRepository).getByLogin(login);
		
		// TODO 06 run the test
	}
	
	@Test public void getByLoginNullEmptyPassword() {
		// TODO 07 study how you can test a method that is throwing an exception
		String login = "test";
		when(userRepository.getByLogin(login)).thenReturn(new User(1L, login, "whatever"));
		try {
			userService.authenticate(login, null);
			Assert.fail("User exists, but password is null, should have thrown an exception");
		} catch(IllegalArgumentException e) {
			// OK
		}
		verify(userRepository).getByLogin(login);
	}
	
	@Test public void getByLoginPassordKo() {
		String login = "test";
		when(userRepository.getByLogin(login)).thenReturn(new User(1L, login, "secret"));
		Assert.assertNull(userService.authenticate(login, "bad password"));
		verify(userRepository).getByLogin(login);
	}
	
	@Test public void getByLoginPassordOk() {
		String login = "test";
		when(userRepository.getByLogin(login)).thenReturn(new User(1L, login, "secret"));
		Assert.assertNotNull(userService.authenticate(login, "secret"));
		verify(userRepository).getByLogin(login);
	}
	
}
