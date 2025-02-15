package com.mberktuncer.monad.config;

import com.mberktuncer.monad.constant.common.FileNames;
import com.mberktuncer.monad.util.EmployeeFileReader;
import com.mberktuncer.monad.service.contract.EmployeeService;
import com.mberktuncer.monad.model.api.CreateEmployeeRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializationConfiguration {

    private static final String EMPLOYEES_FILE = FileNames.EMPLOYEES_FILE.getFilePath();

    @Bean
    CommandLineRunner initDatabase(EmployeeService employeeService) {
        return args -> {
            for (int i = 0; i < 10; i++) {
                try {
                    CreateEmployeeRequest request = EmployeeFileReader.readEmployeeFromFile(
                        EMPLOYEES_FILE, 
                        i
                    );
                    employeeService.save(request);
                } catch (Exception e) {
                    break;
                }
            }
        };
    }
} 