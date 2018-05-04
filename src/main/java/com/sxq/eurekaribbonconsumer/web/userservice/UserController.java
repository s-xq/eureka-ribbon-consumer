package com.sxq.eurekaribbonconsumer.web.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public String getUser() {
//        return userService.getUser(new Random().nextInt(100)).toString();
        try {
            return userService.getUserAsync(new Random().nextInt(100)).get(1000, TimeUnit.MILLISECONDS).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
