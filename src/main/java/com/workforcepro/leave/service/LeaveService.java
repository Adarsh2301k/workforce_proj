package com.workforcepro.leave.service;

import com.workforcepro.leave.dto.LeaveRequestDto;
import com.workforcepro.leave.dto.LeaveResponseDto;

import java.util.List;

public interface LeaveService {

    LeaveResponseDto applyLeave(
            LeaveRequestDto request,
            String email
    );

    List<LeaveResponseDto> getMyLeaves(String email);

    List<LeaveResponseDto> getAllLeaves();

    LeaveResponseDto approveLeave(
            Long leaveId,
            String managerEmail
    );

    LeaveResponseDto rejectLeave(
            Long leaveId,
            String managerEmail
    );
}