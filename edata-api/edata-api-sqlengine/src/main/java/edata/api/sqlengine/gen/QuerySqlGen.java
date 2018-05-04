package edata.api.sqlengine.gen;

import edata.api.sqlengine.model.*;


/**
 * QuerySqlGen
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/3
 */
public class QuerySqlGen {

    private SelectClause selectClause = new SelectClause();
    private FromClause fromClause = new FromClause();
    private WhereClause whereClause = new WhereClause();
    private GroupbyClause groupbyClause = new GroupbyClause();
    private OrderbyClause orderbyClause = new OrderbyClause();


    public QuerySqlGen from (Query query){
        selectClause.from(query);
        fromClause.from(query);
        whereClause.from(query);
        groupbyClause.from(query);
        orderbyClause.from(query);
        return this;
    }

    public String genSql(){
        StringBuilder sb = new StringBuilder();
        sb.append(selectClause.genSql())
                .append(fromClause.genSql())
                .append(whereClause.genSql())
                .append(groupbyClause.genSql())
                .append(orderbyClause.genSql());
        return sb.toString();
    }

}
