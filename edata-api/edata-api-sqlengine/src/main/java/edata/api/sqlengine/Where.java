package edata.api.sqlengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Where
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
public class Where {
    List<WhereFilter> filters = new ArrayList<>();

    public String toSql(){
        StringBuffer sb = new StringBuffer();



        return sb.toString();
    }
}
