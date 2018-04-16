package com.efun.edata.etl.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 简单 Quartz-Cluster 微服务，采用注解配置 Quartz 分布式集群。
 *
 * @author xqw2@163.com
 *
 * @version 0.0.1
 *
 * @date 2018/4/18
 */
@SpringBootApplication
public class QuartzClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzClusterApplication.class, args);

        System.out.println("【【【【【【 简单Quartz-Cluster微服务 】】】】】】已启动.");
    }

}
