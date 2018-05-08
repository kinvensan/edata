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
public class QueryBuilder implements Builder<Query> {

    private Query query;

    public static QueryBuilder builder(){
        return new QueryBuilder();
    }

    @Override
    public QueryBuilder from(Query query) {
        this.query = query;
        return this;
    }

    @Override
    public QueryBuilder newOne() {
        this.query = new Query();
        this.query.setColumns(new ArrayList<>());
        this.query.setTables(new ArrayList<>());
        this.query.setFilters(new ArrayList<>());
        return this;
    }

    public QueryBuilder columns(List<Column> columns){
        this.query.setColumns(columns);
        return this;
    }

    public QueryBuilder addColumn(Column column) {
        if(null == this.query.getColumns()){
            this.query.setColumns(new ArrayList<>());
        }
        this.query.getColumns().add(column);
        return this;
    }


    public QueryBuilder tables(List<Table> tables){
        this.query.setTables(tables);
        return this;
    }

    public QueryBuilder addTable(Table table) {
        if(null == this.query.getTables() || this.query.getTables().isEmpty()){
            this.query.setTables(new ArrayList<>());
        }
        this.query.getTables().add(table);
        return this;
    }


    public QueryBuilder filters(List<Filter> filters) {
        this.query.setFilters(filters);
        return this;
    }

    public QueryBuilder addFilter(Filter filter) {
        if(null == this.query.getFilters() || this.query.getFilters().isEmpty()) {
            this.query.setFilters(new ArrayList<>());
        }
        this.query.getFilters().add(filter);
        return this;
    }

    public Query build(){
        //如果没有Column，则抛出异常

        buildTables();
        buildColumns();
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
            ColumnBuilder.builder().from(column).build();
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
            query.getFilters().get(0).setRelation(0);
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
                return TableBuilder.builder().newOne().name(name).build();
            }).collect(Collectors.toList());
            query.setTables(tables);
        }
        query.getTables().forEach(table -> {
            TableBuilder.builder().from(table).build();
        });
        //把第一个表的关系删除
        query.getTables().get(0).setRelation(0);
    }

    private Set<String> getTablesFromColumn(List<Column> columns){
        return columns.stream().map(column -> column.getTable()).collect(Collectors.toSet());
    }

    private Set<String> getTablesFromFilter(List<Filter> filters) {
        return filters.stream().map(filter -> filter.getTable()).collect(Collectors.toSet());
    }

}
