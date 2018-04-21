package com.zc.cris;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/*
 * 一、Stream API 的操作步骤：
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作(终端操作)
 */
public class TestStreamAPI {


    //2. 中间操作
    // 模拟一个员工数据集合
    private List<Employee> employeeList = Arrays.asList(
            new Employee("老大", 23, 3423.34),
            new Employee("老二", 33, 5521.23),
            new Employee("老三", 13, 2345.34),
            new Employee("老四", 14, 7643.23),
            new Employee("老五", 21, 3645.23),
            new Employee("老五", 21, 3645.23),
            new Employee("老五", 21, 3645.23)
    );

    public static Stream<Character> getCharacterStream(String str) {
        List<Character> list = new ArrayList<>();
        for (Character character : str.toCharArray()) {
            list.add(character);
        }
        return list.stream();
    }

	/*
	  筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */

    // 创建Stream
    @Test
    public void test() {
        //1. 通过 Collection 集合提供的stream() 和 parallelStream()
        List<Integer> list = new ArrayList<>();
        Stream<Integer> stream = list.stream();

        // 2. 通过Arrays 的静态方法将数组转换为Stream
        Stream<Employee> stream1 = Arrays.stream(new Employee[10]);

        //3. 通过Stream 类的静态方法
        Stream<String> stream2 = Stream.of("cirs", "james", "哈登");

        //4. 创建无限流
        //- 迭代
        Stream.iterate(0, (x) -> x + 2).limit(10).forEach(System.out::println);

        //-生成
//        Supplier<Double> supplier = Math::random;
//        Stream<Double> stream3 = Stream.generate(supplier);
        // 精简写法
        Stream<Double> stream3 = Stream.generate(Math::random);
        stream3.limit(5).forEach(System.out::println);

    }

    //内部迭代：迭代操作 Stream API 内部完成（forEach）
    @Test
    public void test1() {
        // 链式写法
        //employeeList.stream().filter(employee -> employee.getAge() > 20).forEach(System.out::println);

        Stream<Employee> stream = employeeList.stream().filter(employee -> {
            System.out.println("stream 对象的中间操作");       // 只有执行了终止操作，中间操作才会开始进行，针对集合里的每个数据进行过滤
            return employee.getAge() > 20;
        });
        stream.forEach(System.out::println);        // 终止操作：一次性执行所有操作，即“惰性求值”
    }

    // limit限制筛选数据的长度
    @Test
    public void test2() {
        employeeList.stream().filter(employee -> {
            System.out.println("短路");
            return employee.getAge() > 20;
        })
                .limit(2)
                .forEach(System.out::println);
    }

    // skip 用于跳过一定数量的数据（小于当前数据集合的数量，否则返回空 Stream）
    // distinct 用于过滤相同的数据（根据元素的equals 和 hashCode）
    @Test
    public void test3() {
        //employeeList.stream().filter(employee -> employee.getAge() > 20).skip(2).forEach(System.out::println);
        employeeList.stream().filter(employee -> employee.getAge() > 20).distinct().forEach(System.out::println);
    }

    /*
		映射
		map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
    @Test
    public void test4() {
        // 将每个数据处理为新的元素并返回
//        Arrays.asList("abc", "fwf", "jol", "era").stream().map(String::toUpperCase).forEach(System.out::println);
        // 提取每个用户的名字
//        employeeList.stream().map(Employee::getName).distinct().forEach(System.out::println);

        // 提取每个用户的名字并且进行处理，map方法可以链式调用
        employeeList.stream().map(Employee::getName).map(String::length).forEach(System.out::println);
    }

    // Stream 中还有 Stream可以将它们拼接起来(示例代码：将Stream中的每个数据提取出来作为另外一个Stream对象进行处理)
    @Test
    public void test5() {

        List<String> list = Arrays.asList("abc", "jil", "kos");
        Stream<Stream<Character>> streamStream = list.stream().map(TestStreamAPI::getCharacterStream);  // {{q,z,a},{g,d,y},{a,g,c}}
        // 流对象中还可以有流对象，此时需要双层forEach
        /*
        streamStream.forEach(x -> {
            x.forEach(System.out::println);
        });
        */

