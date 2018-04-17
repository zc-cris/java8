package com.zc.cris;

import org.junit.Test;

import java.util.*;

public class testLambda {

    // 匿名函数
    @Test
    public void test01(){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> set = new TreeSet<>(comparator);
    }

    // lambda表达式
    @Test
    public void test02(){
        Comparator<Integer> comparator = (o1, o2) -> Integer.compare(o1, o2);
        TreeSet<Integer> set = new TreeSet<>(comparator);
    }

    // 模拟一个员工数据集合
    private List<Employee> employeeList = Arrays.asList(
            new Employee("老大", 23,3423.34),
            new Employee("老二", 33,5521.23),
            new Employee("老三", 13,2345.34),
            new Employee("老四", 14,7643.23),
            new Employee("老五", 21,3645.23)
    );

    // 需求一：选择出大于20岁的员工
    public List<Employee> filterByAge(List<Employee> list){
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : list) {
            if(employee.getAge() > 20){
                emps.add(employee);
            }
        }
        return emps;
    }
    // 需求二：选择出薪水大于5000的员工
    public List<Employee> filterBySalary(List<Employee> list){
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : list) {
            if(employee.getSalary() > 5000){
                emps.add(employee);
            }
        }
        return emps;
    }

    // 测试原始的方法
    @Test
    public void testEmployeeFilter(){
        List<Employee> employees = filterByAge(employeeList);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        System.out.println("-----------");
        List<Employee> employees1 = filterBySalary(employeeList);
        for (Employee employee : employees1) {
            System.out.println(employee);
        }
    }

    //优化方式1：（策略）设计模式（客户端只需要调用这一种方法即可）
    public List<Employee> filterEmployeeStrategy(List<Employee> list, MyStrategy<Employee> strategy){
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : list) {
            if(strategy.filter(employee)){
                emps.add(employee);
            }
        }
        return emps;
    }
    // 测试策略模式
    @Test
    public void testEmployeeStrategy(){
        List<Employee> employees = filterEmployeeStrategy(employeeList, new MyStrategyByAge());
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        System.out.println("+++++++++++");
        List<Employee> employees1 = filterEmployeeStrategy(employeeList, new MyStrategyBySalary());
        for (Employee employee : employees1) {
            System.out.println(employee);
        }
    }

    // 优化方式二：匿名内部类（不是经常使用策略类并且为了减少策略类）
    @Test
    public  void testInnerClass(){
        List<Employee> list = filterEmployeeStrategy(employeeList, new MyStrategy<Employee>() {
            @Override
            public boolean filter(Employee employee) {
                return employee.getName().contains("三");
            }
        });
        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    //优化方式三：lambda表达式
    @Test
    public void  testStrategyByLambda(){
        // 写法一（更加简洁）
        //List<Employee> list = filterEmployeeStrategy(employeeList, (employee -> employee.getSalary()>5000));
        // 写法二：
        List<Employee> list = filterEmployeeStrategy(employeeList, (e) -> e.getSalary() > 5000);
        // 全新的数据遍历方式
        list.forEach(System.out::println);
    }

    // 优化方式四：Stream API(除了Employee 这个实体类和对应的List 集合，其他都没有的情况下，我们可以使用最为简洁的方式)
    @Test
    public void testStreamAPI(){
        // 打印集合里年龄大于20的前两位数据
        employeeList.stream()
                    .filter(employee -> employee.getAge() > 20)
                    .limit(2)
                    .forEach(System.out::println);
        System.out.println("-------------");
        // 打印集合里的所有数据的名字
        employeeList.stream()
                    .map(Employee::getName)
                    .forEach(System.out::println);
    }



}


