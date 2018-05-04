package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Filter;

/**
 * FilterBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class FilterBuilder {

    private Filter filter;

    public static FilterBuilder builder(){
        FilterBuilder builder = new FilterBuilder();
        return builder;
    }

    public FilterBuilder filter(Filter filter){
        this.filter = filter;
        return this;
    }

    /**
     * 处理表达式
     * @return
     */
    public Filter build() {

        return this.filter;
    }
}
