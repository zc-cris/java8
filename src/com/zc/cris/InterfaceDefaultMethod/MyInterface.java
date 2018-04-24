package com.zc.cris.InterfaceDefaultMethod;

public interface MyInterface {

    default String getName() {
        return "哈哈哈";
    }
}
