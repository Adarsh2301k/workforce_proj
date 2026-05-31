package com.workforcepro.leave.service;

import com.workforcepro.common.enums.LeaveStatus;
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

import java.util.List;

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

        return mapToResponse(savedLeave);
    }

    @Override
    public List<LeaveResponseDto> getMyLeaves(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Employee employee = employeeRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        return leaveRepository.findByEmployeeId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LeaveResponseDto> getAllLeaves() {

        return leaveRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public LeaveResponseDto approveLeave(
            Long leaveId,
            String managerEmail) {

        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave request not found"));

        User user = userRepository.findByEmail(managerEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Employee manager = employeeRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        leave.setStatus(LeaveStatus.APPROVED);
        leave.setApprovedBy(manager);

        LeaveRequest savedLeave = leaveRepository.save(leave);

        return mapToResponse(savedLeave);
    }

    @Override
    public LeaveResponseDto rejectLeave(
            Long leaveId,
            String managerEmail) {

        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave request not found"));

        User user = userRepository.findByEmail(managerEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Employee manager = employeeRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        leave.setStatus(LeaveStatus.REJECTED);
        leave.setApprovedBy(manager);

        LeaveRequest savedLeave = leaveRepository.save(leave);

        return mapToResponse(savedLeave);
    }

    private LeaveResponseDto mapToResponse(LeaveRequest leave) {

        LeaveResponseDto response = new LeaveResponseDto();

        response.setId(leave.getId());

        response.setEmployeeName(
                leave.getEmployee().getFirstName()
                        + " "
                        + leave.getEmployee().getLastName()
        );

        response.setLeaveType(leave.getLeaveType());
        response.setStartDate(leave.getStartDate());
        response.setEndDate(leave.getEndDate());
        response.setReason(leave.getReason());
        response.setStatus(leave.getStatus());

        if (leave.getApprovedBy() != null) {
            response.setApprovedBy(
                    leave.getApprovedBy().getFirstName()
                            + " "
                            + leave.getApprovedBy().getLastName()
            );
        }

        return response;
    }
}