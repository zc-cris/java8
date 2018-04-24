package com.zc.cris.InterfaceDefaultMethod;

public class TestDefaultMethod implements MyInterface, MyInterface1 {

    public static void main(String[] args) {
        // 方法冲突的时候，默认继承父类优先
        // 方法冲突的时候，多接口，无父类的情况下，需要显示复写哪个接口的默认方法
        System.out.println(new TestDefaultMethod().getName());

        // 直接调用接口中的静态方法
        MyInterface1.say();

    }


    @Override
    public String getName() {
//        return MyInterface.super.getName();
        return MyInterface1.super.getName();
    }
}
