package edata.api.sqlengine.builder;

import edata.api.sqlengine.model.Column;

import java.util.Map;

/**
 * ColumnBuilder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public class ColumnBuilder {

    private Column column;

    public static ColumnBuilder builder(){
        ColumnBuilder selectColumnBuilder = new ColumnBuilder();
        return selectColumnBuilder;
    }

    public ColumnBuilder column(Column column){
        this.column = column;
        return this;
    }

    public ColumnBuilder build(){
        //如果asName为空 ，则处理为Name
        if(column.getAsName() == null){
            column.setAsName(column.getName());
        }
        //todo 获取Expression类型，并确定处理类
        //添加expression的Contents的内容，并设置为是否聚合
        if(null == column.getExpr()){

        }
        return this;
    }




}
