package edata.api.sqlengine.meta;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EntityManager
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/6
 */
public interface EntityManager {

    public void regist(Class<?> entity);
    public EntityTable getEntityTable(String tableName);
    public String getColumnName(String tableName,String colName);
    public String getColumnType(String tableName,String colName);
    public List<String> getJoinUsing(String tableName);
}
