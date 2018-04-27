package edata.api.sqlengine;

import java.util.List;

/**
 * From is SQL part "select * from xxx"
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
public class From {
    private String table;
    private List<FromJoin> joins;

    public String toSql(){
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }
}
