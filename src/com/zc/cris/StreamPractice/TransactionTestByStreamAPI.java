package com.zc.cris.StreamPractice;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionTestByStreamAPI {

    List<Transaction> transactions = null;

    // 将一个字符串的每个字符提取出来作为一个流对象返回
    public static Stream<String> getCharacter(String str) {
        List<String> list = new ArrayList<>();
        for (Character s : str.toCharArray()) {
            list.add(s.toString());
        }
        return list.stream();
    }

    @Before
    public void before() {
        Trader cris = new Trader("Cris", "Cambridge");
        Trader james = new Trader("James", "Milan");
        Trader curry = new Trader("Curry", "Cambridge");
        Trader harden = new Trader("Harden", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(cris, 2011, 300),
                new Transaction(james, 2012, 1000),
                new Transaction(james, 2011, 400),
                new Transaction(curry, 2012, 710),
                new Transaction(curry, 2012, 700),
                new Transaction(harden, 2012, 950)
        );
    }

    //1. 找出2011年发生的所有交易， 并按交易额排序（从低到高/从高到低）
    @Test
    public void test() {
        // 过滤和定制排序
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted((x, y) -> -Integer.compare(x.getValue(), y.getValue()))
                .forEach(System.out::println);
    }

    // 2. 从交易集合中发现交易员都在哪些城市生活过
    @Test
    public void test1() {
        // 第一种写法
        List<String> list = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .collect(Collectors.toList());
        System.out.println(list);
        list.forEach(System.out::println);

        // 第二种写法
        transactions.stream().map(transaction -> transaction.getTrader().getCity()).distinct().forEach(System.out::println);

    }

    // 3. 从交易集合中查找所有来自剑桥的交易员以及将他们按照名字排序
    @Test
    public void test3() {
        // 第一种 写法(取出所有交易员然后过滤，去重，排序)
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted((x, y) -> -x.getName().compareTo(y.getName()))
                .forEach(System.out::println);

        // 第二种写法(过滤交易数据，然后取出交易员进行去重以及排序)
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .distinct()
                .sorted((x, y) -> x.getName().compareTo(y.getName()))
                .forEach(System.out::println);
    }

    // 4. 返回所有交易员的姓名字符串，并且按照字母排序
    @Test
    public void test4() {
        // 第一种写法
        List<String> collect = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .sorted()
                .collect(Collectors.toList());
//        for (String s : collect) {
//            System.out.println(s);
//        }
        collect.forEach(System.out::println);

        // 第二种写法
        String collect1 = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .sorted()
//                .collect(Collectors.joining("-"));    // Cris-Curry-Curry-Harden-James-James
                .reduce("", String::concat);    // CrisCurryCurryHardenJamesJames
        System.out.println(collect1);

        // 第三种写法(将流中的每一个对象转换为每一个流并合成一个流进行函数处理)
        transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .flatMap(TransactionTestByStreamAPI::getCharacter)
                .sorted(String::compareToIgnoreCase)
                .forEach(System.out::print);


    }

    /*
     * 5. 有没有交易员是在米兰工作的
     */
    @Test
    public void test5() {
        boolean b = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(b);

        // 从交易记录中拿到在米兰工作过的员工的信息
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Milan"))
                .map(Transaction::getTrader)
                .distinct()
                .forEach(System.out::println);

    }

    // 6. 打印生活在剑桥的交易员的所有交易额
    @Test
    public void test6() {
        // 第一种方法（将满足条件的交易筛选出来进行交易额的累加）
        Optional<Integer> sum = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::sum);
        System.out.println(sum);

        // 第二种写法：利用collect 收集来计算满足条件的交易的某个属性的值
        Integer collect = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.summingInt(Transaction::getValue));
        System.out.println(collect);
    }

    // 7. 所有交易中，最高的交易额是多少
    @Test
    public void test7() {
        // 第一种方式
        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .max(Integer::compareTo);
        System.out.println(max.get());

        // 第二种方式
        Optional<Integer> collect = transactions.stream()
                .map(Transaction::getValue)
                .collect(Collectors.maxBy(Integer::compare));
        System.out.println(collect);

        // 第三种方式(获取交易额最高的交易对象)
        Optional<Transaction> transaction = transactions.stream()
                .collect(Collectors.maxBy((x, y) -> Integer.compare(x.getValue(), y.getValue())));
        System.out.println(transaction);

    }


}
