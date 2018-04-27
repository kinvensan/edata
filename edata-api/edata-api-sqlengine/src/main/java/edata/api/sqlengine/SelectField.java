package edata.api.sqlengine;

/**
 * SelectField is SQL part "select field as a from xxx"
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
public class SelectField {

    private String name;//字段名称
    private String asName; //字段别名
    private String uiName; //字段显示的名称
    private String expression; //字段表达式
    private short aggregate = 0; //0，不是聚合字段, 1是聚合字段
    private short dimension = 0;//0，不是维度字段，1是维度字段
    private short orderby = 0;//0 不排序，1 升序 aesc 2 降序desc
    private short display = 1; //0,不显示，1 显示，用于作为关联字段的使用。
    private String table; //字段所在的表


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsName() {
        return asName;
    }

    public void setAsName(String asName) {
        this.asName = asName;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


}
