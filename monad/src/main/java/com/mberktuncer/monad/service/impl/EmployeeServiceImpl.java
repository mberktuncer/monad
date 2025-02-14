package com.mberktuncer.monad.service.impl;

import com.mberktuncer.monad.constant.exception.ErrorMessages;
import com.mberktuncer.monad.core.mapper.MapperUtil;
import com.mberktuncer.monad.exception.DuplicateEmployeeException;
import com.mberktuncer.monad.model.api.CreateEmployeeRequest;
import com.mberktuncer.monad.model.entity.Employee;
import com.mberktuncer.monad.repository.EmployeeRepository;
import com.mberktuncer.monad.service.contract.EmployeeService;
import com.mberktuncer.monad.core.validator.EmployeeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(CreateEmployeeRequest request) {
        EmployeeValidator.validateEmployee(request);
        if (employeeRepository.findById(request.getIdentityNumber()).isPresent()) {
            throw new DuplicateEmployeeException(ErrorMessages.DUPLICATE_EMPLOYEE.getText());
        }
        Employee newEmployee = mapperUtil.mapSourceToDestinationType(request, Employee.class);

        return employeeRepository.save(newEmployee);
    }

    @Override
    public void deleteEmployees(List<Employee> employees) {
        employeeRepository.deleteAll(employees);
    }

    @Override
    public Employee update(String identityNumber, CreateEmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findById(identityNumber)
            .orElseThrow(() -> new RuntimeException(ErrorMessages.EMPLOYEE_NOT_FOUND.getText()));
        
        EmployeeValidator.validateEmployee(request);
        
        if (!identityNumber.equals(request.getIdentityNumber()) &&
            employeeRepository.findById(request.getIdentityNumber()).isPresent()) {
            throw new DuplicateEmployeeException(ErrorMessages.DUPLICATE_EMPLOYEE.getText());
        }
        
        existingEmployee.setIdentityNumber(request.getIdentityNumber());
        existingEmployee.setFirstName(request.getFirstName());
        existingEmployee.setLastName(request.getLastName());
        
        return employeeRepository.save(existingEmployee);
    }
}
