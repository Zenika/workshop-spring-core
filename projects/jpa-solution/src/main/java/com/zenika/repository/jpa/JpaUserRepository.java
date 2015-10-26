/**
 * 
 */
package com.zenika.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zenika.domain.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
@Repository
@Transactional
public class JpaUserRepository implements UserRepository {
	
	@PersistenceContext
	private EntityManager entityManager; 
	

	/* (non-Javadoc)
	 * @see com.zenika.repository.UserRepository#getByLogin(java.lang.String)
	 */
	@Override
	public User getByLogin(String login) {
		return entityManager.createQuery("select u from User u where login = :login", User.class)
				.setParameter("login", login)
				.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see com.zenika.repository.UserRepository#create(java.lang.String, java.lang.String)
	 */
	@Override
	public User create(String login, String password) {
		User user = new User(null,login,password);
		entityManager.persist(user);
		return user;
	}

	/* (non-Javadoc)
	 * @see com.zenika.repository.UserRepository#list()
	 */
	@Override
	public List<User> list() {
		return entityManager.createQuery("select u from User u", User.class).getResultList();
	}
}
