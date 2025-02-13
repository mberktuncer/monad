package com.mberktuncer.monad.repository;

import com.mberktuncer.monad.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
} 