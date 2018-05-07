package edata.api.sqlengine.meta;

import org.beetl.sql.core.kit.CaseInsensitiveHashMap;

import java.util.Map;

/**
 * EntityTable
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/6
 */
public class EntityTable {

    // 实体映射的数据库表名
    private String name;
    private Class<?> clazz;
    // Java属性映射的数据库字段集合（column -> property）
    private Map<String, String> colsMap = new CaseInsensitiveHashMap();
    // 数据库字段映射的Java属性集合（property -> column）
    private Map<String, String> propsMap = new CaseInsensitiveHashMap();
}
