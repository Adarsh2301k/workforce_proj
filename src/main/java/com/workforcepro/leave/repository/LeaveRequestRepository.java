package com.workforcepro.leave.repository;

import com.workforcepro.common.enums.LeaveStatus;
import com.workforcepro.leave.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository
        extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);
    long countByStatus(LeaveStatus status);
}