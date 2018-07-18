package com.zenika;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 */

/**
 * @author acogoluegnes
 *
 */
@Configuration
//TODO 05 enable detection of @Transactional
public class TransactionConfiguration {

	@Autowired DataSource dataSource;
	
	// TODO 04 declare the right PlatformTransactionManager
	
	
}
