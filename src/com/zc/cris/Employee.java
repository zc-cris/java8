package com.zc.cris;

import java.util.Objects;

enum EMP_STATUS {
    FREE, BUSY, VOCATION
}

public class Employee {
    private String name;
    private Integer age;
    private Double salary;
    private EMP_STATUS status;      //员工状态枚举

    public Employee(Integer age) {
        this.age = age;
    }

    public Employee(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Employee(String name, Integer age, Double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Employee(String name, Integer age, Double salary, EMP_STATUS status) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.status = status;
    }

    public EMP_STATUS getStatus() {
        return status;
    }

    public Employee() {
    }

    public void setStatus(EMP_STATUS status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
                Objects.equals(age, employee.age) &&
                Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, age, salary);
    }
}
