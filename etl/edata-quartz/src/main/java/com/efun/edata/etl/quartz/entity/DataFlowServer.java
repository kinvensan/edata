package com.efun.edata.etl.quartz.entity;

import javax.persistence.Entity;

/**
 * Created by kinven on 2018/4/18.
 */
@Entity
public class DataFlowServer {

    private static final String DEFAULT_HEALPATH = "management/health";
    private static final String DEFAULT_SERVERURL = "http://localhost:9393";

    private String serverName;
    private String serverUrl = DEFAULT_SERVERURL;
    private String healPath = DEFAULT_HEALPATH;


}
