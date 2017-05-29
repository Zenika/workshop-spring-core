package com.zenika.web;

import com.zenika.Application;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by acogoluegnes on 20/08/15.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@WebAppConfiguration
public class UserControllerIntegrationTest {

    @Autowired WebApplicationContext wac;

    MockMvc mvc;

    @Before public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    // TODO 12 Enlever @Ignore et lancer le test
    // Le test doit passer
    // Ce test est "out-of-container": il ne lance pas
    // le serveur d'applications, mais une version "bouchon"
    // de Spring MVC
    @Ignore
    @Test
    public void usersRest() throws Exception {
        mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // TODO 17 Enlever @Ignore et lancer le test
    // Le test doit passer
    // Ce test est "out-of-container": il ne lance pas
    // le serveur d'applications, mais une version "bouchon"
    // de Spring MVC
    @Ignore
    @Test
    public void usersHtml() throws Exception {
        mvc.perform(get("/users").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(xpath("//tbody/tr").nodeCount(2));
    }

}
