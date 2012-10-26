/**
 * 
 */
package com.zenika.repository.jdbc;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zenika.domain.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
// TODO 03 supprimer @Ignore
@Ignore
public class JdbcUserRepositoryTest {
	
	private UserRepository userRepository;
	
	private EmbeddedDatabase dataSource;
	
	@Before public void setUp() {
		// TODO 04 analyser l'initialisation de la base de données mémoire
		// elle utilise une classe utilitaire de Spring
		dataSource = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("classpath:/create-tables.sql")
			.addScript("classpath:/insert-data.sql")
			.build();
		// TODO 05 instancier le JdbcUserRepository
		
	}
	
	@After public void tearDown() {
		dataSource.shutdown();
	}
	
	@Test public void getByLoginUserExists() {
		// TODO 06 tester getByLogin avec un utilisateur qui existe (acogoluegnes)
		// s'assurer que le User retourné a bien ce login
	}
	
	@Test public void getByLoginUserDoesNotExist() {
		// TODO 07 tester getByLogin avec un utilisateur qui n'existe pas
		// s'assurer que la méthode retourne null
		
		// TODO 08 lancer le test
	}
	
	@Test public void create() {
		JdbcOperations tpl = new JdbcTemplate(dataSource);;
		int initialCount = tpl.queryForInt("select count(1) from users");
		userRepository.create("mmouse", "password");
		Assert.assertEquals(initialCount+1,tpl.queryForInt("select count(1) from users"));
	}
	
	
	
}
