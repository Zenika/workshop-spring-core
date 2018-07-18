/**
 * 
 */
package com.zenika.business.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zenika.domain.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
public class UserServiceImplTest {

	private UserRepository userRepository;
	
	private UserServiceImpl userService;
	
	@Before public void setUp() {
		userRepository = mock(UserRepository.class);
		userService = new UserServiceImpl();
		userService.setUserRepository(userRepository);
		userService.init();
	}
	
	@Test public void getByLoginNoUser() {
		String login = "test";
		when(userRepository.getByLogin(login)).thenReturn(null);
		Assert.assertNull(userService.authenticate(login, ""));
		verify(userRepository).getByLogin(login);
	}
	
	@Test public void getByLoginNullEmptyPassword() {
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
