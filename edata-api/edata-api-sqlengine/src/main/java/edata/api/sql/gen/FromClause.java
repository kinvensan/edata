package edata.api.sql.gen;

import edata.api.sql.EngineException;
import edata.api.sql.model.Query;
import edata.api.sql.model.SQL99;
import edata.api.sql.model.Table;
import edata.api.sql.type.TableRelation;

import java.util.List;
import java.util.StringJoiner;

/**
 * FromClause
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/3
 */
public class FromClause {

    private List<Table> tables;

    public static FromClause newOne(){
        return new FromClause();
    }

    public FromClause from(Query query){
        this.tables = query.getTables();
        return this;
    }

    public String genSql(){
        if(tables.size() == 0){
            throw EngineException.newClauseException("FromClause check this is no table in query!");
        }
        StringJoiner fromJoiner = SQL99.FROMJOINER();
        this.tables.stream().forEach(table -> {
            fromJoiner.add(TableRelation.getString(table.getRelation())).add(table.getName());
            if(table.getRelation() > 0) {
                fromJoiner.add(SQL99.ON).add(table.getExpr().getBody());
            }
        });
        return fromJoiner.toString().trim();
    }
}
