package com.github.edata.sql.type;

import com.github.edata.sql.model.SQL99;

/**
 * OrderbyType
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/10
 */
public enum OrderbyType {
    NONE,
    ASC,
    DESC;


    public static OrderbyType from(int index){
        switch (index){
            case 0 : return OrderbyType.NONE;
            case 1 : return OrderbyType.ASC;
            case 2 : return OrderbyType.DESC;
            default: return OrderbyType.NONE;
        }
    }

    public static String getString(OrderbyType orderbyType){
        switch (orderbyType){
            case NONE:  return SQL99.EMPTY;
            case ASC:   return SQL99.ASC;
            case DESC:  return SQL99.DESC;
            default: return SQL99.EMPTY;
        }
    }

    public static String getString(int index){
        return getString(from(index));
    }


}
