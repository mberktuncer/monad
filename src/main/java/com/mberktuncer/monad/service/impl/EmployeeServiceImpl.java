package com.mberktuncer.monad.service.impl;

import com.mberktuncer.monad.constant.common.FileNames;
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
import com.mberktuncer.monad.util.EmployeeFileReader;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final String ADDITIONAL_EMPLOYEES_FILE = FileNames.ADDITIONAL_EMPLOYEES_FILE.getFilePath();
    private int currentEmployeeIndex = 0;

    private final EmployeeRepository employeeRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(CreateEmployeeRequest request) {
        EmployeeValidator.validateEmployee(request);
        Employee existingEmployee = employeeRepository.findById(request.getIdentityNumber()).orElse(null);
        
        if (existingEmployee != null && existingEmployee.getStatus() == 0) {
            existingEmployee.setFirstName(request.getFirstName());
            existingEmployee.setLastName(request.getLastName());
            existingEmployee.setStatus(1);
            return employeeRepository.save(existingEmployee);
        } else if (existingEmployee != null && existingEmployee.getStatus() == 1) {
            throw new DuplicateEmployeeException(ErrorMessages.DUPLICATE_EMPLOYEE.getText());
        }
        
        Employee newEmployee = mapperUtil.mapSourceToDestinationType(request, Employee.class);
        newEmployee.setStatus(1);
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

    @Override
    public void softDeleteEmployees(String identityNumber) {
        Employee employee = findByIdentityNumber(identityNumber);
        employee.setStatus(0);
        employeeRepository.save(employee);
    }

    @Override
    public Employee findByIdentityNumber(String identityNumber) {
        return employeeRepository.findByIdentityNumber(identityNumber);
    }

    @Override
    public List<Employee> getAllActiveEmployees() {
        return employeeRepository.findByStatus(1);
    }

    @Override
    public void addRandomEmployeeFromAdditionalFile() {
        CreateEmployeeRequest request = EmployeeFileReader.readEmployeeFromFile(
            ADDITIONAL_EMPLOYEES_FILE, 
            currentEmployeeIndex++
        );
        save(request);
    }
}
