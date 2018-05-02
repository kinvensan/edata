package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Filter;
import edata.api.sqlengine.type.RelationType;

import java.util.ArrayList;
import java.util.List;

/**
 * WhereBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class WhereBuilder {

    private List<Filter> filters;

    public static WhereBuilder builder(){
        WhereBuilder whereBuilder = new WhereBuilder();
        return whereBuilder;
    }

    public WhereBuilder columns(List<Filter> columns){
        if(this.filters == null){
            this.filters = new ArrayList<>();
        }
        this.filters.addAll(columns);
        return this;
    }

    /**
     * 确认Column的个数，并把第一个and关系删除。
     * @return
     */
    public WhereBuilder build() {
        if(filters.size() > 0){
            this.filters.get(0).setRelation(RelationType.NONE);
        }
        filters.forEach(filter -> FilterBuilder.builder().filter(filter).build());
        return this;
    }
}
