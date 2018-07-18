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
	
	@Bean UserRepository userRepository(SessionFactory sessionFactory) {
		return new HibernateUserRepository(sessionFactory);
	}

	@Bean LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setAnnotatedClasses(User.class);
		localSessionFactoryBean.setDataSource(dataSource());
		Properties properties = new Properties();
		properties.setProperty("showSql", "true");
		localSessionFactoryBean.setHibernateProperties(properties);
		return localSessionFactoryBean;
	}
	
	@Bean PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
	
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
