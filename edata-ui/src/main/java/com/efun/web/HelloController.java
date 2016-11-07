package com.efun.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kinven on 16-11-3.
 */
@RestController
public class HelloController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/redis/hello")
    public String redisHello(){
        return stringRedisTemplate.opsForValue().get("hello");
    }

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}
