package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Column;
import edata.api.sqlengine.model.Expression;

import java.util.Map;

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

    public ColumnBuilder column(Column column){
        this.column = column;
        return this;
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


    public Column build(){
        //如果asName为空 ，则处理为Name
        if(null == column.getAsName()) {
            column.setAsName(column.getName());
        }
        //表达式处理
        if(null == column.getExpr()){
            column.setExpr(ExpressionBuilder.builder()
                    .expr(column.getExpr())
                    .columnName(column.getName())
                    .tableName(column.getTable())
                    .build());
        }
        return this.column;
    }




}
