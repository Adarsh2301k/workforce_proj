package com.workforcepro.leave.service;

import com.workforcepro.leave.dto.LeaveRequestDto;
import com.workforcepro.leave.dto.LeaveResponseDto;

public interface LeaveService {

    LeaveResponseDto applyLeave(
            LeaveRequestDto request,
            String email
    );
}