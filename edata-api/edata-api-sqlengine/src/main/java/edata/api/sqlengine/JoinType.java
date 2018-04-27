package edata.api.sqlengine;

/**
 * JoinType
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
public enum JoinType {
    left("left join"),
    inner("inner join"),
    right("right join");

    private String joinString;
    JoinType(String joinString){
        this.joinString = joinString;
    }

    public String toString(){
        return this.joinString;
    }

    public static JoinType from(String joinType){
        if(joinType.equals("left")){
            return left;
        } else if(joinType.equals("inner")){
            return inner;
        } else if(joinType.equals("right")) {
            return right;
        } else {
            throw new RuntimeException("Not Found " + joinType);
        }
    }

}
