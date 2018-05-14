package com.github.edata.sql.model;

import java.util.StringJoiner;

/**
 * SQL99 SQL'92标准的常量关键字
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/3
 */
public class SQL99 {
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
    public final static String USING = "using";
    public final static String BRACKETLEFT = "(";
    public final static String BRACKETRIGHT = ")";
    public final static String ASC = "asc";
    public final static String DESC = "desc";



    public static StringJoiner SELECTJOINER() {
        return new StringJoiner(COMMA,SELECT+SPACE,EMPTY);
    }

    public static StringJoiner FROMJOINER() {
        return new StringJoiner(SPACE,FROM,EMPTY);
    }
    public static StringJoiner USINGJOINER() {
        return new StringJoiner(COMMA,USING+BRACKETLEFT,BRACKETRIGHT);
    }

    public static StringJoiner WHEREJOINER() {
        return new StringJoiner(SPACE,WHERE,EMPTY);
    }

    public static StringJoiner GROUPBYJOINER() {
        return new StringJoiner(COMMA,GROUPBY+SPACE,EMPTY);
    }

    public static StringJoiner ORDERBYJOINER() {
        return new StringJoiner(COMMA,ORDERBY+SPACE,EMPTY);
    }

    public static StringJoiner SPACEJOINER() {return new StringJoiner(SPACE);}

}
