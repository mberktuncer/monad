package com.mberktuncer.monad.service.contract;

import com.mberktuncer.monad.model.api.CreateEmployeeRequest;
import com.mberktuncer.monad.model.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee save(CreateEmployeeRequest employee);
    void deleteEmployees(List<Employee> employees);
    void softDeleteEmployees(String identityNumber);
    Employee update(String identityNumber, CreateEmployeeRequest employee);
    Employee findByIdentityNumber(String identityNumber);
    List<Employee> getAllActiveEmployees();
    void addRandomEmployeeFromAdditionalFile();
}
