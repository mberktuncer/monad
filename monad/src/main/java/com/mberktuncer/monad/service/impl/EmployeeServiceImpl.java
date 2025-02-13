package com.mberktuncer.monad.service.impl;

import com.mberktuncer.monad.model.Employee;
import com.mberktuncer.monad.repository.EmployeeRepository;
import com.mberktuncer.monad.service.contract.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void initializeEmployees() {
        if (employeeRepository.count() == 0) {
            try {
                ClassPathResource resource = new ClassPathResource("employees.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    Employee employee = new Employee(data[0].trim(), data[1].trim(), data[2].trim());
                    employeeRepository.save(employee);
                }
                
                reader.close();
            } catch (Exception e) {
                throw new RuntimeException("Çalışanlar yüklenirken hata oluştu", e);
            }
        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
