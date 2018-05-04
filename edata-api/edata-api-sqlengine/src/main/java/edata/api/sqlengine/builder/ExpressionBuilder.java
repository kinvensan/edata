package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Expression;
import edata.api.sqlengine.model.SQL92;

/**
 * ExpressionBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-4
 **/
public class ExpressionBuilder {

    private Expression expression;
    private String tableName;
    private String columnName;

    public static ExpressionBuilder builder(){
        return new ExpressionBuilder();
    }

    public ExpressionBuilder tableName(String tableName){
        this.tableName = tableName;
        return this;
    }

    public ExpressionBuilder columnName(String ColumnName){
        this.columnName = columnName;
        return this;
    }

    public ExpressionBuilder expr(Expression expr){
        this.expression = expr;
        return this;
    }

    public Expression build(){
        if(null == expression.getFunc() || expression.getFunc().isEmpty()){
            this.expression.setBody(this.tableName+SQL92.DOT+this.columnName);
        }
        return this.expression;
    }
}
