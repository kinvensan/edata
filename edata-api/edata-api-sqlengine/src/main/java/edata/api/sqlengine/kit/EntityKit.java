package edata.api.sqlengine.kit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * EntityKit 获取元素的对应参数，包括：
 * <pre>
 * @JoinUsing（自动Join表字段定义，字段的名称必须一致才能使用）
 * @Comment （表或者字段的UI名称）
 * @JdbcTypeName (表的字段类型）
 * </pre>
 * @author kinven
 * @version 0.1
 * @date 2018/5/5
 */
public class EntityKit {



    public void init(Class<?> clazz){

        List<Field> fields = Arrays.asList(clazz.getFields());
        if(fields.get(0).getType() == String.class){

        }
    }

    public List<String> getJoinUsing(String tableName) {
        return new ArrayList<>();
    }

    public String jdbcTypeName(String tableName,String colName) {
        return "string";
    }

    public String getComment(String tableName,String colName){
        return "";
    }
}
