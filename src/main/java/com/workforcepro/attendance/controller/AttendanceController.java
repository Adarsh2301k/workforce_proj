package com.workforcepro.attendance.controller;

import com.workforcepro.attendance.dto.AttendanceRequestDTO;
import com.workforcepro.attendance.dto.AttendanceResponseDTO;
import com.workforcepro.attendance.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(
            AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceResponseDTO markAttendance(
            @RequestBody AttendanceRequestDTO request) {

        return attendanceService.markAttendance(request);
    }

    @GetMapping
    public List<AttendanceResponseDTO> getAllAttendance() {

        return attendanceService.getAllAttendance();
    }

    @GetMapping("/{id}")
    public AttendanceResponseDTO getAttendanceById(
            @PathVariable Long id) {

        return attendanceService.getAttendanceById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<AttendanceResponseDTO> getAttendanceByEmployee(
            @PathVariable Long employeeId) {

        return attendanceService
                .getAttendanceByEmployee(employeeId);
    }
}