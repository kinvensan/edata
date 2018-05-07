package edata.api.sqlengine.builder;

import edata.api.sqlengine.QuerySqlEngine;
import edata.api.sqlengine.meta.DefaultEntityManager;
import edata.api.sqlengine.meta.EntityManager;
import edata.api.sqlengine.model.Expression;
import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.model.SQL92;
import edata.api.sqlengine.model.Table;
import edata.api.sqlengine.type.TableRelation;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * TableBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/1
 */
public class TableBuilder implements Builder<Table> {

    private Table table;
    private Query query;

    public static TableBuilder builder(){
        return new TableBuilder();
    }

    public TableBuilder query(Query query){
        this.query = query;
        return this;
    }

    public TableBuilder table(Table table){
        this.table = table;
        //如果表达式为空，则直接使用EntityKit获取，
        //构建Express
        return this;
    }

    public TableBuilder firstTable(Table table){
        this.table = table;
        table.setRelation(0);
        //清除表达式内容
        table.getExpr().setBody("");
        return this;
    }

    @Override
    public TableBuilder from(Table table) {
        this.table = table;
        return this;
    }

    @Override
    public TableBuilder newOne(Object... args) {
        this.table = new Table(String.valueOf(args[0]));
        return this;
    }

    //必须要有名字
    public TableBuilder name(String name){
        this.table.setName(name);
        return this;
    }

    public TableBuilder relation(int relation){
        this.table.setRelation(relation);
        return this;
    }

    public TableBuilder expr(Expression expression){
        this.table.setExpr(expression);
        return this;
    }

    public Table build(){
        if(null == this.table.getName()){
            /*
            log.error.
            ...他妈异常，名字都没有
            */
        }
        if(null == this.table.getExpr()){
            // 跑个腿，与实体关联，实现join using表达式
            List<String> params = DefaultEntityManager.getInstance().getJoinUsing(this.table.getName());
            StringJoiner usingJoiner = SQL92.USING();
            ExpressionBuilder.builder().func("NONE").body(usingJoiner.add())
        }

        return this.table;
    }

    public Query getQuery(){
        return this.query;
    }
}
