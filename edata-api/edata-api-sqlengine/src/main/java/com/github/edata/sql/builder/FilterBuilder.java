package com.github.edata.sql.builder;

import com.github.edata.sql.model.Filter;
import com.github.edata.sql.model.Expression;

/**
 * FilterBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class FilterBuilder implements Builder<Filter>{

    private Filter filter;

    public static FilterBuilder builder(){
        FilterBuilder builder = new FilterBuilder();
        return builder;
    }

    public FilterBuilder filter(Filter filter){
        this.filter = filter;
        return this;
    }

    public FilterBuilder firstFilter(Filter filter) {
        filter.setRelation(0);
        this.filter = filter;
        return this;
    }

    @Override
    public FilterBuilder from(Filter filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public FilterBuilder newOne() {
        this.filter = new Filter();
        return this;
    }

    public FilterBuilder name(String name) {
        this.filter.setName(name);
        return this;
    }

    public FilterBuilder table(String table) {
        this.filter.setTable(table);
        return this;
    }

    public FilterBuilder relation(int relation){
        this.filter.setRelation(relation);
        return this;
    }

    public FilterBuilder expr(Expression expression){
        this.filter.setExpr(expression);
        return this;
    }

    public FilterBuilder func(String func){
        if(null == this.filter.getExpr()) {
            this.filter.setExpr(new Expression());
        }
        this.filter.getExpr().setFunc(func);
        return this;
    }

    public FilterBuilder addParam(String key,Object value){
        if(null == this.filter.getExpr()) {
            this.filter.setExpr(new Expression());
        }
        this.filter.getExpr().getParams().put(key,value);
        return this;
    }



    /**
     * 处理表达式
     * @return
     */
    public Filter build() {

        //filter表达式构建
        ExpressionBuilder.builder().from(this.filter.getExpr()).params(this.filter).build();
        return this.filter;
    }
}
