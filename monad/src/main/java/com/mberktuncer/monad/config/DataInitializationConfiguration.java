package com.mberktuncer.monad.config;

import com.mberktuncer.monad.model.entity.Employee;
import com.mberktuncer.monad.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class DataInitializationConfiguration {

    private final EmployeeRepository employeeRepository;

    public DataInitializationConfiguration(EmployeeRepository employeeRepository) {
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
                throw new RuntimeException("Something went wrong when employees importing", e);
            }
        }
    }
} 