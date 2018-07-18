/**
 * 
 */
package com.zenika;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zenika.domain.User;
import com.zenika.repository.UserRepository;
import com.zenika.repository.hibernate.HibernateUserRepository;

/**
 * @author acogoluegnes
 *
 */
@Configuration
@EnableTransactionManagement
public class TestConfiguration {
	
    // TODO 02 declare the SessionFactory
	
	// TODO 03 declare the HibernateUserRepository
	
	// TODO 04 declare the HibernateTransactionManager
	
	
	@Bean BeanPostProcessor persistenceExceptionBeanPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
		.setType(EmbeddedDatabaseType.H2)
		.addScript("classpath:/create-tables.sql")
		.addScript("classpath:/insert-data.sql")
		.build();
	}
	
}
