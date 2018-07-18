/**
 * 
 */
package com.zenika;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zenika.domain.User;
import com.zenika.repository.UserRepository;
import com.zenika.repository.jpa.JpaUserRepository;

/**
 * @author acogoluegnes
 *
 */
@Configuration
@EnableTransactionManagement
public class TestConfiguration {
	
	// TODO 02 declare the l'EntityManagerFactory
	
	// TODO 03 declare the JpaUserRepository
	
	// TODO 04 declare the JpaTransactionManager
	
	
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
