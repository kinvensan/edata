package edata.api.sqlengine.gen;

import edata.api.sqlengine.model.Column;
import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.model.SQL92;

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

    public GroupbyClause from(Query query){
        Boolean hasGroupby = query.getColumns().stream().anyMatch(column -> 1==column.getAggregate());
        if(hasGroupby){
            this.columns = query.getColumns().stream().filter(column -> 1 == column.getAggregate()).collect(Collectors.toList());
        }
        return this;
    }

    public String genSql(){
        if(columns.size() == 0){
            return SQL92.EMPTY;
        }
        StringJoiner commaJoiner = new StringJoiner(SQL92.COMMA,SQL92.GROUPBY+SQL92.SPACE,SQL92.EMPTY);
        columns.forEach(column -> {
            commaJoiner.add(column.getExpr().getBody());
        });
        return commaJoiner.toString();
    }
}
