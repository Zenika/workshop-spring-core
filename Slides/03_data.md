# Workshop Spring Core<br><br><i class="fa fa-database" aria-hidden="true"></i>Accès aux données
<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Introduction](#/1)
*   [Le conteneur](#/2)
*   **[Accès aux données](#/3)**
*   [Le Web](#/4)
*   [Spring Boot](#/5)



## Support JDBC

*   Classe centrale : `JdbcTemplate`
    *   Interface : `JdbcOperations`
*   Abstraction de JDBC
    *   Gestion des ressources (connexions, Statement)
    *   Exception runtime (`DataAccessException`)
*   Méthodes directes
    *   queryForObject
*   Callback
    *   Pour des besoins plus précis



## JdbcTemplate

```java
JdbcOperations tpl = new JdbcTemplate(dataSource);
tpl.update(
  "insert into users (login,password) values (?,?)",
  login,password
);
```

*   Thread-safe après initialisation



## Callback

*   Code métier appelé au bon moment
*   Partie technique gérée par Spring

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

*   Requête avec le JdbcTemplate
*   Test JUnit sur une BD embarquée



## Transaction

*   Spring permet de rendre des POJO transactionnels
*   Les appels de méthodes sont “entourés” d'un begin puis d'un commit (ou rollback)
*   On parle de transaction déclarative
*   Utilisation de l'AOP



## Un POJO transactionnel

*   Par annotation ou XML
    *   Annotation = le plus simple

```java
@Transactional // Toutes les méthodes sont transactionnelles
public class UserServiceImpl implements UserService {

  //  Automatiquement transactionnelle
  public User create(String login, String password) {
    (...)
  }

  // Surcharge
  @Transactional(readOnly=true)
  public User authenticate(String login, String password) {
    (...)
  }

}
```



## Comportement par défaut

*   Une transaction est commencée au début de la méthode
*   La transaction continue pour les appels imbriqués
    *   Si POJO transactionnels, selon le niveau de propagation
*   Commit si la méthode se finit normalement
*   Rollback si une `RuntimeException` est lancée



## Attributs transactionnels

*   L'annotation ou le XML permettent de régler les attributs transactionnels
*   Propagation
*   Isolation
*   Timeout
*   Quand faire ou ne pas faire un rollback
*   Read-only



## Activer les transactions

*   Utiliser `@EnableTransactionManagement`
*   Définir le `PlatformTransactionManager`

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

*   Abstraction de gestion de transactions
*   Implémentations selon la couche de persistance
    *   DataSourceTransactionManager
    *   HibernateTransactionManager
    *   JpaTransactionManager
    *   JtaTransactionManager



## Activation en XML

```xml
<bean id="transactionManager"
      class="o.s.jdbc.datasource.DataSourceTransactionManager">
  <property name="dataSource" ref="dataSource" />
</bean>

<tx:annotation-driven />
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : transaction-start

*   Configuration avec `@Transactional`
*   Déclaration d'un `PlatformTransactionManager`
*   Activation des annotations
*   Test d'intégration



## Hibernate

*   Spring propose un support Hibernate
*   Gestion des ressources
*   Gestion des transactions
*   Etapes
    *   Configuration de la SessionFactory
    *   Configuration du HibernateTransactionManager
    *   Injection de la SessionFactory dans les DAO
*   Attention aux classes de support utilisées
    *   *.hibernate4.* pour Hibernate 4
    *   *.hibernate5.* pour Hibernate 5



## Utilisation dans une DAO

*   Pas de dépendance vers Spring

```java
public class HibernateUserRepository implements UserRepository {

  private final SessionFactory sessionFactory;

  public HibernateUserRepository(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public User getByLogin(String login) {
    // Bien utiliser la méthode getCurrentSession() !
    return (User) sessionFactory.getCurrentSession()
      .createCriteria(User.class)
      .add(Restrictions.eq("login", login))
    .uniqueResult();
  }
}
```



## Configuration SessionFactory

```java
@Bean LocalSessionFactoryBean sessionFactory() {
  LocalSessionFactoryBean localSessionFactoryBean =
    new LocalSessionFactoryBean();
  // Lister les classes une par une ou
  // utiliser "packagesToScan" pour détecter
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
*   Les DAO doivent être appelés avec une transaction active
*   Toujours le cas en production
    *   Services transactionnels appelant un DAO
*   Configuration de transaction explicite pour les tests



## Gestion des exceptions

*   Des HibernateException remontent
    *   Utilisation de l'API Hibernate
*   Pour avoir des `DataAccessException`
    *   Apposer `@Repository` sur les DAO
    *   Déclarer un `PersistenceExceptionTranslationPostProcessor`


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

*   Requête avec Hibernate
*   Configuration SessionFactory
*   Configuration des transactions
*   Test d'intégration



## JPA

*   Spring propose un support JPA
    *   Gestion des ressources
    *   Gestion des transactions
*   Etapes
    *   Configuration du EntityManagerFactory
    *   Configuration du JpaTransactionManager
    *   Injection du EntityManager dans les DAO



## Utilisation dans un DAO

*   Pas de dépendance vers Spring

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
*   Les DAO doivent être appelés avec une transaction active
*   Toujours le cas en production
    *   Services transactionnels appelant un DAO
*   Configuration de transaction explicite pour les tests



## Gestion des exceptions

*   Des PersistenceException remontent
    *   Utilisation de l'API JPA
*   Pour avoir des `DataAccessException`
    *   Apposer `@Repository` sur les DAO
    *   Déclarer un `PersistenceExceptionTranslationPostProcessor`

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

*   Requête avec JPA
*   Configuration EntityManagerFactory
*   Configuration des transactions
*   Test d'intégration
