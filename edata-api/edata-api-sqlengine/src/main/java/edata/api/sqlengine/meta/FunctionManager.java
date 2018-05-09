package edata.api.sqlengine.meta;

import edata.api.sqlengine.kit.ScannerKit;
import org.beetl.core.Function;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * FunctionManager
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/9
 */
public class FunctionManager {
    private static final Map<String,FunctionInfo> functionMap = new ConcurrentHashMap<>();
    private String basePackage;


    public void register(Class<?> clazz){
        FunctionInfo functionInfo = new FunctionInfo();
        functionInfo.register(clazz);
        functionMap.put(functionInfo.getName(),functionInfo);
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void refresh(){
        Set<Class<?>> clazzes = ScannerKit.getClasses(this.basePackage);
        //过滤掉非函数部分,然后再实现注册。
        Set<Class<?>> classes = clazzes.stream().filter(clazz -> {
            return true;
        }).collect(Collectors.toSet());

        classes.forEach(clazz -> {
            register(clazz);
        });
    }
}
