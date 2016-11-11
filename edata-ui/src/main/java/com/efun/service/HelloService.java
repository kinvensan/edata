package com.efun.service;

import com.efun.dao.HBaseHelloDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by kinven on 16-11-9.
 */
@Service
public class HelloService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    HBaseHelloDao hBaseHelloDao;

    public Map<String,String> hbaseSayHello(){
        return hBaseHelloDao.getHello();
    }

    public String redisSayHello(){
        return stringRedisTemplate.opsForValue().get("hello");
    }
}
