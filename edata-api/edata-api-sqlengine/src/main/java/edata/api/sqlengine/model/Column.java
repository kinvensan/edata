package edata.api.sqlengine.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Column 用于解析Columns中的字段内容
 * <pre>
 *     columns:[{
 *         name: "a",
 *         table: "table1",
 *         typename:"string",
 *         uiname:"字段1"
 *         expr:{
 *          body:"",
 *          attr:{},
 *          params:[]
 *         },
 *         aggregate:0 //0 维度 ，1 聚合
 *         orderby:0
 *         hidden:0
 *     }]
 * </pre>
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
@Data
public class Column {
    @NonNull
    private String name;//字段名称，如果表达式存在。
    @NonNull
    private String table; //字段所在的表
    private String typename;//字段的类型
    private String uiname; //字段显示的名称
    private String asName; //字段输出名称
    private short aggregate = 0; //0，不是聚合字段, 1是聚合字段
    private short orderby = 0;//0 不排序，1 升序 aesc 2 降序desc
    private short hidden = 0; //0,不显示，1 显示，用于作为排序字段的使用。

    private Expression expr; //Sql表达式
}
