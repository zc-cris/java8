package com.zc.cris.StreamPractice;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PracticeStream {
    /*
	 2.	怎样用 map 和 reduce 方法数一数流中有多少个Employee呢？
	 */
    List<Employee> employees = Arrays.asList(
            new Employee("cris", 23, 354454.23, EMP_STATUS.BUSY),
            new Employee("curry", 26, 454534.23, EMP_STATUS.FREE),
            new Employee("克里斯", 22, 56767.23, EMP_STATUS.FREE),
            new Employee("克莉丝汀", 21, 345454.23, EMP_STATUS.VOCATION),
            new Employee("james", 29, 1232344.23, EMP_STATUS.BUSY)
    );

    /*
      1.	给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？
    ，给定【1，2，3，4，5】， 应该返回【1，4，9，16，25】。
    */
    @Test
    public void test() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5};
        Arrays.stream(arr).map(x -> x * x).forEach(System.out::println);
    }

    @Test
    public void test2() {
        // 这里非常巧妙，使用map 将流中的每一个数据转换为数字1，然后对每一个数字进行求和操作
        Optional<Integer> reduce = employees.stream().map(employee -> 1).reduce(Integer::sum);
        System.out.println(reduce.get());

        // 实际上最简单的是下面这种写法
        long count = employees.stream().count();
        System.out.println(count);
    }

}
