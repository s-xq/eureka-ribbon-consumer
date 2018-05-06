package com.sxq.eurekaribbonconsumer.web.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public User getUser() {
//        return userService.getUser(new Random().nextInt(100)).toString();
        try {
            return userService.getUserAsync(new Random().nextInt(100)).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "getUsers", method = RequestMethod.GET)
    public List<User> getUsers() {
        Random rand = new Random(17);
        int[] ids = new int[10];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = rand.nextInt(100);
            System.out.println(String.valueOf(ids[i]));
        }
        System.out.println(ids);
        return userService.getUsers(ids);
    }


}
