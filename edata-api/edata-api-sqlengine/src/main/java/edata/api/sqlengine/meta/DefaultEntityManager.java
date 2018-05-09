package edata.api.sqlengine.meta;

import org.beetl.sql.core.kit.CaseInsensitiveHashMap;

import java.util.List;
import java.util.Map;

/**
 * DefaultEntityManager
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/6
 */
public class DefaultEntityManager implements EntityManager{
    private static EntityManager instance = null;
    private static final Map<String,EntityTableInfo> entityTableMap = new CaseInsensitiveHashMap();

    public static EntityManager getInstance(){
        if(instance == null) {
            instance = new DefaultEntityManager();
        }
        return instance;
    }

    public EntityTableInfo get(String tableName){
        return entityTableMap.get(tableName);
    }


    @Override
    public void regist(Class<?> entity) {

    }

    @Override
    public EntityTableInfo getEntityTable(String tableName) {
        return null;
    }

    @Override
    public String getColumnName(String tableName, String colName) {
        return null;
    }

    @Override
    public String getColumnType(String tableName, String colName) {
        return null;
    }

    @Override
    public List<String> getJoinUsing(String tableName) {
        return null;
    }
}
