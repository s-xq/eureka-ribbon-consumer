package com.sxq.eurekaribbonconsumer.web.userservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;
import java.util.logging.Logger;


@Service
public class UserService {
    private Logger mLogger = Logger.getLogger(getClass().getName());

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "defaultUser")
    public User getUser(int userId) {
        User user = restTemplate.getForObject("http://HELLO-SERVICE/getUser?userId={1}", User.class, userId);
        mLogger.info(String.format("return %s", user.toString()));
        return user;
    }

    @HystrixCommand(fallbackMethod = "defaultUserAsync")
    public Future<User> getUserAsync(int userId) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://HELLO-SERVICE/getUser?userId={1}", User.class, userId);
            }
        };
    }

    public User defaultUser(int userId) {
        return new User();
    }


    public Future<User> defaultUserAsync(int userId) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return new User();
            }
        };
    }
}
