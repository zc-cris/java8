package com.zc.cris;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestLambda3 {

    private List<Employee> list = Arrays.asList(
            new Employee("cris", 23, 44563.34),
            new Employee("绫波丽", 22, 64245.34),
            new Employee("明日香", 22, 34352.34),
            new Employee("三上悠亚", 25, 43452.34)
    );

    /**
     * 调用 Collection.sort()方法，通过定制排序比较两个 Employee （先按照年龄的倒叙比，如果年龄相同，按照名字比，使用 lambda表达式）
     */
    @Test
    public void test01() {
        Collections.sort(list, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return -Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    /**
     * 声明函数式接口，接口中声明一个抽象方法，public String getValue(String str)
     * 1. 编写方法，将函数式接口作为参数，将一个字符串转换为大写，并作为方法的返回值
     * 2. 将字符串的第二个到第四个位置截取（留头不留尾）
     */
    @Test
    public void test2(){
        String str = handleString("i love lambda expression", x -> x.toUpperCase());
        System.out.println(str);    // I LOVE LAMBDA EXPRESSION
        System.out.println("-----------");
        System.out.println(handleString("123456", str1 -> str1.substring(1, 4)));

    }
    public String handleString(String str, MyStringFunction myStringFunction){
        return myStringFunction.getValue(str);
    }
}
