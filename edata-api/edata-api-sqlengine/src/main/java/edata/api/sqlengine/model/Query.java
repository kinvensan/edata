package edata.api.sqlengine.model;

import lombok.Data;

import java.util.List;

/**
 * QueryManager 用于解析Json中的一个查询对象
 * <pre>
 *     {query:{
 *         cloumns:[...],
 *         tables:[...],
 *         filters:[...]
 *     }}
 * </pre>
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
@Data
public class Query {
    private List<Column> columns;
    private List<Table> tables;
    private List<Filter> filters;
}
