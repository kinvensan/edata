package com.efun.edata.etl.quartz.service;

import com.efun.edata.etl.quartz.entity.DataFlowServer;
import org.springframework.cloud.dataflow.rest.client.DataFlowTemplate;

/**
 * Created by kinven on 2018/4/18.
 */
public interface DataFlowServerService {

    public DataFlowTemplate getInstance(int serverId);
    public void checkHealth(int serverId);
}
