package com.github.edata.sql.builder;

/**
 * Builder
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
public interface Builder<T> {

    public <E extends Builder<T>> E from(T t);
    public <E extends Builder<T>> E newOne();
    public T build();
}
