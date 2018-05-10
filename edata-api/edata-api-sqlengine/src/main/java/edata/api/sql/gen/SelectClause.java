package edata.api.sql.gen;

import edata.api.sql.EngineException;
import edata.api.sql.model.Column;
import edata.api.sql.model.Query;
import edata.api.sql.model.SQL99;

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
//    private StringJoiner commaJoiner = new StringJoiner(SQL99.EMPTY);

    public static SelectClause newOne(){
        return new SelectClause();
    }

    public SelectClause from(Query query){
        columns.addAll(query.getColumns());
        columns = columns.stream().filter(column -> 0==column.getHidden()).collect(Collectors.toList());
        return this;
    }

    public String genSql(){
        if(columns.size() == 0){
            throw EngineException.newClauseException("SelectClause check this is no column in query!");
        }
        StringJoiner selectjoiner = SQL99.SELECTJOINER();
        this.columns.forEach(column -> {
            StringJoiner spaceJoiner = SQL99.SPACEJOINER();
            spaceJoiner.add(column.getExpr().getBody()).add(SQL99.AS).add(column.getAsName());
            selectjoiner.add(spaceJoiner.toString());
        });
        return selectjoiner.toString().trim();
    }
}
