# Spring Core Workshop<br><br><i class="fa fa-power-off" aria-hidden="true"></i>Spring Boot
<!-- .slide: class="page-title" -->



## Summary

<!-- .slide: class="toc" -->

*   [Introduction](#/1)
*   [Spring container](#/2)
*   [Data access](#/3)
*   [Web](#/4)
*   **[Spring Boot](#/5)**



## Spring Boot

*   Purpose: make Spring more accessible
*   Principles
    *   “Convention over configuration”
    *   Automatic configuration...
    *   ...but override it...
    *   ...depending on classpath
*   Easiest way to use Spring
*   Focus on Java configuration



## How to start?

*   Maven / Gradle project
*   Add Spring Boot dependencies
*   [Spring Initializr](https://start.spring.io/)



## "Starters" dependencies

*   Example with Maven

```xml
<project>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.3.RELEASE</version>
    </parent>
    (...)
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

*   Parent POM defines Maven properties (example: version numbers)
*   Dependencies automatically import all which is required
    * in this example for Web development with Spring (Spring MVC, Jackson, etc.)




## Start application

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
```

*   Write main() method
*   `SpringApplication.run` starts a Web container



## Packaging

*   pom.xml

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
         </plugin>
    </plugins>
</build>
```

```bash
$ mvn clean package
...
$ ls -alh target/
total 18M
(...)
-rw-rw-r-- 1 zenika zenika  18M août  21 11:11 spring-boot-solution-2.0.3.jar
-rw-rw-r-- 1 zenika zenika 148K août  21 11:11 spring-boot-solution-2.0.3.jar.original
```



## Executable JAR

*   The jar file contains everything required for the application

```console
$ java -jar target/spring-boot-solution-2.0.3.jar
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::             (v2.0.3)

...

2018-07-21 11:17:00.340  INFO 9864 --- [main] Tomcat started on port(s): 8080 (http)
2018-07-21 11:17:00.348  INFO 9864 --- [main] Started Application in 7.31 seconds (JVM running for 7.845)
```

*   `*.jar.original` only contains application classes



## @SpringBootApplication

*   3 annotations in 1
    *   `@Configuration`
    *   `@ComponentScan` (components are scanned from the annotated class)
    *   `@EnableAutoConfiguration`



## @SpringBootApplication

*   Configure Spring in Spring Boot?
    *   `@Configuration` classes in the application
    *   Same as usual



## @EnableAutoConfiguration

*   Automatic configuration
*   Conditional configuration
    *   If a library is found (or missing) in the classpath
    *   If Spring beans are found/missing
*   Examples
    *   Spring MVC detected => DispatcherServlet declared
    *   H2 detected => embedded DataSource declared
    *   DataSource bean => JdbcTemplate declared



## External configuration

*   application.properties files
*   Order:
    *   /config from current folder
    *   Current folder
    *   /config on classpath
    *   Classpath root
*   Properties on top of the list have priority on the others



## application.properties

*   Properties for auto-configuration
*   For example, the Web container

```java
server.port=9000
server.address=192.168.1.20
server.session.timeout=1800 # seconds
```



## application.yaml

*   Alternative format: YAML

```yaml
server:
    address: 192.168.1.100
---
spring:
    profiles: development
server:
    address: 127.0.0.1
---
spring:
    profiles: production
server:
    address: 192.168.1.120
```

*   Bonus: profile management



## External configuration

*   Examples of available options
    *   Database connection
    *   Connection pool configuration
    *   Logging (levels, format)
    *   SSL Configuration
    *   Database initialization scripts
    *   Etc.
*   Refer to official documentation for all available options (more than 1000!)



## Monitoring with Actuator

*   Informations on the environnement
    *   Spring beans, health checks, etc.
*   Exposed via HTTP endpoints
*   For both production and development
    *   Useful metrics on the application

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



## /actuator

*   Home page for endpoints

```json
{
  _links: {
    self: {
      href: "http://localhost:8080/actuator",
      templated: false
    },
    autoconfig: {
      href: "http://localhost:8080/autoconfig",
      templated: false
    },
    beans: {
      href: "http://localhost:8080/beans",
      templated: false
    },
    ...
  }
}
```

<i class="fa fa-exclamation-circle" aria-hidden="true"></i> Most endpoints are secured by default



## /health

*   Ping external systems

```json
{
  status: "UP",
  diskSpace: {
    status: "UP",
    total: 243905724416,
    free: 110182084608,
    threshold: 10485760
  },
  db: {
    status: "UP",
    database: "H2",
    hello: 1
  },
  ...
}
```



## /beans

*   Spring beans
    *   Useful for debugging the component scan and auto-configuration

```json
{
  content: [
    {
      context: "application",
      parent: null,
      beans: [
        {
           bean: "application",
           scope: "singleton",
           type: "com.zenika.Application$$EnhancerBySpringCGLIB$$faaf2c13",
           resource: "null",
           dependencies: [ ]
        },
        ...
      ]
    }
  ]
}
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> Labs: spring-boot-start

*   Launch Spring Boot application
*   REST and HTML controller
*   Spring Boot application configuration
*   Using actuator
