package com.workforcepro.department.service;

import com.workforcepro.department.dto.DepartmentRequest;
import com.workforcepro.department.dto.DepartmentResponse;
import com.workforcepro.department.entity.Department;
import com.workforcepro.department.repository.DepartmentRepository;
import com.workforcepro.exception.DuplicateResourceException;
import com.workforcepro.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        if (departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Department already exists");
        }

        Department department = new Department();
        department.setName(request.getName());
        department.setDescription(request.getDescription());

        Department savedDepartment = departmentRepository.save(department);

        return mapToResponse(savedDepartment);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        return mapToResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        department.setName(request.getName());
        department.setDescription(request.getDescription());

        Department updatedDepartment = departmentRepository.save(department);

        return mapToResponse(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        departmentRepository.delete(department);
    }

    private DepartmentResponse mapToResponse(Department department) {

        DepartmentResponse response = new DepartmentResponse();

        response.setId(department.getId());
        response.setName(department.getName());
        response.setDescription(department.getDescription());

        return response;
    }
}