package com.mberktuncer.monad.repository;

import com.mberktuncer.monad.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByStatus(Integer status);
    Employee findByIdentityNumber(String identityNumber);
} 