package edata.api.sqlengine.builder;

import edata.api.sqlengine.kit.BeetlKit;
import edata.api.sqlengine.model.*;

import java.util.HashMap;
import java.util.Map;

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
public class ExpressionBuilder implements Builder<Expression> {

    private Expression expression;

    public static ExpressionBuilder builder(){
        return new ExpressionBuilder();
    }

    public ExpressionBuilder params(Column column){
        this.expression.getParams().put("column",column);
        return this;
    }

    public ExpressionBuilder params(Filter filter) {
        //适应习惯
        this.expression.getParams().put("column",filter);
        this.expression.getParams().put("filter",filter);
        return this;
    }

    public ExpressionBuilder params(Table table) {
        this.expression.getParams().put("table",table);
        return this;
    }

    @Override
    public ExpressionBuilder from(Expression expression) {
        this.expression = expression;
        if(null == this.expression.getParams()){
            this.expression.setParams(new HashMap<>());
        }
        return this;
    }

    @Override
    public ExpressionBuilder newOne() {
        this.expression = new Expression();
        this.expression.setParams(new HashMap<>());
        return this;
    }

    public ExpressionBuilder func(String func) {
        this.expression.setFunc(func);
        return this;
    }

    public ExpressionBuilder params(Map<String,Object> params){
        if(null == this.expression.getParams()){
            this.expression.setParams( new HashMap<>() );
        }
        this.expression.getParams().putAll(params);
        return this;
    }

    public ExpressionBuilder body(String body){
        this.expression.setBody(body);
        return this;
    }

    public Expression build(){
        //利用表达式进行body翻译
        /**
         * template t = gt.getTemplate("hello,${name}");
         * t.binding("name", "beetl");
         * String str = t.render();
         */

        this.expression.setBody(
            BeetlKit.render(this.expression.getFunc(),this.expression.getParams())
        );

        return this.expression;
    }
}
