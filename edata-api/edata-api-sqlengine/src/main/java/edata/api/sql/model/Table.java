package edata.api.sql.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Table 用于解析Json中的Table对象
 * <pre>
 *     tables:[{
 *         name:"table",
 *         relation: 0,
 *         expr:{
 *             body:"",//autogen
 *             func:""
 *             params:[]
 *         }
 *     }]
 * </pre>
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
@Data
public class Table {
    @NonNull
    private String name; //表的名字
    private int relation = 1 ; //0 主表，1，innerjoin表，2 left join表 3，right join表
    private Expression expr = new Expression(); //on的表达式

    public Table(){

    }
}
