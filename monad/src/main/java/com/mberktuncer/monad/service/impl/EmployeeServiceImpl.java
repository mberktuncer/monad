package com.mberktuncer.monad.service.impl;

import com.mberktuncer.monad.constant.exception.ErrorMessages;
import com.mberktuncer.monad.exception.DuplicateEmployeeException;
import com.mberktuncer.monad.model.api.CreateEmployeeRequest;
import com.mberktuncer.monad.model.entity.Employee;
import com.mberktuncer.monad.repository.EmployeeRepository;
import com.mberktuncer.monad.service.contract.EmployeeService;
import com.mberktuncer.monad.core.validator.EmployeeValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(CreateEmployeeRequest employee) {
        EmployeeValidator.validateEmployee(employee);
        if (employeeRepository.findById(employee.getIdentityNumber()).isPresent()) {
            throw new DuplicateEmployeeException(ErrorMessages.DUPLICATE_EMPLOYEE.getText());
        }
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());
        newEmployee.setIdentityNumber(employee.getIdentityNumber());
        return employeeRepository.save(newEmployee);
    }
}
