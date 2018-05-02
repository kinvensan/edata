package edata.api.sqlengine.query;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * SelectColumn is SQL part "select field as a from xxx"
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */

@Data
@RequiredArgsConstructor
@Builder
public class SelectColumn {
    @NonNull
    private Integer id; //用于元表中定义的字段
    @NonNull
    private String name;//字段名称，如果表达式存在。
    @NonNull
    private String table; //字段所在的表
    private String typeName;//字段的类型
    private String uiName; //字段显示的名称
    private String asName; //字段输出名称
    private short aggregate = 0; //0，不是聚合字段, 1是聚合字段
    private short dimension = 0;//0，未知，1是维度字段，2是维度关联字段
    private short orderby = 0;//0 不排序，1 升序 aesc 2 降序desc
    private short hidden = 0; //0,不显示，1 显示，用于作为关联字段的使用。

    private BaseExpression expression; //Sql表达式
}
