package edata.api.sqlengine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Select is SQL part "select * from xxx"
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
public class Select {
    private List<SelectField> fields = new ArrayList<>();

    public String toSql(){
        StringBuffer sb = new StringBuffer();
        sb.append(Query.Select);
        for(int i = 0 ; i < fields.size();i++){
            SelectField field = fields.get(i);
            if(field.getExpression() != null ){
                sb.append(field.getExpression());
                sb.append(field.getAsName());
            } else {
                sb.append(field.getName());
            }
        }
    }
}
