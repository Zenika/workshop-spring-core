# Spring Core Workshop<br><br><i class="fa fa-database" aria-hidden="true"></i>Data access
<!-- .slide: class="page-title" -->



## Summary

<!-- .slide: class="toc" -->

*   [Introduction](#/1)
*   [Spring container](#/2)
*   **[Data access](#/3)**
*   [Web](#/4)
*   [Spring Boot](#/5)



## JDBC support

*   Central class: `JdbcTemplate`
    *   Interface: `JdbcOperations`
*   Abstraction over error-prone JDBC code
    *   Manage resources (Connection, Statement)
    *   Exception runtime (`DataAccessException`)
*   Methods
    *   queryForObject
    *   queryForMap / queryForList
*   Callbacks
    *   Map ResultSet



## JdbcTemplate

```java
JdbcOperations tpl = new JdbcTemplate(dataSource);
tpl.update(
  "insert into users (login,password) values (?,?)",
  login,password
);
```

*   Thread-safe après initialization



## Callback

*   Business code called at the right moment
*   Technical part handled by Spring

```java
RowMapper<User> rowMapper = new UserRowMapper();
User user = tpl.queryForObject(
  "select id,login,password from users where login = ?",
  rowMapper,
  login
);

()...)

private class UserRowMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new User(
      rs.getLong("id"),rs.getString("login"),rs.getString("password")
    );
  }
}
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : jdbc-start

*   Requests with JdbcTemplate
*   Testing with embedded database



## Transaction

*   Spring can make your POJOs transactional
*   Method calls are "wrapped" around a *begin* and a *commit* (or *rollback*)
*   Declarative transactions
*   Makes use of AOP



## Transactional POJO

*   With annotations (simpler) or XML

```java
@Transactional // All methods are transactional
public class UserServiceImpl implements UserService {

  //  Automatically transactional
  public User create(String login, String password) {
    (...)
  }

  // Overload
  @Transactional(readOnly=true)
  public User authenticate(String login, String password) {
    (...)
  }

}
```



## Default behvior

*   A new transaction is started at the beginning of method call
*   Or the existing transaction continues in case of nested calls
    *   Can specify different propagation levels
*   Commit if the method completes
*   Rollback when a `RuntimeException` is thrown



## Transactional options

*   Via annotation or XML
*   Propagation
*   Isolation
*   Timeout
*   When to rollback or not
*   Read-only



## Activate transactions

*   Use `@EnableTransactionManagement`
*   Define the `PlatformTransactionManager`

```java
@Configuration
@EnableTransactionManagement
public class InfraConfiguration {

	@Bean public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean public DataSource dataSource() {
      ...
	}

}
```



## PlatformTransactionManager

*   Transactio nmanagement abstraction
*   Implementation according to persistence layer
    *   DataSourceTransactionManager
    *   HibernateTransactionManager
    *   JpaTransactionManager
    *   JtaTransactionManager



## XML configuration

```xml
<bean id="transactionManager"
      class="o.s.jdbc.datasource.DataSourceTransactionManager">
  <property name="dataSource" ref="dataSource" />
</bean>

<tx:annotation-driven />
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : transaction-start

*   Configuration with `@Transactional`
*   Declaration of `PlatformTransactionManager`
*   Activate annotations
*   Integration test



## Hibernate

*   Spring offers Hibernate support
*   Resource management
*   Transactions management
*   Steps
    *   Configure *SessionFactory*
    *   Configure *HibernateTransactionManager*
    *   Injection of SessionFactory in DAOs
*   Be careful with Hibernate support classes
    *   *.hibernate4.* pour Hibernate 4
    *   *.hibernate5.* pour Hibernate 5



## DAO usage

*   No Spring dependency

```java
public class HibernateUserRepository implements UserRepository {

  private final SessionFactory sessionFactory;

  public HibernateUserRepository(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public User getByLogin(String login) {
    // Use the right method getCurrentSession()!
    return (User) sessionFactory.getCurrentSession()
      .createCriteria(User.class)
      .add(Restrictions.eq("login", login))
    .uniqueResult();
  }
}
```



## SessionFactory configuration

```java
@Bean LocalSessionFactoryBean sessionFactory() {
  LocalSessionFactoryBean localSessionFactoryBean =
    new LocalSessionFactoryBean();
  // Either list the @Entity classes
  // Or use "packagesToScan" to detect them
  // les classes @Entity
  localSessionFactoryBean.setAnnotatedClasses(User.class);
  localSessionFactoryBean.setDataSource(dataSource());
  Properties properties = new Properties();
  properties.setProperty("showSql", "true");
  localSessionFactoryBean.setHibernateProperties(properties);
  return localSessionFactoryBean;
}
```



## HibernateTransactionManager

```java
@Bean PlatformTransactionManager transactionManager(
                      SessionFactory sessionFactory) {
  return new HibernateTransactionManager(sessionFactory);
}
```
*   DAO must be called within an active transaction
*   Always the case in production
    *   Transaction services calling a DAO
*   Explicit transaction configuration for testing



## Exception management

*   `HibernateException`s
    *   Related to Hibernate API
*   In order to get `DataAccessException`s
    *   Annotate DAOs with `@Repository`
    *   Declare a `PersistenceExceptionTranslationPostProcessor`

```java
@Repository
public class HibernateUserRepository implements UserRepository {
  ...
}

@Bean BeanPostProcessor persistenceExceptionBeanPostProcessor() {
  return new PersistenceExceptionTranslationPostProcessor();
}
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : hibernate-start

*   Requests with Hibernate
*   SessionFactory configuration
*   Transaction configuration
*   Integration tests



## JPA

*   Spring offers JPA support
    *   Resource management
    *   Transaction management
*   Steps
    *   Configure `EntityManagerFactory`
    *   Configure `JpaTransactionManager`
    *   Inject `EntityManager` in DAOs



## DAO usage

*   No Spring dependency

```java
public class JpaUserRepository implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager; 	

	@Override
	public User getByLogin(String login) {
		return entityManager.createQuery(
                 "select u from User u where login = :login",
                 User.class)
				.setParameter("login", login)
				.getSingleResult();
	}

}
```



## EntityManagerFactory
```java
@Bean LocalContainerEntityManagerFactoryBean emf() {
  LocalContainerEntityManagerFactoryBean emfFactoryBean =
    new LocalContainerEntityManagerFactoryBean();

  emfFactoryBean.setDataSource(dataSource());
  emfFactoryBean.setPackagesToScan(
    User.class.getPackage().getName()
  );

  HibernateJpaVendorAdapter adapter =
    new HibernateJpaVendorAdapter();
  adapter.setShowSql(true);
  adapter.setDatabase(Database.H2);
  emfFactoryBean.setJpaVendorAdapter(adapter);

  return emfFactoryBean;
}
```



## JpaTransactionManager

```java
@Bean PlatformTransactionManager transactionManager(
                                 EntityManagerFactory emf) {
  return new JpaTransactionManager(emf);
}
```
*   DAO must be called within an active transaction
*   Always the case in production
    *   Transaction services calling a DAO
*   Explicit transaction configuration for testing


## Exception management

*   `PersistenceException`s are raised
    *   Uses JPA API
*   In order to get `DataAccessException`s
    *   Annotate DAOs with `@Repository`
    *   Declare a `PersistenceExceptionTranslationPostProcessor`

```java
@Repository
public class JpaUserRepository implements UserRepository {
  ...
}

@Bean BeanPostProcessor persistenceExceptionBeanPostProcessor() {
  return new PersistenceExceptionTranslationPostProcessor();
}
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : jpa-start

*   JPA requests
*   EntityManagerFactory configuration
*   Transaction configuration
*   Integration tests
