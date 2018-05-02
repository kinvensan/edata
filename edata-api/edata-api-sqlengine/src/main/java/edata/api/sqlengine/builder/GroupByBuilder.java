package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GroupByBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class GroupByBuilder {
    private List<Column> columns;
    public static GroupByBuilder builder(){
        GroupByBuilder groupByBuilder = new GroupByBuilder();
        return groupByBuilder;
    }

    public GroupByBuilder columns(List<Column> columns){
        if(this.columns == null){
            this.columns = new ArrayList<>();
        }
        this.columns.addAll(columns);
        return this;
    }

    /**
     * 筛选出可以组成GroupBy部分的字段
     * @return
     */
    public GroupByBuilder build(){
        List<Column> groupByColumns = this.columns.stream()
                .filter(selectColumn -> 1==selectColumn.getAggregate())
                .collect(Collectors.toList());
        groupByColumns.forEach(column -> ColumnBuilder.builder().column(column).build());
        this.columns = groupByColumns;
        return this;
    }
}
