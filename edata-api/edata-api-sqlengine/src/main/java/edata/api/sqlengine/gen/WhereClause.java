package edata.api.sqlengine.gen;

import edata.api.sqlengine.model.Filter;
import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.model.SQL92;
import edata.api.sqlengine.type.FilterRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * WhereClause
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/3
 */
public class WhereClause {

    List<Filter> filters = new ArrayList<>();

    public WhereClause from(Query query){
        this.filters = query.getFilters();
        return this;
    }

    public String genSql() {
        if(filters.size() == 0){
            return SQL92.EMPTY;
        }

        StringJoiner spaceJoiner = new StringJoiner(SQL92.SPACE,SQL92.WHERE+SQL92.SPACE,SQL92.SPACE);
        this.filters.stream().forEach(filter -> {
            spaceJoiner.add(FilterRelation.getString(filter.getRelation())).add(filter.getExpr().getBody());

        });
        return spaceJoiner.toString();
    }
}
