package com.zc.cris.annotation;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
    重复注解和类型注解
 */
public class TestAnnotation {

    // private @NotNull Object; 框架实现了指定的注解，可以提供编译时检查，对数据类型做出规范

    // 获取重复注解的属性值
    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<TestAnnotation> testAnnotationClass = TestAnnotation.class;
        Method say = testAnnotationClass.getMethod("say");

        MyAnnotation[] annotationsByType = say.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation myAnnotation : annotationsByType) {
            System.out.println(myAnnotation.value());
        }
    }

    @MyAnnotation("jack")
    @MyAnnotation("老二爹")
    public void say(@MyAnnotation("123456") String password) {
        System.out.println("say!!!!!!!");
    }
}
