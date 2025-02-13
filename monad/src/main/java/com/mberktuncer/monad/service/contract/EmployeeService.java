package com.mberktuncer.monad.service.contract;

import com.mberktuncer.monad.model.api.CreateEmployeeRequest;
import com.mberktuncer.monad.model.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> findAll();
    Employee save(CreateEmployeeRequest employee);
}
