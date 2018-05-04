package edata.api.sqlengine.model;

import edata.api.sqlengine.type.FilterRelation;
import edata.api.sqlengine.type.TableRelation;
import lombok.Data;
import lombok.NonNull;

/**
 * Filter 用于解析Json中的过滤部分
 * <pre>
 *     filters:[{
 *         name:"c"
 *         table:"table"
 *         relation:1
 *         expr:{
 *             body:"",
 *             attr:{},
 *             params:[]
 *         }
 *     }]
 * </pre>
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
@Data
public class Filter {
    @NonNull
    private String name;  //字段的名称
    @NonNull
    private String table; //字段对应的表格
    private int relation = 0; //前置关系 0 为空，1 and，2 or
    private Expression expr;
}
