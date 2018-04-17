package com.zc.cris;

// 筛选工资大于5000 的员工策略
public class MyStrategyBySalary implements  MyStrategy<Employee> {
    @Override
    public boolean filter(Employee employee) {
        return employee.getSalary() > 5000;
    }
}
