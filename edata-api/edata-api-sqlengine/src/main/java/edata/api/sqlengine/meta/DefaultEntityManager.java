package edata.api.sqlengine.meta;

import org.beetl.sql.core.kit.CaseInsensitiveHashMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DefaultEntityManager
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/6
 */
public class DefaultEntityManager implements EntityManager{
    private static EntityManager instance = null;
    private static final Map<String,EntityTable> entityTableMap = new CaseInsensitiveHashMap();

    public static EntityManager getInstance(){
        if(instance == null) {
            instance = new DefaultEntityManager();
        }
        return instance;
    }

    public EntityTable get(String tableName){
        return entityTableMap.get(tableName);
    }

    public void registry(){

    }

    @Override
    public void regist() {

    }

    @Override
    public EntityTable getEntityTable(String tableName) {
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
