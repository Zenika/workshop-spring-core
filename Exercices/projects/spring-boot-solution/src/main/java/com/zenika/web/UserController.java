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

    @Autowired
    UserService userService;

    @RequestMapping(value="/users",method= RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("message","Hello Spring Boot!");
        model.addAttribute("users",userService.list());
        return "users";
    }

    @RequestMapping(value="/users",method= RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<User> users() {
        return userService.list();
    }

}
