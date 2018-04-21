package com.zc.cris;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 * /*
 * * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * * 			  （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * *
 * * 1. 对象的引用 :: 实例方法名
 * *
 * * 2. 类名 :: 静态方法名
 * *
 * * 3. 类名 :: 实例方法名
 * *
 * * 注意：
 * * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 * * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 * *
 * * 1. 类名 :: new
 * *
 * * 三、数组引用
 * *
 * * 	类型[] :: new;
 * *
 */
public class TestMethodRef {

    //对象 :: 实例方法名（lambda接口的抽象方法的参数和返回值必须和实例对象方法的参数及返回值保持一致）
    @Test
    public void test1() {
        // 普通写法
//     Consumer<String> con = x -> System.out.print(x);
        // 方法引用写法
        Consumer<String> con = System.out::println;     //直接写方法名即可


        con.accept("afafa");


    }

    @Test
    public void test2() {
        Employee employee = new Employee("cris", 23, 343545.34);
//        Supplier<String> sup2 = () -> employee.getName();
        Supplier<String> sup = employee::getName;
        System.out.println(sup.get());

        Supplier<Integer> supplier = employee::getAge;
        System.out.println(supplier.get());
    }

    // 类::静态方法名(lambda接口的抽象方法的参数和返回值必须和类静态方法的参数及返回值保持一致)
    @Test
    public void test3() {
        // 函数式接口除了自定义，还可以使用jdk内置的四种函数式接口及扩展，以及替换掉匿名内部类（如：Runnable接口...）
        // 后面两种是最常用的
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        System.out.println(comparator.compare(2, 5));

        Comparator<Integer> comparator1 = Integer::compare;         // 可以直接调用类里面的静态方法
        System.out.println(comparator1.compare(5, 2));

    }

    // 类::实例方法名(若lambda体中的第一个参数是实例方法的调用者，第二个参数是实例方法的参数(或者没有第二个参数)，可以这么使用)
    @Test
    public void test4() {
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        System.out.println(biPredicate.test("cris", "cris4"));

        BiPredicate<String, String> biPredicate1 = String::equals;
        System.out.println(biPredicate1.test("cris", "cris"));
    }

    // 构造器引用(参数类型和个数必须和构造器对应，内置函数式接口的泛型含义固定，不能随意更改)
    @Test
    public void test5() {
        Supplier<Employee> supplier = () -> new Employee();
        System.out.println(supplier.get());

        Supplier<Employee> supplier1 = Employee::new;
        System.out.println(supplier1.get());

        Function<Integer, Employee> function = Employee::new;
        System.out.println(function.apply(23));

        BiFunction<String, Integer, Employee> function1 = Employee::new;
        System.out.println(function1.apply("cris", 22));
    }

    // 数组引用
    @Test
    public void test6() {
        Function<Integer, Employee[]> function = (x) -> new Employee[x];
        System.out.println(function.apply(4).length);

        Function<Integer, String[]> function1 = String[]::new;
        System.out.println(function1.apply(12).length);

        Supplier<List<String>> supplier = ArrayList::new;
        System.out.println(supplier.get().size());

    }

    /**
     * 声明函数式接口，接口中声明一个抽象方法，public String getValue(String str)
     * 1. 编写方法，将函数式接口作为参数，将一个字符串转换为大写，并作为方法的返回值
     * 2. 将字符串的第二个到第四个位置截取（留头不留尾）
     */
    @Test
    public void test7() {
        Function<String, String> function = String::toUpperCase;
        System.out.println(function.apply("cris, i love u"));

        String str = "afafaf";
        BiFunction<Integer, Integer, String> function1 = str::substring;
        System.out.println(function1.apply(1, 4));
    }

    /**
     * 声明一个带有两个泛型的函数式接口，T 为参数，R 为返回值
     * 接口中声明一个抽象方法
     * 在 test 类中声明一个方法，指定这个函数式接口为参数，计算两个 Long 型参数的和
     * 再计算两个 Long 型参数的积
     */
    @Test
    public void test8() {
        BiFunction<Long, Long, Long> biFunction = (x, y) -> x + y;
        System.out.println(biFunction.apply(23L, 34L));

        // 比较两个值的最大值（类名::静态方法）
        BiFunction<Integer, Integer, Integer> biFunction1 = Integer::max;
        System.out.println(biFunction1.apply(34, 23));

    }


}



