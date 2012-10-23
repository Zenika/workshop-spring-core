/**
 * 
 */
package com.zenika.business;

import com.zenika.domain.User;

/**
 * @author acogoluegnes
 *
 */
public interface UserService {

	User authenticate(String login,String password);
	
	User create(String login,String password);
	
}
