package com.zc.cris;

@FunctionalInterface
public interface MyLongFunction<T, R> {

    public R function(T t1, T t2);
}
