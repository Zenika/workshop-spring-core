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

    // TODO 09 Inject UserService with @Autowired

    // TODO 10 Write a REST method returning the list of users

    // TODO 11 Configure Spring Boot to initialize the database
    // Without the initialization, users would not be loaded
    // in the database
    // Open application.properties (project root)
    // Add the following entries:
    // spring.datasource.schema=classpath:/create-tables.sql
    // spring.datasource.data=classpath:/insert-data.sql

    // TODO 12 Start, and go to http://localhost:8080/users
    // The list of users should be displayed a JSON content

    // TODO 13 Stop the application

    // TODO 14 Write a method to display a message to users


    // TODO 15 Open src/main/resources/templates/users.ftl
    // It is the template which will be used by the last method
    // Spring Boot automatically configure the template engine so that
    // it searches templates within /src/main/resources/templates
    // There is nothing to do in users.ftl, just read the comments
    // that explain the different blocks.

    // TODO 16 Start the application, and go to http://localhost:8080/users
    // The HTML page with the list of users should be displayed
}
