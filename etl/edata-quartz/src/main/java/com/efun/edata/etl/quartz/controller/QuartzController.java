package com.efun.edata.etl.quartz.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kinven on 2018/4/17.
 */
@RestController
public class QuartzController {

    @RequestMapping("/scheduler/{dagId}")
    public String showDag(@PathVariable String dagId){
        return "hell world " + dagId;
    }
}
