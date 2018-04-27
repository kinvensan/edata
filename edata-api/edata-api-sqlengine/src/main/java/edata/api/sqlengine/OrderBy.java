package edata.api.sqlengine;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderBy
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/27
 */
public class OrderBy {
    private List<SelectField> fields = new ArrayList<>();

    public List<SelectField> getFields() {

        return fields;
    }

    public void addField(int index,SelectField field){
        fields.add(index,field);
    }

    public void removeField(int index){
        fields.remove(index);
    }

    public void addField(SelectField field){
        fields.add(field);
    }

    public void removeField(SelectField field){
        fields.remove(field);
    }

    public int size(){
        return fields.size();
    }

    public String toSql(){
        StringBuffer sb = new StringBuffer();
        sb.append(Query.ORDER_BY);
        for(SelectField field:fields){
            sb.append(field.getAsName());
            if(size() > 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
