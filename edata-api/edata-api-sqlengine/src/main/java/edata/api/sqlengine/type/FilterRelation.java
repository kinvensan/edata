package edata.api.sqlengine.type;

import edata.api.sqlengine.model.SQL92;

/**
 * FilterRelation
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-4
 **/
public enum FilterRelation {
    NONE,
    AND,
    OR;

    public static FilterRelation from(int index){
        switch (index){
            case 0 : return FilterRelation.NONE;
            case 1 : return FilterRelation.AND;
            case 2 : return FilterRelation.OR;
            default: return FilterRelation.NONE;
        }
    }

    public static String getString(FilterRelation filterRelation){
        switch (filterRelation){
            case NONE : return SQL92.EMPTY;
            case AND  : return SQL92.AND;
            case OR   : return SQL92.OR;
            default   : return SQL92.EMPTY;
        }
    }

    public static String getString(int index){
        return getString(from(index));
    }
}
