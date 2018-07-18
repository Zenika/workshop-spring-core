# Spring Core Workshop<br><br><i class="fa fa-globe" aria-hidden="true"></i>Web
<!-- .slide: class="page-title" -->



## Summary

<!-- .slide: class="toc" -->

*   [Introduction](#/1)
*   [Spring container](#/2)
*   [Data access](#/3)
*   **[Web](#/4)**
*   [Spring Boot](#/5)



## Spring et le Web

*   Typical application
    *   Backend with Spring (service + DAO)
    *   Web framework for UI
        *   Spring MVC, Thymeleaf, Wicket, JSF, etc.
*   How to start Spring in a Web application?



## ContextLoaderListener

*   In web.xml

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

*   contextConfigLocation is optional
    *   Defaukt: /WEB-INF/applicationContext.xml
    *   Can specify multiple configuration files (comma-separated, or new line)



## Access to Spring context

*   "Root application context"
*   Access it, i.e. in a Servlet:

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



## Externalisation

*   Often required to externalize configuration
*   Principle:
    *   Properties file managed by Production team
    *   Reference this file in Spring
    *   Syntax: `${propertyName}` inside configuration



## Externalisation

*   Spring configuration

```xml
<!-- do NOT use thus DataSource in production ! -->
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

*   Property file

```
db.driver=org.h2.Driver
db.url=jdbc:h2:tcp://localhost/mem:spring_workshop
db.username=sa
db.password=

```



## Profiles

*   Define groups of Spring beans
*   Active them during the creation of Spring application context



## Profile definition

*   XML configuration

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



## Profile definition

*   Java configuration

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



## Profile activation

*   Command line
```bash
-Dspring.profiles.active=in-memory-db
```

*   Programmatically
```java
System.setProperty("spring.profiles.active", "in-memory-db");
```

*   In test classes only
```java
@ActiveProfiles("in-memory-db")
public class InMemoryDbTest {
  (...)
}
```



## Profiles

*   Accessible from `Environment`
  *   Injected with @Autowired
*   Java configuration
  *   Can test values of active profiles
  *   And configure/instanciate object graphs accordingly
  *   ex. : profile “standalone”, “app-server”, “prod”, etc.



## Profiles

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

*   ContextLoaderListener configuration
*   Externalisation with PropertyPlaceholder
*   Profile definition and activation
