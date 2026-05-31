package com.workforcepro.department.controller;

import com.workforcepro.department.dto.DepartmentRequest;
import com.workforcepro.department.dto.DepartmentResponse;
import com.workforcepro.department.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public DepartmentResponse createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        return departmentService.createDepartment(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    public DepartmentResponse getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public DepartmentResponse updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        return departmentService.updateDepartment(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDepartment(@PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return "Department deleted successfully";
    }
}