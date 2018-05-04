package com.sxq.eurekaribbonconsumer.web.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public String getUser() {
        return userService.getUser(new Random().nextInt(100)).toString();
    }
}
