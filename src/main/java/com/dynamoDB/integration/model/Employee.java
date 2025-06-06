package com.dynamoDB.integration.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private String name;
    private int salary;
    private int age;
    private String performance;
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }



    public int getSalary() {
        return salary;
    }

    public String getPerformance() {
        return performance;
    }


    public String getName() {
        return name;
    }



    public int getAge() {
        return age;
    }



    public void setName(String name) {
        this.name = name;
    }



    public void setAge(int age) {
        this.age = age;
    }




}
