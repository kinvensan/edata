package edata.api.sqlengine;

import edata.api.sqlengine.builder.*;
import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.query.GroupBy;
import edata.api.sqlengine.query.OrderBy;

/**
 * QueryManager
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/2
 */
public class QueryManager {

    private SelectBuilder selectBuilder;
    private FromBuilder fromBuilder;
    private WhereBuilder whereBuilder;
    private GroupByBuilder groupByBuilder;
    private OrderByBuilder orderByBuilder;

    public void parse(Query query){
        this.selectBuilder = SelectBuilder.builder().columns(query.getColumns()).build();
        this.fromBuilder = FromBuilder.builder().tables(query.getTables()).build();
        this.whereBuilder = WhereBuilder.builder().columns(query.getFilters()).build();
        this.groupByBuilder = GroupByBuilder.builder().columns(query.getColumns()).build();
        this.orderByBuilder = OrderByBuilder.builder().columns(query.getColumns()).build();
    }

    public void toSqlTemplate(StringBuffer sb){

    }
}
