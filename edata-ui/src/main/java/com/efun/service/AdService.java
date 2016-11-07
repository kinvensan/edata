package com.efun.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.ParseState;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by kinven on 16-11-3.
 */
@Service
public class AdService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();


    public Map<String,Long>  getTotal() throws IOException {
        Map<String,Long> gameCount = new HashMap<>();
        Long totalClicked = 0L;
        Long totalInstalled = 0L;
        Long totalRegister = 0L;
        Long totalUser = 0L;

        List<?> gameJson = stringRedisTemplate.opsForHash().values("ad_game_count");

        for(Object json : gameJson){
            Map<String,Integer> totalMap = objectMapper.readValue(json.toString(),HashMap.class);
            totalClicked += totalMap.get("click");
            totalInstalled += totalMap.get("install");
            totalRegister += totalMap.get("register");
        }
        gameCount.put("total_clicked",totalClicked);
        gameCount.put("total_installed",totalInstalled);
        gameCount.put("total_register",totalRegister);
        gameCount.put("total_user",totalUser);
        return gameCount;
    }

    public Map<String,Long> getTotalByGame(List<String> gameIds) throws IOException {
        Map<String,Long> gameCount = new HashMap<>();
        Long totalClicked = 0L;
        Long totalInstalled = 0L;
        Long totalRegister = 0L;
        Long totalUser = 0L;
        Map<?,?> gameJson = stringRedisTemplate.opsForHash().entries("ad_game_count");
        for(Map.Entry<?,?> entry : gameJson.entrySet()){
            if(gameIds.contains(entry.getKey())) {
                Map<String, Integer> totalMap = objectMapper.readValue(entry.getValue().toString(), HashMap.class);
                totalClicked += totalMap.get("click");
                totalInstalled += totalMap.get("install");
                totalRegister += totalMap.get("register");
            }
        }
        gameCount.put("total_clicked",totalClicked);
        gameCount.put("total_installed",totalInstalled);
        gameCount.put("total_register",totalRegister);
        gameCount.put("total_user",0L);
        return gameCount;

    }

}
