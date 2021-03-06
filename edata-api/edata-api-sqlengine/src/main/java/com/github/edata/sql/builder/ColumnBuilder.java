package com.github.edata.sql.builder;

import com.github.edata.sql.model.Column;
import com.github.edata.sql.model.Expression;

/**
 * ColumnBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class ColumnBuilder implements Builder<Column> {

    private Column column;

    public static ColumnBuilder builder(){
        ColumnBuilder selectColumnBuilder = new ColumnBuilder();
        return selectColumnBuilder;
    }

    @Override
    public ColumnBuilder from(Column column) {
        this.column = column;
        return this;
    }

    @Override
    public ColumnBuilder newOne() {
        this.column = new Column();
        return this;
    }

    public ColumnBuilder name(String name){
        this.column.setName(name);
        return this;
    }

    public ColumnBuilder table(String table){
        this.column.setTable(table);
        return this;
    }

    public ColumnBuilder asName(String asName){
        this.column.setAsName(asName);
        return this;
    }

    public ColumnBuilder aggregate(int aggregate){
        this.column.setAggregate(aggregate);
        return this;
    }

    public ColumnBuilder hidden(int hidden){
        this.column.setHidden(hidden);
        return this;
    }

    public ColumnBuilder orderby(int orderby) {
        this.column.setOrderby(orderby);
        return this;
    }

    public ColumnBuilder expression(Expression expression){
        this.column.setExpr(expression);
        return this;
    }

    public ColumnBuilder func(String func){
        if(null == this.column.getExpr()){
            this.column.setExpr(new Expression());
        }
        this.column.getExpr().setFunc(func);
        return this;
    }

    public Column build(){
        //如果asName为空 ，则处理为Name
        if(null == column.getAsName()) {
            column.setAsName(column.getName());
        }
        //表达式处理
        if(null == column.getExpr()){
            func("${column.fullName}");
        }
        //构建内部表达式的body
        ExpressionBuilder.builder().from(this.column.getExpr()).params(this.column).build();
        return this.column;
    }




}
