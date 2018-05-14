package com.github.edata.sql.type;

import com.github.edata.sql.model.SQL99;

/**
 * TableRelation
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public enum TableRelation {

    NONE,
    INNERJOIN,
    LEFTJOIN,
    RIGHTJOIN;

    public static TableRelation from(int index){
        switch (index){
            case 0 : return TableRelation.NONE;
            case 1 : return TableRelation.INNERJOIN;
            case 2 : return TableRelation.LEFTJOIN;
            case 3 : return TableRelation.RIGHTJOIN;
            default: return TableRelation.NONE;
        }
    }

    public static String getString(TableRelation tableRelation){
        switch (tableRelation){
            case NONE:      return SQL99.EMPTY;
            case INNERJOIN: return SQL99.INNERJOIN;
            case LEFTJOIN:  return SQL99.LEFTJOIN;
            case RIGHTJOIN: return SQL99.RIGHTJOIN;
            default: return SQL99.EMPTY;
        }
    }

    public static String getString(int index){
        return getString(from(index));
    }


}
