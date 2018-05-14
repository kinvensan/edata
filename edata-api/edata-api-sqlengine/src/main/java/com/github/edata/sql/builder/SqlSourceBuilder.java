package com.github.edata.sql.builder;

import com.github.edata.sql.gen.*;
import com.github.edata.sql.model.Query;
import com.github.edata.sql.model.SQL99;
import com.github.edata.sql.model.SqlSource;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * SqlSourceBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-10
 **/
public class SqlSourceBuilder implements Builder<SqlSource> {

    private SqlSource sqlSource;
    private Query query;

    public static SqlSourceBuilder builder(){
        return new SqlSourceBuilder();
    }

    @Override
    public SqlSourceBuilder from(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
        return this;
    }

    @Override
    public SqlSourceBuilder newOne() {
        this.sqlSource = new SqlSource();
        return this;
    }

    public SqlSourceBuilder query(Query query){
        this.query = QueryBuilder.builder().from(query).build();
        return this;
    }

    public SqlSourceBuilder params(Map<String,Object> params){
        //排除column，table，filter等名称
        if(null == this.sqlSource.getParams()){
            this.sqlSource.setParams(new HashMap<>());
        }
        this.sqlSource.addParamsMap(params);
        this.sqlSource.getParams().remove("column");
        this.sqlSource.getParams().remove("table");
        this.sqlSource.getParams().remove("filter");
        return this;
    }

    @Override
    public SqlSource build() {

        //构建SQL
        StringJoiner spaceJoiner = SQL99.SPACEJOINER();
        spaceJoiner.add(SelectClause.newOne().from(query).genSql())
                .add(FromClause.newOne().from(query).genSql())
                .add(WhereClause.newOne().from(query).genSql())
                .add(GroupbyClause.newOne().from(query).genSql())
                .add(OrderbyClause.newOne().from(query).genSql());
        this.sqlSource.setTemplate(spaceJoiner.toString());

        this.query.getFilters().forEach(filter -> {
            this.sqlSource.addParamsMap(filter.getExpr().getParams());
        });
        this.sqlSource.getParams().remove("column");
        this.sqlSource.getParams().remove("table");
        this.sqlSource.getParams().remove("filter");

        this.sqlSource.setId("q_"+(this.sqlSource.getTemplate().hashCode()+this.sqlSource.getParams().hashCode()));

        return this.sqlSource;
    }
}
