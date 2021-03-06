package com.github.edata.sql.gen;

import com.github.edata.sql.model.Column;
import com.github.edata.sql.model.Query;
import com.github.edata.sql.model.SQL99;
import com.github.edata.sql.type.OrderbyType;

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

    public static OrderbyClause newOne(){
        return new OrderbyClause();
    }

    public OrderbyClause from(Query query){
        this.columns = query.getColumns().stream().filter(column -> !(0==column.getOrderby())).collect(Collectors.toList());
        return this;
    }

    public String genSql(){
        if(columns.size() == 0){
            return SQL99.EMPTY;
        }
        StringJoiner orderbyJoiner = SQL99.ORDERBYJOINER();
        columns.forEach(column -> {
            StringJoiner spaceJoiner = SQL99.SPACEJOINER();
            spaceJoiner.add(column.getAsName()).add(OrderbyType.getString(column.getOrderby()));
            orderbyJoiner.add(spaceJoiner.toString());
        });
        return orderbyJoiner.toString().trim();
    }
}
