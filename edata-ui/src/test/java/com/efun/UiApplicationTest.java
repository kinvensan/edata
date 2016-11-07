package com.efun;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by kinven on 16-11-3.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UiApplicationTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void testRedisSetAndGet() throws Exception {
        stringRedisTemplate.opsForValue().set("hello","world2");
        Assert.assertEquals("Redis is ok","world2",stringRedisTemplate.opsForValue().get("hello"));

    }
}
