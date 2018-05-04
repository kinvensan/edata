package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Query;

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

    /**
     * 获取Query对象
     * @return
     */
    public Query getQuery(){
        return query;
    }

    public Query build(){
        //如果没有Column，则抛出异常
        //如果没有Table，则构建一个Table

        //重新构建每一个Column
        //重新构建每一个Table
        //重新构建每一个Filter
        return this.query;
    }
}
