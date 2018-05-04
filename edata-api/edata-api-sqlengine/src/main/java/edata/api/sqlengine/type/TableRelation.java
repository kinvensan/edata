package edata.api.sqlengine.type;

import edata.api.sqlengine.model.SQL92;
import edata.api.sqlengine.model.Table;

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
            case NONE:      return SQL92.EMPTY;
            case INNERJOIN: return SQL92.INNERJOIN;
            case LEFTJOIN:  return SQL92.LEFTJOIN;
            case RIGHTJOIN: return SQL92.RIGHTJOIN;
            default: return SQL92.EMPTY;
        }
    }

    public static String getString(int index){
        return getString(from(index));
    }


}
