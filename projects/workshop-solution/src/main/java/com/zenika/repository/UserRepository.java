/**
 * 
 */
package com.zenika.repository;

import com.zenika.domain.User;

/**
 * @author acogoluegnes
 *
 */
public interface UserRepository {

	User getByLogin(String login);
	
	User create(String login,String password);
	
}
