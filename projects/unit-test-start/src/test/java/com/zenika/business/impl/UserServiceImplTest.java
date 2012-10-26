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
// TODO 02 supprimer @Ignore
@Ignore
public class UserServiceImplTest {

	private UserRepository userRepository;
	
	private UserServiceImpl userService;
	
	@Before public void setUp() {
		// TODO 03 initialiser le mock
		// le mock est ensuite injecté dans le service
		
		userService = new UserServiceImpl();
		userService.setUserRepository(userRepository);
		userService.init();
	}
	
	@Test public void getByLoginNoUser() {
		String login = "test";
		// TODO 04 programmer le mock pour qu'il retourne null sur l'appel de getByLogin

		// TODO 05 s'assurer que la méthode UserService.authenticate retourne null
		
		// on lance la vérification sur les appels au mock
		verify(userRepository).getByLogin(login);
	}
	
	@Test public void getByLoginNullEmptyPassword() {
		// TODO 07 regarder comment tester une méthode qui lance une exception
		String login = "test";
		when(userRepository.getByLogin(login)).thenReturn(new User(1L, login, "whatever"));
		try {
			userService.authenticate(login, null);
			Assert.fail("Utilisateur existant, mais mot de passe entré nul, une exception aurait du être lancée");
		} catch(IllegalArgumentException e) {
			// OK
		}
		verify(userRepository).getByLogin(login);
		
		// TODO 08 lancer le test
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
