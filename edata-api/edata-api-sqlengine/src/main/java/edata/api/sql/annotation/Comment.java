package edata.api.sql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Comment
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-9
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
public @interface Comment {
    public String value();
}
