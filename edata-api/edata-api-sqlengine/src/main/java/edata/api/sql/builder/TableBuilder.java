package edata.api.sql.builder;

import edata.api.sql.EngineException;
import edata.api.sql.kit.ScannerKit;
import edata.api.sql.model.Expression;
import edata.api.sql.model.Query;
import edata.api.sql.model.SQL99;
import edata.api.sql.model.Table;

import java.util.StringJoiner;

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
    public TableBuilder newOne() {
        this.table = new Table();
        this.table.setRelation(1);
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

    public TableBuilder func(String func){
        if(null == this.table.getExpr()){
            this.table.setExpr(new Expression());
        }
        this.table.getExpr().setFunc(func);
        return this;
    }

    public Table build(){
        if(null == this.table.getName()){
            /*
            log.error.
            ...他妈异常，名字都没有
            */
            throw EngineException.newBuilderException("TableBuilder check error, this is no table selected." );
        }

        if(ScannerKit.hasTable(table.getName()) && null == this.table.getExpr()){
            // 跑个腿，与实体关联，实现join using表达式
            StringJoiner usingJoiner = SQL99.USINGJOINER();
            ScannerKit.getEntityTableInfo(this.table.getName()).getJoinColumns().stream().forEach(joinColumn -> {
                usingJoiner.add(joinColumn);
            });
            this.func(usingJoiner.toString());
        }

        //构建表达式body
        ExpressionBuilder.builder().from(this.table.getExpr()).params(this.table).build();

        return this.table;
    }

    public Query getQuery(){
        return this.query;
    }
}
