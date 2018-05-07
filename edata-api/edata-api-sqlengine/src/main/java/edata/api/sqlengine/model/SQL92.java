package edata.api.sqlengine.model;

import java.util.StringJoiner;

/**
 * SQL92 SQL'92标准的常量关键字
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/3
 */
public class SQL92 {
    public final static String SELECT = "select";
    public final static String SPACE = " ";
    public final static String FROM = "from";
    public final static String WHERE = "where";
    public final static String GROUPBY = "group by";
    public final static String ORDERBY = "order by";
    public final static String ON = "on";
    public final static String AS = "as";
    public final static String LEFTJOIN = "left join";
    public final static String INNERJOIN = "inner join";
    public final static String RIGHTJOIN = "right join";
    public final static String COMMA = ",";
    public final static String EMPTY = "";
    public final static String AND = "and";
    public final static String OR = "or";
    public final static String DOT = ".";
    public final static String USING = "USING";
    public final static String BRACKETLEFT = "(";
    public final static String BRACKETRIGHT = ")";



    public static StringJoiner SELECT() {
        return new StringJoiner(COMMA,SELECT,EMPTY);
    }

    public static StringJoiner FROM() {
        return new StringJoiner(SPACE,FROM,EMPTY);
    }
    public static StringJoiner USING() {
        return new StringJoiner(COMMA,USING+BRACKETLEFT,BRACKETRIGHT);
    }

    public static StringJoiner WHERE() {
        return new StringJoiner(SPACE,WHERE,EMPTY);
    }

    public static StringJoiner GROUPBY() {
        return new StringJoiner(COMMA,GROUPBY,EMPTY);
    }

    public static StringJoiner ORDERBY() {
        return new StringJoiner(COMMA,ORDERBY,EMPTY);
    }

}
