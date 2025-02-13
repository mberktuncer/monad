package com.mberktuncer.monad.service.impl;

import com.mberktuncer.monad.model.Employee;
import com.mberktuncer.monad.repository.EmployeeRepository;
import com.mberktuncer.monad.service.contract.EmployeeService;
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
}
