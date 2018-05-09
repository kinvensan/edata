package edata.api.sqlengine.meta;

import java.util.List;

/**
 * EntityManager
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/6
 */
public interface EntityManager {

    public void regist(Class<?> entity);
    public EntityTableInfo getEntityTable(String tableName);
    public String getColumnName(String tableName,String colName);
    public String getColumnType(String tableName,String colName);
    public List<String> getJoinUsing(String tableName);
}
