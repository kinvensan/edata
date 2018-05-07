package edata.api.sqlengine.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class Expression {
    @NonNull
    private String func = "NONE";
    private String body = "";
    private List<Object> params = new ArrayList<>();
}
