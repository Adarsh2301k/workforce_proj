package com.workforcepro.employee.service;

import com.workforcepro.employee.dto.EmployeeRequest;
import com.workforcepro.employee.dto.EmployeeResponse;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    Page<EmployeeResponse> getAllEmployees(int page, int size, String sortBy);

    EmployeeResponse getEmployeeById(Long id);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);
}