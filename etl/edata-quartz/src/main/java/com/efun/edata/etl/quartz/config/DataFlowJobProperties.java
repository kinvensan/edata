package com.efun.edata.etl.quartz.config;

import java.util.List;
import java.util.Map;

/**
 * Created by kinven on 2018/4/18.
 */
public class DataFlowJobProperties {
    private String serverName;
    private String serverurl;
    private String taskName;
    private Map<String,String> parameters;
    private List<String> arguments;

}
