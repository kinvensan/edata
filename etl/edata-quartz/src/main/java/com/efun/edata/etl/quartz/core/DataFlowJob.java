package com.efun.edata.etl.quartz.core;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.cloud.dataflow.rest.client.DataFlowTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by kinven on 2018/4/17.
 */
public class DataFlowJob implements Job {

    private DataFlowTemplate dataFlowTemplate;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            dataFlowTemplate = new DataFlowTemplate(new URI("http://localhost:9393"));

            dataFlowTemplate.taskOperations().launch("helloworld",new LinkedHashMap<String,String>(),new ArrayList<String>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
