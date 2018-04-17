package com.zc.cris;

import java.util.List;

// 自定义一个策略接口
@FunctionalInterface
public interface MyStrategy<T> {

    public boolean filter(T t);
}
