package com.workforcepro.leave.service;

import com.workforcepro.employee.entity.Employee;
import com.workforcepro.employee.repository.EmployeeRepository;
import com.workforcepro.exception.ResourceNotFoundException;
import com.workforcepro.leave.dto.LeaveRequestDto;
import com.workforcepro.leave.dto.LeaveResponseDto;
import com.workforcepro.leave.entity.LeaveRequest;
import com.workforcepro.leave.repository.LeaveRequestRepository;
import com.workforcepro.user.entity.User;
import com.workforcepro.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRequestRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public LeaveServiceImpl(
            LeaveRequestRepository leaveRepository,
            EmployeeRepository employeeRepository,
            UserRepository userRepository) {

        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LeaveResponseDto applyLeave(
            LeaveRequestDto request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Employee employee = employeeRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        LeaveRequest leave = new LeaveRequest();

        leave.setEmployee(employee);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());

        LeaveRequest savedLeave = leaveRepository.save(leave);

        LeaveResponseDto response = new LeaveResponseDto();

        response.setId(savedLeave.getId());
        response.setEmployeeName(
                employee.getFirstName() + " " + employee.getLastName()
        );
        response.setLeaveType(savedLeave.getLeaveType());
        response.setStartDate(savedLeave.getStartDate());
        response.setEndDate(savedLeave.getEndDate());
        response.setReason(savedLeave.getReason());
        response.setStatus(savedLeave.getStatus());

        if (savedLeave.getApprovedBy() != null) {
            response.setApprovedBy(
                    savedLeave.getApprovedBy().getFirstName()
                            + " "
                            + savedLeave.getApprovedBy().getLastName()
            );
        }

        return response;
    }
}