package com.zenika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by acogoluegnes on 20/08/15.
 */

// TODO 01 add the right annotation to make this class a Spring Boot application

public class Application {

    public static void main(String[] args) {
        // TODO 02 add the code to start the application
        

        // TODO 03 start the application, and go to http://localhost:8080/actuator
        // if everything works ok, a JSON content should be displayed

        // TODO 04 go to http://localhost:8080/beans
        // Make sure that userServiceImpl and jdbcUserRepository
        // are in the ist
        // How come they have been automatically created?

        // TODO 05 stop the application
    }

    // TODO 06 declare a "Hello world" REST controller
    // Reminder:
    //  - use a static inner class
    //  - use  @RestController and @RequestMapping annotations
    //  - go to URL /hello

    // TODO 07 start the application and go to http://localhost:8080/hello
    // it should show the message

    // TODO 08 stop the application


}
