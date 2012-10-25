/**
 * 
 */
package com.zenika.business.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenika.business.UserService;
import com.zenika.domain.User;
import com.zenika.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Value("${digest}")
	private String digest;
	
	private Encoder encoder;
	
	/* (non-Javadoc)
	 * @see com.zenika.business.UserService#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public User authenticate(String login, String password) {
		User user = userRepository.getByLogin(login);
		if(user == null) {
			return null;
		} else {
			if(password == null) {
				throw new IllegalArgumentException("Le mot de passe ne peut être nul !");
			}
			password = encoder.encode(password);
			if(password.equals(user.getPassword())) {
				return user;
			} else {
				return null;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.zenika.business.UserService#create(java.lang.String, java.lang.String)
	 */
	@Override
	public User create(String login, String password) {
		User user = userRepository.getByLogin(login);
		if(user == null) {
			user = userRepository.create(login, encoder.encode(password));
		} else {
			throw new IllegalArgumentException("Un utilisateur avec ce login existe déjà");
		}
		return user;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zenika.business.UserService#list()
	 */
	@Override
	public List<User> list() {
		return userRepository.list();
	}
	
	@PostConstruct
	public void init() {
		if(digest == null || digest.trim().length() == 0) {
			LOGGER.info("Pas de hachage pour les mots de passe");
			encoder = new Encoder() {
				@Override
				public String encode(String input) {
					return input;
				}
			};
		} else {
			LOGGER.info("Utilisation de l'algorithme {} pour le hachage des mots de passe",digest);
			encoder = new MessageDigestEncoder(digest);
		}
	}
	
	private interface Encoder {
		
		String encode(String input);
		
	}
	
	private class MessageDigestEncoder implements Encoder {
		
		private final String algorithm;
		
		public MessageDigestEncoder(String algorithm) {
			this.algorithm = algorithm;
		}
		
		@Override
		public String encode(String input) {
			byte[] bytes = StringUtils.getBytesUtf8(input);
			return Hex.encodeHexString(DigestUtils.getDigest(algorithm).digest(bytes));
		}
	}
	
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void setDigest(String digest) {
		this.digest = digest;
	}
	
}
