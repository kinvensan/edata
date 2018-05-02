package edata.api.sqlengine.expression;

/**
 * FieldNameExpression
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class FieldNameExpression implements SqlExpression{

    String name;
    String table;
    String asName;

    @Override
    public void toSql(StringBuffer sb){
        sb.append(table).append(Query.DOT).append(name)
                .append(Query.SPACE).append(Query.AS).append(asName);

    }

}
