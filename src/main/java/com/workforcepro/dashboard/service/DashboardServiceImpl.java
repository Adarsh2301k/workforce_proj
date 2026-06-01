package com.workforcepro.dashboard.service;

import com.workforcepro.attendance.repository.AttendanceRepository;
import com.workforcepro.common.enums.LeaveStatus;
import com.workforcepro.dashboard.dto.DashboardResponseDTO;
import com.workforcepro.department.repository.DepartmentRepository;
import com.workforcepro.employee.repository.EmployeeRepository;
import com.workforcepro.leave.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardServiceImpl
        implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LeaveRequestRepository leaveRepository;
    private final AttendanceRepository attendanceRepository;

    public DashboardServiceImpl(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            LeaveRequestRepository leaveRepository,
            AttendanceRepository attendanceRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.leaveRepository = leaveRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public DashboardResponseDTO getDashboardStats() {

        DashboardResponseDTO response =
                new DashboardResponseDTO();

        response.setTotalEmployees(
                employeeRepository.count());

        response.setTotalDepartments(
                departmentRepository.count());

        response.setTotalLeaves(
                leaveRepository.count());

        response.setPendingLeaves(
                leaveRepository.countByStatus(
                        LeaveStatus.PENDING));

        response.setApprovedLeaves(
                leaveRepository.countByStatus(
                        LeaveStatus.APPROVED));

        response.setTodayAttendance(
                attendanceRepository.countByAttendanceDate(
                        LocalDate.now()));

        return response;
    }
}