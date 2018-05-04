package edata.api.sqlengine.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Expr
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
@Data
public class Expression {
    private String func = "";
    private String body = "";
    private List<Object> params = new ArrayList<>();
}
