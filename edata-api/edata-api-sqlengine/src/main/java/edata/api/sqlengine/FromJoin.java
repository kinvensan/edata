package edata.api.sqlengine;

/**
 * FromJoin
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
public class FromJoin {
    private String table; // join table名称
    private JoinType joinType = JoinType.left; // join类型，left join , inner join ,right join
    private String onExpression;
}
