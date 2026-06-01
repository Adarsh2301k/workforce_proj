package com.workforcepro.attendance.service;

import com.workforcepro.attendance.dto.AttendanceRequestDTO;
import com.workforcepro.attendance.dto.AttendanceResponseDTO;

import java.util.List;

public interface AttendanceService {

    AttendanceResponseDTO markAttendance(
            AttendanceRequestDTO request);

    List<AttendanceResponseDTO> getAllAttendance();

    AttendanceResponseDTO getAttendanceById(Long id);

    List<AttendanceResponseDTO> getAttendanceByEmployee(
            Long employeeId);
}