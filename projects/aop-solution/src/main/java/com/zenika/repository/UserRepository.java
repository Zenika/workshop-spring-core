/**
 * 
 */
package com.zenika.repository;

import java.util.List;

import com.zenika.domain.User;

/**
 * @author acogoluegnes
 *
 */
public interface UserRepository {

	User getByLogin(String login);
	
	User create(String login,String password);
	
	List<User> list();
	
}
