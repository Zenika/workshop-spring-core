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
		// TODO 01 écrire la requête pour sélectionner un User via son login
		// utiliser la méthode queryForObject, passer le rowMapper et le paramètre login
		// en cas de EmptyResultDataAccessException, retourner null
		return null;
	}
	
	@Override
	public User create(final String login, final String password) {
		// solution simple, ne permet pas de récupérer l'identifiant généré
		// tpl.update("insert into users (login,password) values (?,?)",login,password);

		// solution plus avancée pour pouvoir récupérer l'identifiant généré
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
			// TODO 02 créer une instance de User dans le RowMapper
			return null;
		}
		
	}

}