        // 还可以使用 Stream 提供的flatMap          {a,b,c,a,f,g,s,f,a}  将所有的流平铺成一个流
        list.stream().flatMap(TestStreamAPI::getCharacterStream).forEach(System.out::println);

    }

    @Test
    public void test8() {
        List<String> list = Arrays.asList("abc", "jil", "kos");
        List list1 = new ArrayList();
        list1.add(23);
        list1.add(12);
//        list1.add(list);
        list1.addAll(list);
        System.out.println(list1);

    }

    /*
		sorted()——自然排序
		sorted(Comparator com)——定制排序
	 */
    @Test
    public void test9() {
        // 自然排序，按照元素自己指定好的排序规则排序
        List<String> list = Arrays.asList("fff", "aaa", "ccc", "bbb");
        list.stream().sorted().forEach(System.out::println);

        // 定制排序，按照自定义的排序规则排序(年龄一样按照姓名排序，否则就按照年龄排序)
        employeeList.stream().sorted((x, y) -> {
            if (x.getAge() == y.getAge()) {
                return x.getName().compareTo(y.getName());
            } else {
                return x.getAge().compareTo(y.getAge());
            }
        }).forEach(System.out::println);
    }

    //3. 终止操作
	/*
		allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否一个都没有匹配的元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
	 */
    @Test
    public void test10() {
        List<Employee> employees = Arrays.asList(
                new Employee("cris", 23, 354454.23, EMP_STATUS.BUSY),
                new Employee("curry", 26, 454534.23, EMP_STATUS.FREE),
                new Employee("克里斯", 22, 56767.23, EMP_STATUS.FREE),
                new Employee("克莉丝汀", 21, 345454.23, EMP_STATUS.VOCATION),
                new Employee("james", 29, 1232344.23, EMP_STATUS.BUSY)
        );

        // 快速判断集合中的每一个数据是否满足某种条件（相比于以前的把每一个数据提出来判断简洁了太多，虽然原理相同，但是书写逻辑及其简约，并且java8也对此种写法优化）
        boolean b = employees.stream().allMatch(employee -> employee.getStatus().equals(EMP_STATUS.BUSY));
        boolean b1 = employees.stream().anyMatch(employee -> employee.getStatus().equals(EMP_STATUS.BUSY));
        boolean b2 = employees.stream().noneMatch(employee -> employee.getStatus().equals(EMP_STATUS.BUSY));

//        Optional<Employee> first = employees.stream().sorted((x, y) -> -x.getSalary().compareTo(y.getSalary())).findFirst();
        Optional<Employee> first = employees.stream().sorted((x, y) -> Double.compare(x.getSalary(), y.getSalary())).findFirst();

        // 从满足条件的数据中随机获取一个数据(这里需要使用并行流-->多个线程执行这个任务；串行流就是一个线程执行到底)
        Optional<Employee> any = employees.parallelStream().filter(employee -> employee.getStatus().equals(EMP_STATUS.FREE)).findAny();

        System.out.println(b);
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(first.get());
        System.out.println(any.get());
    }

    @Test
    public void test11() {
        System.out.println(employeeList.stream().count());
        System.out.println(employeeList.stream().max((x, y) -> Double.compare(x.getSalary(), y.getSalary())));

        // 获取工资最少的员工的名字（将所有员工提取出来比较拿到符合的数据的其他信息）
        //System.out.println(employeeList.stream().min((x, y) -> Double.compare(x.getAge(), y.getAge())).get().getName());

        // 获取所有员工的最小工资（将所有工资提取出来进行比较）
        Optional<Double> min = employeeList.stream().map(Employee::getSalary).min(Double::compare);
        System.out.println(min);
    }


}
