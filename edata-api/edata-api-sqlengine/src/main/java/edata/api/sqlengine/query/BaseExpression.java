package edata.api.sqlengine.query;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Properties;

/**
 * BaseExpression 用于处理表达式，例如一般的表达式，case when表达式（用于分层）等。
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
@Data
@RequiredArgsConstructor
@Builder
public class BaseExpression {
    private String type;
    private Properties properties;

    @NonNull
    private String contents;

    public void addProperty(String key,String value){
        properties.put(key,value);
    }
}
