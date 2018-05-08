package edata.api.sqlengine.model;

import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Expr 表达是解析，使用
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
@Data
public class Expression {
    @NonNull
    private String func;
    private String body;
    private Map<String,Object> params ;

    public Expression(){
        this.func = "";
        this.body = "";
        this.params = new HashMap<>();
    }
}
