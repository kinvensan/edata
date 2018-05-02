package edata.api.sqlengine.query;

import edata.api.sqlengine.type.RelationType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * WhereColumn
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
@Data
@RequiredArgsConstructor
@Builder
public class WhereColumn {
    @NonNull
    private String name;  //字段的名称
    @NonNull
    private String table; //字段对应的表格
    private RelationType relationType = RelationType.AND; //前置关系 0 为空，1 and，2 or

    private BaseExpression expression;

}
