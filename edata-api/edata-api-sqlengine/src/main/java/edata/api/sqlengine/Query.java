package edata.api.sqlengine;

/**
 * Created by kinven on 2018/4/26.
 */
public class Query {
    public static final String Select = "select ";
    public static final String From = " from ";
    public static final String WHERE = " where ";
    public static final String GROUP_BY = " group by ";
    public static final String ORDER_BY = " order by ";
    private Select select;
    private From from;
    private Where where;
    private GroupBy groupBy = new GroupBy();
    private OrderBy orderBy = new OrderBy();



    public String toSql(){
        StringBuffer sb = new StringBuffer();
        if(groupBy.size() > 0) {
            sb.append(groupBy.toSql());
        }
        if(orderBy.size() > 0) {
            sb.append(orderBy.toSql());
        }
        return sb.toString();
    }


}
