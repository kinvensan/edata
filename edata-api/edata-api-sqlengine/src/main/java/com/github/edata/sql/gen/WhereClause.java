package com.github.edata.sql.gen;

import com.github.edata.sql.model.Query;
import com.github.edata.sql.model.Filter;
import com.github.edata.sql.model.SQL99;
import com.github.edata.sql.type.FilterRelation;

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

    public static WhereClause newOne(){
        return new WhereClause();
    }

    public WhereClause from(Query query){
        this.filters = query.getFilters();
        return this;
    }

    public String genSql() {
        if(filters.size() == 0){
            return SQL99.EMPTY;
        }

        StringJoiner whereJoiner = SQL99.WHEREJOINER();
        this.filters.stream().forEach(filter -> {
            whereJoiner.add(FilterRelation.getString(filter.getRelation())).add(filter.getExpr().getBody());

        });
        return whereJoiner.toString().trim();
    }
}
