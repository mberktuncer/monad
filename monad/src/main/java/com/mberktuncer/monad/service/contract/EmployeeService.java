package com.mberktuncer.monad.service.contract;

import com.mberktuncer.monad.model.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> findAll();
    Employee save(Employee employee);
}
