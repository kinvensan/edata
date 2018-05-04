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

    public Table build(){
        //如果Query中没有Table，则使用Column和filter获取一套。
        if(query.getTables().isEmpty()){
            Set<String> tables = query.getColumns().stream().map(column -> {
                return column.getTable();
            }).collect(Collectors.toSet());
            Set<String> tables2 = query.getFilters().stream().map(filter -> {
                return filter.getTable();
            }).collect(Collectors.toSet());
            tables.addAll(tables2);
            tables2.forEach(name ->{
                table.setName(name);
                table.setRelation(TableRelation.INNERJOIN.ordinal());
                query.getTables().add(table);
            });

        }
        //否则，直接建立表达
        return this.table;
    }

    public Query getQuery(){
        return this.query;
    }
}
