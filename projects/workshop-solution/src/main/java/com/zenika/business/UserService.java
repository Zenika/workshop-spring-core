/**
 * 
 */
package com.zenika.business;

import java.util.List;

import com.zenika.domain.User;

/**
 * @author acogoluegnes
 *
 */
public interface UserService {

	User authenticate(String login,String password);
	
	User create(String login,String password);
	
	List<User> list();
	
}
