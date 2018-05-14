package com.github.edata.sql.model;

import lombok.Data;

import java.util.ArrayList;
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
    private List<Column> columns = new ArrayList<>();
    private List<Table> tables = new ArrayList<>();
    private List<Filter> filters = new ArrayList<>();
}
