package com.workforcepro.employee.controller;

import com.workforcepro.employee.dto.EmployeeRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import com.workforcepro.employee.dto.EmployeeResponse;
import com.workforcepro.employee.service.EmployeeService;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public EmployeeResponse createEmployee(@Valid @RequestBody EmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    public Page<EmployeeResponse> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "firstName") String sortBy
    ) {
        return employeeService.getAllEmployees(page, size, sortBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public EmployeeResponse updateEmployee(@PathVariable Long id,
    		@Valid @RequestBody EmployeeRequest request) {
        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully";
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Protected employee endpoint accessed successfully";
    }
}