package edata.api.sqlengine.gen;

import edata.api.sqlengine.model.Column;
import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.model.SQL92;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * SelectClause
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/4
 */
public class SelectClause {

    private List<Column> columns;
    private StringJoiner commaJoiner = new StringJoiner(SQL92.EMPTY);

    public static SelectClause newone(){
        return new SelectClause();
    }

    public SelectClause from(Query query){
        this.columns = query.getColumns().stream().filter(column -> !(0==column.getHidden())).collect(Collectors.toList());;
        return this;
    }

    public String genSql(){
        if(columns.size() > 0) {
            commaJoiner = new StringJoiner(SQL92.COMMA,SQL92.SELECT+SQL92.SPACE,SQL92.SPACE);
            this.columns.stream().forEach(column -> {
                StringJoiner spaceJoiner = new StringJoiner(SQL92.SPACE);
                spaceJoiner.add(column.getExpr().getBody()).add(SQL92.AS).add(column.getAsName());
                commaJoiner.add(spaceJoiner.toString());
            });
        }
        return commaJoiner.toString();
    }
}
