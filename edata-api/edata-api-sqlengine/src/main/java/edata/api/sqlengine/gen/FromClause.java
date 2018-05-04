package edata.api.sqlengine.gen;

import edata.api.sqlengine.SqlException;
import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.model.SQL92;
import edata.api.sqlengine.model.Table;
import edata.api.sqlengine.type.TableRelation;

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

    public FromClause from(Query query){
        this.tables = query.getTables();
        return this;
    }

    public String genSql(){
        if(tables.size() == 0){
            throw new SqlException();
        }
        StringJoiner spaceJoiner = new StringJoiner(SQL92.SPACE,SQL92.FROM+SQL92.SPACE,SQL92.SPACE);
        this.tables.stream().forEach(table -> {
            spaceJoiner.add(TableRelation.getString(table.getRelation())).add(table.getName());
            if(table.getRelation() > 0) {
                spaceJoiner.add(SQL92.ON).add(table.getExpr().getBody());
            }
        });
        return spaceJoiner.toString();
    }
}
