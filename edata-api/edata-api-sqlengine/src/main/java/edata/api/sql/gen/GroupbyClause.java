package edata.api.sql.gen;

import edata.api.sql.model.Column;
import edata.api.sql.model.Query;
import edata.api.sql.model.SQL99;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * GroupbyClause
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/3
 */
public class GroupbyClause {
    private List<Column> columns = new ArrayList<>();

    public static GroupbyClause newOne(){
        return new GroupbyClause();
    }

    public GroupbyClause from(Query query){
        Boolean hasGroupby = query.getColumns().size() > 1;
        hasGroupby = hasGroupby && query.getColumns().stream().anyMatch(column -> 1==column.getAggregate());
        if(hasGroupby){
            //this.columns.addAll(query.getColumns());
            this.columns = query.getColumns().stream().filter(column -> 0 == column.getAggregate()).collect(Collectors.toList());
        }
        return this;
    }

    public String genSql(){
        if(columns.size() == 0){
            return SQL99.EMPTY;
        }
        StringJoiner groupbyjoiner = SQL99.GROUPBYJOINER();
        columns.forEach(column -> {
            groupbyjoiner.add(column.getExpr().getBody());
        });
        return groupbyjoiner.toString().trim();
    }
}
