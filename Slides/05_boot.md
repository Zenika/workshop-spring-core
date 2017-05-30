# Workshop Spring Core<br><br><i class="fa fa-power-off" aria-hidden="true"></i>Spring Boot
<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Introduction](#/1)
*   [Le conteneur](#/2)
*   [Accès aux données](#/3)
*   [Le Web](#/4)
*   **[Spring Boot](#/5)**



## Spring Boot

*   But : rendre Spring plus accessible
*   Principes
    *   “Convention over configuration”
    *   Configuration automatique...
    *   Mais surchargeable...
    *   En fonction du classpath
*   Le moyen le plus simple d'utiliser Spring
*   Focus sur la configuration Java



## Comment commencer ?

*   Projet Maven ou Gradle
*   Ajouter des dépendances Spring Boot
*   [Spring Initializr](https://start.spring.io/)



## Dépendances "starters"

*   Ex. : Maven

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

*   Le parent positionne des propriétés Maven (ex. : numéros de versions)
*   Les dépendances importent automatiquement le nécessaire
    * ici pour le développement Web avec Spring (Spring MVC, Jackson, etc.)




## Lancer l'application

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
```

*   Ecrire une méthode main
*   `SpringApplication.run` démarre un conteneur web



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
-rw-rw-r-- 1 zenika zenika  18M août  21 11:11 spring-boot-solution-1.5.3.jar
-rw-rw-r-- 1 zenika zenika 148K août  21 11:11 spring-boot-solution-1.5.3.jar.original
```



## JAR exécutable

*   Le fichier jar embarque tout ce qui est nécessaire à l'exécution de l'application

```console
$ java -jar target/spring-boot-solution-1.5.3.jar
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::             (v1.5.3)

...

2016-08-21 11:17:00.340  INFO 9864 --- [main] Tomcat started on port(s): 8080 (http)
2016-08-21 11:17:00.348  INFO 9864 --- [main] Started Application in 7.31 seconds (JVM running for 7.845)
```

*   Le fichier `*.jar.original` contient seulement les classes du projets



## @SpringBootApplication

*   3 annotations en 1
    *   `@Configuration`
    *   `@ComponentScan` (les components sont scannés depuis la classe annotée)
    *   `@EnableAutoConfiguration`



## @SpringBootApplication

*   Configurer Spring dans Spring Boot ?
    *   Classes `@Configuration`
    *   Dans l'arborescence de Application
    *   Configurer comme d'habitude



## @EnableAutoConfiguration

*   Configuration automatique
*   Configuration
    *   Selon la présence de librairies sur le classpath
    *   Selon la présence de beans Spring
*   Exemples
    *   Spring MVC présent => DispatcherServlet déclarée
    *   H2 présent => DataSource embarqué déclaré
    *   Bean DataSource => JdbcTemplate déclaré



## Configuration externe

*   Fichiers application.properties
*   Localisations (ordre de précédence):
    *   /config depuis le répertoire courant
    *   Le répertoire courant
    *   /config sur le classpath
    *   Racine du classpath
*   Les propriétés définies en haut de la liste sont prioritaires sur celles du bas



## application.properties

*   Contient les paramètres de l'auto-configuration
*   Par ex., pour le conteneur web

```java
server.port=9000
server.address=192.168.1.20
server.session.timeout=1800 # en secondes
```



## application.yaml

*   La configuration peut également être écrit en YAML

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

*   Bonus : gestion des profils



## Configuration externe

*   Exemples de paramètres disponibles
    *   Connexion base de données
    *   Paramètres pool de connexions
    *   Log (niveau, format)
    *   Configuration SSL
    *   Initialisation de la base de données
    *   Etc.
*   Documentation de référence pour les paramètres disponibles (plus de 1000 !)



## Monitoring avec Actuator

*   Informations sur l'environnement
    *   Beans Spring, “health check”, etc
*   Exposés sur HTTP (“endpoints”)
*   Pour la production et le développement
    *   Indications utiles sur l'application

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



## /actuator

*   Accueil des endpoints

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

<i class="fa fa-exclamation-circle" aria-hidden="true"></i> La plupart des endpoints sont sécurisés ("sensitive") par défaut



## /health

*   Ping des systèmes externes

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

*   Les beans Spring
    *   Pratique pour débugguer le component scan, l'auto-configuration

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



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : spring-boot-start

*   Lancement d'une application Spring Boot
*   Contrôleur REST et HTML
*   Configuration d'une application Spring Boot
*   Utilisation de l'Actuator
