package com.sxq.eurekaribbonconsumer.web.userservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
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


    @HystrixCommand
    public Future<User> getUserAsync(int userId) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://HELLO-SERVICE/getUser?userId={1}", User.class, userId);
            }
        };
    }

    @HystrixCommand(fallbackMethod = "defaultUsers")
    public List<User> getUsers(int[] userIds) {
        ArrayList<Integer> userIdList = new ArrayList<>();
        for (int id : userIds) {
            userIdList.add(id);
        }
        String param = StringUtils.join(userIdList, ",");
        List<User> users = restTemplate.getForObject("Http://HELLO-SERVICE/getUsers?userIds={1}", List.class, param);
        mLogger.info(String.format("return %s", users.toString()));
        return users;
    }

    public User defaultUser(int userId) {
        return new User();
    }

    public List<User> defaultUsers(int[] userIds) {
        List<User> res = new ArrayList<>();
        for (int id : userIds) {
            res.add(new User());
        }
        return res;
    }

}
