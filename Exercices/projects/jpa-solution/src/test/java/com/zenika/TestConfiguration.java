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
	
	@Bean UserRepository userRepository() {
		return new JpaUserRepository();
	}

	@Bean LocalContainerEntityManagerFactoryBean emf() {
		LocalContainerEntityManagerFactoryBean emfFactoryBean = new LocalContainerEntityManagerFactoryBean();
		emfFactoryBean.setDataSource(dataSource());
		emfFactoryBean.setPackagesToScan(User.class.getPackage().getName());
		
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setDatabase(Database.H2);
		emfFactoryBean.setJpaVendorAdapter(adapter);
		
		return emfFactoryBean;
	}
	
	@Bean PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
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
