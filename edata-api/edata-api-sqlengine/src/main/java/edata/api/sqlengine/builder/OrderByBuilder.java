package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderByBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class OrderByBuilder {
    private List<Column> columns;

    public static OrderByBuilder builder(){
        OrderByBuilder orderByBuilder = new OrderByBuilder();
        return orderByBuilder;
    }

    public OrderByBuilder columns(List<Column> columns){
        if(this.columns == null){
            this.columns = new ArrayList<>();
        }
        this.columns.addAll(columns);
        return this;
    }

    public OrderByBuilder build() {
        List<Column> orderByColumns = this.columns.stream()
                .filter(column -> 0==column.getOrderby() )
                .collect(Collectors.toList());
        orderByColumns.forEach(column -> ColumnBuilder.builder().column(column).build());
        this.columns = orderByColumns;
        return this;
    }
}
