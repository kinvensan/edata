package edata.api.sqlengine.gen;

import edata.api.sqlengine.model.Column;
import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.model.SQL92;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * OrderbyClause
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-4
 **/
public class OrderbyClause {

    private List<Column> columns = new ArrayList<>();

    public OrderbyClause from(Query query){
        this.columns = query.getColumns().stream().filter(column -> !(0==column.getOrderby())).collect(Collectors.toList());
        return this;
    }

    public String genSql(){
        if(columns.size() == 0){
            return SQL92.EMPTY;
        }
        StringJoiner commaJoiner = new StringJoiner(SQL92.COMMA,SQL92.ORDERBY+SQL92.SPACE,SQL92.EMPTY);
        columns.forEach(column -> {
            commaJoiner.add(column.getAsName());
        });
        return commaJoiner.toString();
    }
}
