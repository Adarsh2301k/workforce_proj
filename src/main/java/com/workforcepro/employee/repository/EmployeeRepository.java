package com.workforcepro.employee.repository;

import com.workforcepro.employee.entity.Employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmployeeCode(String employeeCode);
    Optional<Employee> findByUserId(Long userId);
}