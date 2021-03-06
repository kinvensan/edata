package com.github.edata.sql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TemplateFunctionConfig
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.FIELD})
public @interface TemplateFunctionConfig {
}
