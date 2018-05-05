package com.sxq.eurekaribbonconsumer.web.userservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
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

    //    @CacheResult(cacheKeyMethod = "getUserCacheKey")
    @HystrixCommand(fallbackMethod = "defaultUser")
    public User getUser(int userId) {
        User user = restTemplate.getForObject("http://HELLO-SERVICE/getUser?userId={1}", User.class, userId);
        mLogger.info(String.format("return %s", user.toString()));
        return user;
    }


    /**
     * 使用Hystrix缓存需要初始化{@link HystrixRequestContext}，否则无法使用缓存的功能。
     * 并且{@link HystrixRequestContext}可以有多个，但是彼此之间不共享缓存，调用{@link HystrixRequestContext#shutdown()}将会清除该上下文对应的缓存。
     * <p>
     * 使用方法：
     * https://github.com/Netflix/Hystrix/wiki/How-To-Use#Caching
     */
    //    @CacheResult(cacheKeyMethod = "getUserCacheKey")
    @HystrixCommand
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

//    public String getUserCacheKey(int userId){
//        return String.valueOf(userId);
//    }
}
