/**
 * 
 */
package com.zenika.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zenika.domain.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
@Repository
public class HibernateUserRepository implements UserRepository {
	
	private final SessionFactory sessionFactory;
	
	public HibernateUserRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	/* (non-Javadoc)
	 * @see com.zenika.repository.UserRepository#getByLogin(java.lang.String)
	 */
	@Override
	public User getByLogin(String login) {
		return (User) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("login", login))
				.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.zenika.repository.UserRepository#create(java.lang.String, java.lang.String)
	 */
	@Override
	public User create(String login, String password) {
		User user = new User(null,login,password);
		sessionFactory.getCurrentSession().save(user);
		return user;
	}

	/* (non-Javadoc)
	 * @see com.zenika.repository.UserRepository#list()
	 */
	@Override
	public List<User> list() {
		return (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).list();
	}

}
