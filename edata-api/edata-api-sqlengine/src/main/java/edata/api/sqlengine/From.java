package edata.api.sqlengine;

import java.util.*;

/**
 * From is SQL part "select * from xxx"
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
public class From {
    private String table;
    private List<FromJoin> joins= new ArrayList<>();
    private Set<String> tables = new LinkedHashSet<>();//要保证set是顺序的。


    public String getAsTalbeName(String tableName){
        if(table.equalsIgnoreCase(tableName)){
            return "T1";
        } else {
            for (int i = 0; i < joins.size(); i++) {
                FromJoin fromJoin = joins.get(i);
                if (fromJoin.getTable().equalsIgnoreCase(tableName)) {
                    return "T" + (i + 2);
                }
            }
        }
        throw new RuntimeException("not found tablename " + tableName);
    }

    public String toSql(){
        StringBuffer sb = new StringBuffer();
        sb.append(Query.From).append(table).append(" ");
        for(int i = 0;i < joins.size(); i++){
            FromJoin fromJoin = joins.get(i);
            sb.append(fromJoin.toSql());
        }
        return sb.toString();
    }
}
