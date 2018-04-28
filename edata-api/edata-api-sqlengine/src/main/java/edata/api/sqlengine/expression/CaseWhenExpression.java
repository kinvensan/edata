package edata.api.sqlengine.expression;

import edata.api.sqlengine.expression.Expression;

/**
 * From is SQL part "select * from xxx"
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
public class CaseWhenExpression implements Expression {



    public CaseWhenExpression(){

    }

    @Override
    public String toString() {
        return super.toString();
    }


    @Override
    public String toSql() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }
}
