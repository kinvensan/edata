package com.github.edata.sql;

import com.github.edata.sql.model.Query;
import com.github.edata.sql.builder.SqlSourceBuilder;
import com.github.edata.sql.kit.ScannerKit;
import com.github.edata.sql.meta.FunctionInfo;
import com.github.edata.sql.model.SqlSource;
import com.github.edata.sql.meta.EntityTableInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Engine
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/6
 */
public class Engine {

    private static final Map<String,FunctionInfo> FUNCTION_INFO_MAP= new ConcurrentHashMap<>();

    private static final Map<String,EntityTableInfo> ENTITY_TABLE_INFO_MAP= new ConcurrentHashMap<>();

    private Config config;

    private static class SingletonHolder{
        public static Engine instance = new Engine();
    }

    private Engine(){

    }

    public static Engine getInstance(){
        if(null == SingletonHolder.instance.getConfig()) {
            SingletonHolder.instance.configurate(new Config());
        }
        return SingletonHolder.instance;
    }

    /**
     * 如果配置有变化,可以通过系统进行构建
     * @param config
     */
    public void configurate(Config config){
        this.config = config;
        scanFunction(config.getFunctionPackage());
        scanEntityTable(config.getEntityPackage());
    }

    public SqlSource parse(Query query){
        return SqlSourceBuilder.builder().newOne().query(query).build();
    }

    public Config getConfig() {
        return config;
    }

    public boolean hasFunction(String name){
        return FUNCTION_INFO_MAP.containsKey(name);
    }

    public FunctionInfo getFunctionInfo(String name){
        return FUNCTION_INFO_MAP.get(name);
    }

    public boolean hasTable(String name){
        return  ENTITY_TABLE_INFO_MAP.containsKey(name);
    }

    public EntityTableInfo getEntityTableInfo(String name){
        return  ENTITY_TABLE_INFO_MAP.get(name);
    }

    private void scanFunction(String basePackage){
        ScannerKit.getClasses(basePackage).stream().forEach(clazz -> {
            FunctionInfo functionInfo = new FunctionInfo();
            functionInfo.register(clazz);
            if(null != functionInfo.getName()){
                FUNCTION_INFO_MAP.put(functionInfo.getName(),functionInfo);
            }
        });
    }

    private void scanEntityTable(String basePackage) {
        ScannerKit.getClasses(basePackage).stream().forEach(clazz -> {
            EntityTableInfo entityTableInfo = new EntityTableInfo();
            entityTableInfo.register(clazz);
            if(null != entityTableInfo.getTable()){
                ENTITY_TABLE_INFO_MAP.put(entityTableInfo.getTable(),entityTableInfo);
            }
        });
    }
}
