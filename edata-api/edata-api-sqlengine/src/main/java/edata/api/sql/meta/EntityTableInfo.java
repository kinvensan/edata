package edata.api.sql.meta;

import edata.api.sql.kit.StringKit;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * EntityTableInfo
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/6
 */
public class EntityTableInfo {

    // 实体映射的数据库表名
    private String table;
    private Class<?> entityClass;

    // Java属性映射的数据库字段集合（column -> property）
    private Map<String, EntityColumnInfo> colsIndex = new CaseInsensitiveHashMap();

    // join使用的keys(column->table)
    private List<String> joinColumnIndex = new ArrayList<>();


    public EntityTableInfo(){

    }

    public void register(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table tableAnno = entityClass.getAnnotation(Table.class);
            if (StringKit.isNotBlank(tableAnno.name())) {
                this.table = tableAnno.name();
                this.entityClass = entityClass;
            }
            // 列
            List<Field> fieldList = getAllField(entityClass, null);
            for (Field field : fieldList) {
                EntityColumnInfo entityColumnInfo = new EntityColumnInfo();
                entityColumnInfo.register(field);

                //构建各种索引;
                if(null != entityColumnInfo.getColumnName()) {
                    colsIndex.put(entityColumnInfo.getColumnName(), entityColumnInfo);
                }
                if(null != entityColumnInfo) {
                    joinColumnIndex.add(entityColumnInfo.getJoinColumn());
                }


            }
        }
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTable() {
        return table;
    }

    public List<String> getJoinColumns() {
        return joinColumnIndex;
    }

    public void something(Field field) {
        String propName = field.getName();
        Method method = null;
        try {
          //符合JavaBean规范的get方法名称（userName=>getUserName,uName=>getuName）
          method = entityClass.getMethod("get"+(propName.length()>1&&propName.charAt(1)>='A'&&propName.charAt(1)<='Z'?propName:StringKit.toUpperCaseFirstOne(propName)));
        } catch (Exception e) {
          //没有找到getMethod，无需处理
        }
        Column column = null;
        if (field.isAnnotationPresent(Column.class)) {
          column = field.getAnnotation(Column.class);
        } else if (method!=null&&method.isAnnotationPresent(Column.class)) {
          column = method.getAnnotation(Column.class);
        }
        String columnName = null;
        if (column != null){
          columnName = column.name();
        }
        // 没有@Transient 注解 才储存 prop=>col 映射关系，否则不存储
        if(!field.isAnnotationPresent(Transient.class)&&(method==null||!method.isAnnotationPresent(Transient.class))){
        //                    addCol(propName, columnName);
        }
        //                addProp(columnName, propName);

        JoinColumn joinColumn = null;
        if (field.isAnnotationPresent(JoinColumn.class)) {
          joinColumn = field.getAnnotation(JoinColumn.class);
        } else if (method!=null&&method.isAnnotationPresent(Column.class)) {
          joinColumn = method.getAnnotation(JoinColumn.class);
        }
        if(joinColumn != null) {
        //                    addJoinColumn(joinColumn.name(),table);
        }
    }



    /**
     * 获取全部的Field
     *
     * @param entityClass
     * @param fieldList
     * @return
     */
    private static List<Field> getAllField(Class<?> entityClass, List<Field> fieldList) {
        if (fieldList == null) {
            fieldList = new ArrayList<Field>();
        }
        if (entityClass.equals(Object.class)) {
            return fieldList;
        }
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            // 排除静态字段，解决bug#2
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }
        }
        Class<?> superClass = entityClass.getSuperclass();
        if (superClass != null && !superClass.equals(Object.class) &&!Map.class.isAssignableFrom(superClass) && !Collection.class.isAssignableFrom(superClass)) {
            return getAllField(entityClass.getSuperclass(), fieldList);
        }
        return fieldList;
    }

}
