# Workshop Spring Core<br><br><i class="fa fa-globe" aria-hidden="true"></i>Le Web
<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Introduction](#/1)
*   [Le conteneur](#/2)
*   [Accès aux données](#/3)
*   **[Le Web](#/4)**
*   [Spring Boot](#/5)



## Spring et le Web

*   Application typique
    *   Spring pour le backend (service + DAO)
    *   Framework Web pour l'UI
        *   Spring MVC, Thymeleaf, Wicket, JSF, etc.
*   Comment démarrer Spring dans une application Web ?



## ContextLoaderListener

*   Dans le fichier web.xml

```xml
<context-param>
    	<param-name>contextConfigLocation</param-name>
    	<param-value>
    	/WEB-INF/applicationContext.xml
    	</param-value>
</context-param>

<listener>
    	<listener-class>
         org.springframework.web.context.ContextLoaderListener
       </listener-class>
</listener>
```

*   contextConfigLocation est optionnel
    *   Par défaut: /WEB-INF/applicationContext.xml
    *   On peut indiquer plusieurs fichiers de configuration (virgules ou retours chariot)



## Accès au contexte Spring

*   On parle du "root application context"
*   Utilitaire pour y accéder, ex. dans une Servlet :

```java
public class ListUsersServlet extends HttpServlet {

  private UserService userService;

  @Override
  public void init() throws ServletException {
    ApplicationContext ctx =
      WebApplicationContextUtils.getRequiredWebApplicationContext(
        getServletContext()
    );
    userService = ctx.getBean(UserService.class);
  }
  (...)
}
```



## Frameworks Web

*   La plupart des frameworks Web s'intègrent avec Spring
*   Marche à suivre :
    *   Configuration de l'intégration
    *   Ajout d'une propriété dans un contrôleur
    *   Annoter la propriété avec l'annotation appropriée
        *   ex. : `@SpringBean` pour Wicket



## Externalisation

*   Souvent nécessaire d'externaliser la configuration
*   Principe :
    *   Fichier properties géré par l'équipe de prod
    *   Faire référence à ce fichier dans Spring
    *   Syntaxe `${propertyName}` dans la configuration



## Externalisation

*   Configuration Spring

```xml
<!-- ne surtout PAS utiliser ce DataSource en production ! -->
<bean id="dataSource" class="o.s.jdbc.datasource.SingleConnectionDataSource">
  <property name="driverClassName" value="${db.driver}" />
  <property name="url" value="${db.url}" />
  <property name="username" value="${db.username}" />
  <property name="password" value="${db.password}" />
  <property name="suppressClose" value="true" />
</bean>

<context:property-placeholder
  location="file:./application-configuration.properties"/>

```

*   Fichier properties

```
db.driver=org.h2.Driver
db.url=jdbc:h2:tcp://localhost/mem:spring_workshop
db.username=sa
db.password=

```



## Profils

*   Définir des groupes de beans
*   Activer leur prise en compte à la création du contexte Spring



## Profils : définition

*   Configuration XML

```xml
<beans profile="in-memory-db">
  <jdbc:embedded-database id="dataSource" type="H2">
    <jdbc:script location="classpath:/create-tables.sql" />
    <jdbc:script location="classpath:/insert-data.sql" />
  </jdbc:embedded-database>
</beans>

<beans profile="standalone-db">
  (...)
</beans>
```



## Profils : définition

*   Configuration Java

```java
@Configuration
@Profile("in-memory-db")
public class InMemoryDbConfiguration {

  @Bean
  public DataSource dataSource() {
      return new EmbeddedDatabaseBuilder()
          .setType(EmbeddedDatabaseType.H2)
          .addScript("classpath:/create-tables.sql")
          .addScript("classpath:/insert-data.sql")
          .build();
  }

}

@Configuration
@Profile("standalone-db")
public class StandaloneDbConfiguration {
  (...)
}
```



## Activation des profils

*   Ligne de commande
```bash
-Dspring.profiles.active=in-memory-db
```

*   Programmatiquement
```java
System.setProperty("spring.profiles.active", "in-memory-db");
```

*   Classes de test
```java
@ActiveProfiles("in-memory-db")
public class InMemoryDbTest {
  (...)
}
```



## Profils

*   Profils accessibles depuis Environment
  *   Lui-même injecté avec @Autowired
*   En configuration Java
  *   Permet de tester des valeurs de profils
  *   Et d'instancier des grappes d'objets en conséquences
  *   ex. : profil “standalone”, “app-server”, “prod”, etc.



## Profils

```java
@Configuration
public class ApplicationConfiguration {

	@Autowired Environment environment;

	@Bean public ObjectMapper jsonMapper() {
		ObjectMapper mapper = new ObjectMapper();
		if(environment.acceptProfiles("debug")) {
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
		return mapper;
	}

}
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : web-start

*   Configuration du ContextLoaderListener
*   Externalisation avec PropertyPlaceholder
*   Définition et activation de profils
