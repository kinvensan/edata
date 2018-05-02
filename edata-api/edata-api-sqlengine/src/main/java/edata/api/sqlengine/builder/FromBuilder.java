package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Column;
import edata.api.sqlengine.model.Filter;
import edata.api.sqlengine.model.Table;
import edata.api.sqlengine.type.RelationType;

import java.util.ArrayList;
import java.util.List;

/**
 * FromBuilder 对FromJoin部分进行构建
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class FromBuilder {
    private List<Table> tables;

    public static FromBuilder builder() {
        FromBuilder fromBuilder = new FromBuilder();
        return fromBuilder;
    }

    public FromBuilder tables(List<Table> tables) {
        if (this.tables == null) {
            this.tables = new ArrayList<>();
        }
        this.tables.addAll(tables);
        return this;
    }

    public FromBuilder extra(List<Column> columns,List<Filter> filters){
        //从columns和filters中自动构建table的默认关联。
        return this;
    }

    /**
     *
     *
     * @return
     */
    public FromBuilder build() {

        return this;
    }
}
