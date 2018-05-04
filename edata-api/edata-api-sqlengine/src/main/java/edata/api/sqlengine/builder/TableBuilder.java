package edata.api.sqlengine.builder;

/**
 * TableBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/1
 */
public class TableBuilder {

    public static TableBuilder builder(){
        return new TableBuilder();
    }

    public TableBuilder build(){
        return this;
    }
}
