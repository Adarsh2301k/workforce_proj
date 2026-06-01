package com.workforcepro.attendance.service;

import com.workforcepro.attendance.dto.AttendanceRequestDTO;
import com.workforcepro.attendance.dto.AttendanceResponseDTO;
import com.workforcepro.attendance.entity.Attendance;
import com.workforcepro.attendance.repository.AttendanceRepository;
import com.workforcepro.employee.entity.Employee;
import com.workforcepro.employee.repository.EmployeeRepository;
import com.workforcepro.exception.DuplicateResourceException;
import com.workforcepro.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            EmployeeRepository employeeRepository) {

        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public AttendanceResponseDTO markAttendance(
            AttendanceRequestDTO request) {

        Employee employee = employeeRepository
                .findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found"));

        attendanceRepository
                .findByEmployeeAndAttendanceDate(
                        employee,
                        request.getAttendanceDate())
                .ifPresent(attendance -> {
                    throw new DuplicateResourceException(
                            "Attendance already marked for this date");
                });

        Attendance attendance = new Attendance();

        attendance.setEmployee(employee);
        attendance.setAttendanceDate(
                request.getAttendanceDate());
        attendance.setCheckInTime(
                request.getCheckInTime());
        attendance.setCheckOutTime(
                request.getCheckOutTime());
        attendance.setStatus(
                request.getStatus());
        attendance.setRemarks(
                request.getRemarks());

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return mapToResponse(savedAttendance);
    }

    @Override
    public List<AttendanceResponseDTO> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AttendanceResponseDTO getAttendanceById(
            Long id) {

        Attendance attendance =
                attendanceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance not found"));

        return mapToResponse(attendance);
    }

    @Override
    public List<AttendanceResponseDTO>
    getAttendanceByEmployee(Long employeeId) {

        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found"));

        return attendanceRepository
                .findByEmployee(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AttendanceResponseDTO mapToResponse(
            Attendance attendance) {

        AttendanceResponseDTO response =
                new AttendanceResponseDTO();

        response.setId(attendance.getId());

        response.setEmployeeId(
                attendance.getEmployee().getId());

        response.setEmployeeName(
                attendance.getEmployee().getFirstName()
                        + " "
                        + attendance.getEmployee().getLastName());

        response.setAttendanceDate(
                attendance.getAttendanceDate());

        response.setCheckInTime(
                attendance.getCheckInTime());

        response.setCheckOutTime(
                attendance.getCheckOutTime());

        response.setStatus(
                attendance.getStatus());

        response.setRemarks(
                attendance.getRemarks());

        return response;
    }
}