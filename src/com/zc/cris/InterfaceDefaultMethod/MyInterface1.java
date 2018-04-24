package com.zc.cris.InterfaceDefaultMethod;

public interface MyInterface1 {
    public static void say() {
        System.out.println("cirs so cool！");
    }

    default String getName() {
        return "嘿嘿嘿";
    }
}
