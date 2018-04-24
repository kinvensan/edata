package com.efun.edata.etl.dexecutor.core;

/**
 * Created by kinven on 2018/4/22.
 */
public class Task extends com.github.dexecutor.core.task.Task<Integer,Boolean> {

    private Integer id;
    private Boolean result; //改进为returnT
    private String name;
    private String taskType; //改进为enum类型
    private Coordinate coordinate;


    @Override
    public Boolean execute() {
        return null;
    }
}
