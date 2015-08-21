package com.zenika.web;

import com.zenika.business.UserService;
import com.zenika.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by acogoluegnes on 20/08/15.
 */
@Controller
public class UserController {

    // TODO 09 Injecter le UserService avec @Autowired

    // TODO 10 Ecrire une méthode REST retournant les utilisateurs

    // TODO 11 Configurer Spring Boot pour initialiser la base de données
    // Sans cette initialisation, les utilisateurs ne seront pas
    // dans la base
    // Ouvrir le fichier src/main/resources/application.properties
    // Ajouter les entrées de configuration suivantes (sans les // devant)
    // spring.datasource.schema=classpath:/create-tables.sql
    // spring.datasource.data=classpath:/insert-data.sql

    // TODO 12 Lancer l'application et aller sur http://localhost:8080/users
    // Un document JSON avec les utilisateurs doit s'afficher

    // TODO 13 Arreter l'application

    // TODO 14 Ecrire une méthode pour afficher un message et les utilisateurs


    // TODO 15 Ouvrir le fichier src/main/resources/templates/users.ftl
    // Il s'agit du template qui va etre appelé après la méthode précédemment écrite.
    // Spring Boot configure automatiquement le moteur de template pour qu'il
    // recherche les templates dans le répertoire /src/main/resources/templates.
    // Il n'y a rien à écrire dans users.ftl, il suffit de lire les commentaires
    // qui expliquent les différents traitements.

    // TODO 16 Lancer l'application et aller sur http://localhost:8080/users
    // La page HTML des utilisateurs doit s'afficher
}
