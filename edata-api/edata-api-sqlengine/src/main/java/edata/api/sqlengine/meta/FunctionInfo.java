package edata.api.sqlengine.meta;

import edata.api.sqlengine.annotation.TemplateFunction;
import edata.api.sqlengine.annotation.TemplateFunctionConfig;
import org.beetl.core.Function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FunctionInfo 获取函数登记表
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/9
 */
public class FunctionInfo {
    private String name;
    private Class<Function> clazz;
    private Map<String,Object> config = new LinkedHashMap<>();

    public static boolean isFunction(Class<?> clazz){
        return true;
    }

    public void register(Class<?> clazz){
        if(isFunction(clazz)){
            TemplateFunction templateFunction = clazz.getAnnotation(TemplateFunction.class);
            this.name = templateFunction.name();
            this.clazz = (Class<Function>)clazz;
        }
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setClazz(Class<Function> clazz) {
        this.clazz = clazz;
    }

    public Class<Function> getClazz() {
        return clazz;
    }

    public void setConfig(Map<String, Object> config) {
        this.config.putAll(config);
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public Function getInstance(){
        Function instance = null;
        try {
            instance =  clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(instance == null){
            //返回nullfunction。
        }
        if(config.isEmpty()){
            return instance;
        }
        //进行配置调用
        List<Method> configMethodSet = Arrays.stream(clazz.getMethods()).filter(method -> {
            return method.isAnnotationPresent(TemplateFunctionConfig.class)
                    && method.getGenericParameterTypes().length == 1;
        }).collect(Collectors.toList());
        if(!configMethodSet.isEmpty()) {
            Method configMethod = configMethodSet.get(0);
            try {
                configMethod.invoke(instance, config);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
