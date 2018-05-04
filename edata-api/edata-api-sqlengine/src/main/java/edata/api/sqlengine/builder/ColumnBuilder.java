package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Column;

import java.util.Map;

/**
 * ColumnBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class ColumnBuilder {

    private Column column;

    public static ColumnBuilder builder(){
        ColumnBuilder selectColumnBuilder = new ColumnBuilder();
        return selectColumnBuilder;
    }

    public ColumnBuilder column(Column column){
        this.column = column;
        return this;
    }

    public Column build(){
        //如果asName为空 ，则处理为Name
        if(null == column.getAsName()){
            column.setAsName(column.getName());
        }
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
