package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.model.Table;
import edata.api.sqlengine.type.TableRelation;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * TableBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/1
 */
public class TableBuilder {

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
        return this;
    }

    public TableBuilder name(String name){
        this.table = new Table(name);
        return this;
    }

    public TableBuilder firstTable(Table table){
        this.table = table;
        table.setRelation(0);
        return this;
    }

    public Table build(){
        return this.table;
    }

    public Query getQuery(){
        return this.query;
    }
}
