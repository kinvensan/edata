package edata.api.sql.meta;


import edata.api.sql.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import java.lang.reflect.Field;

/**
 * EntityColumnInfo
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/10
 */
public class EntityColumnInfo {
    private String columnName;
    private String propName;
    private String joinColumn;
    private String comment;
    private Class<?> columnType;

    public EntityColumnInfo(){

    }

    /**
     * 不在Get方法上做处理
     * @param field
     */
    public void register(Field field){
        if(field.isAnnotationPresent(Column.class)&&!field.isAnnotationPresent(Transient.class)) {
            Column columnAnno = field.getAnnotation(Column.class);
            this.columnName = columnAnno.name();
            this.propName = field.getName();
            this.columnType = field.getType();
            if(field.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn joinColumnAnno = field.getAnnotation(JoinColumn.class);
                this.joinColumn = joinColumnAnno.name();
            }
            if(field.isAnnotationPresent(Comment.class)) {
                Comment commentAnno = field.getAnnotation(Comment.class);
                this.comment = commentAnno.value();
            }
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public String getPropName() {
        return propName;
    }

    public Class<?> getColumnType() {
        return columnType;
    }

    public String getJoinColumn() {
        return joinColumn;
    }
}
