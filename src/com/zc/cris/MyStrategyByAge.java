package com.zc.cris;

import java.util.List;

// 筛选年龄大于20的员工
public class MyStrategyByAge implements  MyStrategy<Employee> {

    @Override
    public boolean filter(Employee employee) {
        return employee.getAge() > 20;
    }
}
