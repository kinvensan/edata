package edata.api.sqlengine.builder;

import edata.api.sqlengine.SqlException;
import edata.api.sqlengine.model.Column;
import edata.api.sqlengine.model.Filter;
import edata.api.sqlengine.model.Query;
import edata.api.sqlengine.model.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * QueryBuilder 读取Json文件，并把Json文件中内容转换为Query对象
 * 经过函数替换，以及表达式构建，把Query对象构建完毕。
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/3
 */
public class QueryBuilder {

    private Query query;

    public static QueryBuilder builder(){
        return new QueryBuilder();
    }

    public QueryBuilder query(Query query){
        this.query = query;
        return this;
    }

    /**
     * 获取Query对象
     * @return
     */
    public Query getQuery(){
        return query;
    }

    public Query build(){
        //如果没有Column，则抛出异常

        buildTables();
        buildFilters();



        //重新构建每一个Column
        //重新构建每一个Table
        //重新构建每一个Filter
        return this.query;
    }

    private void buildColumns(){
        if(null == query.getColumns() || 0==query.getColumns().size()){
            throw new SqlException();
        }
        query.getColumns().forEach(column -> {
            ColumnBuilder.builder().column(column).build();
        });
    }

    private void buildFilters(){
        if(null == query.getFilters() || 0==query.getFilters().size()){
          query.setFilters(new ArrayList<>());
        }
        query.getFilters().forEach(filter -> {
            FilterBuilder.builder().filter(filter).build();
        });
        if(query.getFilters().size() >= 1){
            FilterBuilder.builder().firstFilter(query.getFilters().get(0)).build();
        }
    }

    /**
     * 如果没有Table，则构建一个Table
     */
    private void buildTables() {
        if(null == query.getTables() || 0==query.getTables().size()){
            Set<String> tableNames = getTablesFromColumn(query.getColumns());
            tableNames.addAll(getTablesFromFilter(query.getFilters()));
            List<Table> tables = tableNames.stream().map(name -> {
                return TableBuilder.builder().name(name).build();
            }).collect(Collectors.toList());
            query.setTables(tables);
        }
        query.getTables().forEach(table -> {
            TableBuilder.builder().table(table).build();
        });
        //把第一个表的关系删除
        TableBuilder.builder().firstTable(query.getTables().get(0)).build();
    }

    private Set<String> getTablesFromColumn(List<Column> columns){
        return columns.stream().map(column -> column.getTable()).collect(Collectors.toSet());
    }

    private Set<String> getTablesFromFilter(List<Filter> filters) {
        return filters.stream().map(filter -> filter.getTable()).collect(Collectors.toSet());
    }

}
