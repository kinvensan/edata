package com.efun.web;

import com.efun.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by kinven on 16-11-3.
 */
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @RequestMapping("/redis/hello")
    public String redisHello(){
        return helloService.redisSayHello();
    }

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/hbase/hello")
    public Map<String,String> hbaseHello(){
        return helloService.hbaseSayHello();
    }
}
