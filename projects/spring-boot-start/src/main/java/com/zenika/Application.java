package com.zenika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by acogoluegnes on 20/08/15.
 */

// TODO 01 ajouter l'annotation faisant de cette classe une application Spring Boot

public class Application {

    public static void main(String[] args) {
        // TODO 02 Ajouter le code pour démarrer l'application
        SpringApplication.run(Application.class);

        // TODO 03 Lancer l'application et aller sur http://localhost:8080/actuator
        // un document JSON doit s'afficher si tout fonctionne correctement

        // TODO 04 Aller sur http://localhost:8080/beans
        // S'assurer que les beans userServiceImpl et jdbcUserRepository
        // sont bien dans la liste.
        // Comment se fait-il qu'ils ont été automatiquement créés ?

        // TODO 05 Arreter l'application
    }

    // TODO 06 Déclarer un controleur REST "Hello world"
    // Rappels :
    //  - utiliser une classe statique interne
    //  - utiliser les annotations @RestController et @RequestMapping
    //  - mapper sur l'URL /hello

    // TODO 07 Lancer l'application et aller sur http://localhost:8080/hello
    // Le message doit s'afficher

    // TODO 08 Arreter l'application


}
