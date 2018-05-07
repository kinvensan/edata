package edata.api.sqlengine;

import edata.api.sqlengine.meta.EntityManager;

/**
 * QuerySqlEngine
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/6
 */
public class QuerySqlEngine {

    private static QuerySqlEngine querySqlEngine;
    private EntityManager entityManager;

    public static QuerySqlEngine instance(){
        if(querySqlEngine == null){
            querySqlEngine = new QuerySqlEngine();
        }
        return querySqlEngine;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
