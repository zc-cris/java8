package com.zc.cris;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
 * Java8 内置的四大核心函数式接口
 *
 * Consumer<T> : 消费型接口
 * 		void accept(T t);
 *
 * Supplier<T> : 供给型接口
 * 		T get();
 *
 * Function<T, R> : 函数型接口
 * 		R apply(T t);
 *
 * Predicate<T> : 断言型接口
 * 		boolean test(T t);
 *
 */
public class TestInnerFunctionInterface {

    // Predicate<T> 断言型接口
    @Test
    public void testPredicate() {
        System.out.println(filterString(Arrays.asList("fafe", "jia", "jk", "g"), s -> s.length() > 2));
    }

    // 需求：将一个字符串集合按照指定规则筛选出来
    public List<String> filterString(List<String> list, Predicate<String> pre) {
        List<String> stringList = new ArrayList<>();
        for (String s : list) {
            if (pre.test(s)) {            // 一个泛型指定参数类型，返回值为布尔
                stringList.add(s);
            }
        }
        return stringList;
    }

    // Function<T, R> 函数型接口
    @Test
    public void testFunction() {
        String str = function("\t\t\t\t 我真的不喜欢你   ", x -> x.trim());
        System.out.println(str);
        System.out.println(function("有的时候不是努力就可以成功的", x -> x.substring(2, 5)));

    }

    public String function(String str, Function<String, String> fun) {
        return fun.apply(str);  // 函数型接口，两个泛型，分别指定参数类型和返回值类型
    }


    // Supplier<T> 供给型接口
    @Test
    public void testSupplier() {
        List<Integer> list = supplier(5, () -> (int) (Math.random() * 100));
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    // 需求：每次产生指定数量的随机数，返回一个list集合
    public List<Integer> supplier(Integer num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer integer = sup.get();    // 供给型接口的抽象方法没有参数，只有对调用方的返回值
            list.add(integer);
        }
        return list;
    }


    // Consumer<T> 消费型接口
    @Test
    public void testConsumer() {
        consumer(12345D, d -> System.out.println("这是消费性接口，每次消费" + d + "元"));
    }

    public void consumer(Double d, Consumer<Double> con) {
        con.accept(d);          // 消费性接口需要有一个参数，没有返回值
    }


}
