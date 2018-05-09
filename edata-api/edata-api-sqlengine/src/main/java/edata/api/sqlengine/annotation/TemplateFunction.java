package edata.api.sqlengine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TemplateFunction
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TemplateFunction {
    public String name();
}
