package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SelectBuilder 把对应的Select和需要的Select字段输出。
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class SelectBuilder {

    private List<Column> columns;

    public static SelectBuilder builder(){
        SelectBuilder selectBuilder = new SelectBuilder();
        return selectBuilder;
    }

    public SelectBuilder columns(List<Column> columns){
        if(this.columns == null ){
            this.columns = new ArrayList<>();
        }
        this.columns.addAll(columns);
        return this;
    }

    /**
     * 过滤不输出的字段，并对输出字段进行Column的字段重建。
     * @return
     */
    public SelectBuilder build(){
        List<Column> selectColumns = this.columns.stream()
                .filter(column -> 1 == column.getHidden())
                .collect(Collectors.toList());
        selectColumns.forEach(column -> ColumnBuilder.builder().column(column).build());
        this.columns = selectColumns;

        return this;
    }
}
