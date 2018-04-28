package edata.api.sqlengine;

/**
 * WhereFilter
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
public class WhereFilter {
    private String name;
    private String expression;
    private String AndOrType;

    public String toSql() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }
}
