/**
 * 
 */
package com.zenika.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author acogoluegnes
 *
 */
@Entity
@Table(name="users")
@SequenceGenerator(name="users_seq",sequenceName="users_seq",allocationSize=1)
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="users_seq")
	private Long id;
	
	private String login;
	
	private String password;

	public User() {}
	
	public User(Long id, String login, String password) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
