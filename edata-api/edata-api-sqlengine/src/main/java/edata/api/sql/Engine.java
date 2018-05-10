package edata.api.sql;

import edata.api.sql.builder.SqlSourceBuilder;
import edata.api.sql.kit.ScannerKit;
import edata.api.sql.meta.FunctionInfo;
import edata.api.sql.model.SqlSource;
import edata.api.sql.meta.EntityTableInfo;
import edata.api.sql.model.Query;

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
        ScannerKit.scanEntityTable(config.getEntityPackage());
    }

    public SqlSource parse(Query query){
        return SqlSourceBuilder.builder().newOne().query(query).build();
    }

    public Config getConfig() {
        return config;
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
