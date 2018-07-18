/**
 * 
 */
package com.zenika.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.zenika.domain.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
public class JdbcUserRepository implements UserRepository {
	
	private final JdbcOperations tpl;
	
	private RowMapper<User> rowMapper = new UserRowMapper();
	
	public JdbcUserRepository(DataSource dataSource) {
		this.tpl = new JdbcTemplate(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * @see com.zenika.repository.UserRepository#getByLogin(java.lang.String)
	 */
	@Override
	public User getByLogin(String login) {
		// TODO 01 write the request to retrieve a user from its login
		// use queryForObject(), with the RowMapper and login parameter
		// in case of EmptyResultDataAccessException, return null
		return null;
	}
	
	@Override
	public User create(final String login, final String password) {
		// simple solution, cannot retrieve the generated identifier
		// tpl.update("insert into users (login,password) values (?,?)",login,password);

		// advanced solution, can retrieve the generated identifier
		KeyHolder keyHolder = new GeneratedKeyHolder();
		tpl.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement stmt = con.prepareStatement("insert into users (login,password) values (?,?)");
				stmt.setString(1, login);
				stmt.setString(2, password);
				return stmt;
			}
		}, keyHolder);
		return new User(keyHolder.getKey().longValue(),login,password);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zenika.repository.UserRepository#list()
	 */
	@Override
	public List<User> list() {
		return tpl.query("select id,login,password from users order by login",rowMapper);
	}
	
	private static class UserRowMapper implements RowMapper<User> {
		
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO 02 create a User instance in the RowMapper
			return null;
		}
		
	}

}
