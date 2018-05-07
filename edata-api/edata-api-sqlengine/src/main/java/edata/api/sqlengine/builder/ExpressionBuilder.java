package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Expression;
import edata.api.sqlengine.model.SQL92;

import java.util.List;

/**
 * ExpressionBuilder 使用Beetl的自定义函数完成。
 * <pre>
 *     StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
 *     Configuration cfg = Configuration.defaultConfiguration();
 *     GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
 *     Template t = gt.getTemplate("hello,${name}");
 *     t.binding("name", "beetl");
 *     String str = t.render();
 *     System.out.println(str);
 * </pre>
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-4
 **/
public class ExpressionBuilder {

    private Expression expression;
    private String tableName;
    private String columnName;
    private String func;
    private String body;
    private List<Object> params;

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
    public ExpressionBuilder func(String func){
        this.func = func;
        this.expression.setFunc(func);
        return this;
    }

    public ExpressionBuilder body(String body){
        this.body = body;
        this.expression.setBody(body);
        return this;
    }

    public ExpressionBuilder params(Object... params){
//        this.params = params;
//        this.expression.setParams(params);
        return this;
    }


    public ExpressionBuilder expr(Expression expr){
        this.expression = expr;
        return this;
    }

    public ExpressionBuilder newExpression(String func){
        this.expression = new Expression();
        return this;
    }

    public Expression build(){

        if("NAME".equals(expression.getFunc())) {
            this.expression.setBody(this.tableName+SQL92.DOT+this.columnName);
            return this.expression;
        }
        if("NONE".equals(expression.getFunc())) {
            return this.expression;
        }


        return this.expression;
    }
}
