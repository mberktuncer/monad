package com.mberktuncer.monad.service.impl;

import com.mberktuncer.monad.model.Employee;
import com.mberktuncer.monad.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final List<Employee> employees;
    
    public EmployeeServiceImpl() {
        this.employees = initializeEmployees();
    }
    
    private List<Employee> initializeEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("11111111111", "John", "Doe"));
        employees.add(new Employee("22222222222", "Jane", "Smith"));
        employees.add(new Employee("33333333333", "Alice", "Johnson"));
        employees.add(new Employee("44444444444", "Bob", "Brown"));
        employees.add(new Employee("55555555555", "Charlie", "Davis"));
        employees.add(new Employee("66666666666", "David", "Miller"));
        employees.add(new Employee("77777777777", "Eva", "Wilson"));
        employees.add(new Employee("88888888888", "Frank", "Moore"));
        employees.add(new Employee("99999999999", "Grace", "Taylor"));
        employees.add(new Employee("00000000000", "Henry", "Anderson"));
        return employees;
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

}
