package edata.api.sqlengine.gen;

import edata.api.sqlengine.SqlException;
import edata.api.sqlengine.model.Column;
import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.model.SQL92;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * SelectClause 通过Query的Colums列获取所有的Select语句部分
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/4
 */
public class SelectClause {

    private List<Column> columns = new ArrayList<>();
//    private StringJoiner commaJoiner = new StringJoiner(SQL92.EMPTY);

    public static SelectClause newone(){
        return new SelectClause();
    }

    public SelectClause from(Query query){
        columns.addAll(query.getColumns());
        columns = columns.stream().filter(column -> 0==column.getHidden()).collect(Collectors.toList());
        return this;
    }

    public String genSql(){
        if(columns.size() == 0){
            throw new SqlException();
        }
        StringJoiner commaJoiner = new StringJoiner(SQL92.COMMA,SQL92.SELECT+SQL92.SPACE,SQL92.SPACE);
        this.columns.forEach(column -> {
            StringJoiner spaceJoiner = new StringJoiner(SQL92.SPACE);
            spaceJoiner.add(column.getExpr().getBody()).add(SQL92.AS).add(column.getAsName());
            commaJoiner.add(spaceJoiner.toString());
        });
        return commaJoiner.toString();
    }
}
