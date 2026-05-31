package com.workforcepro.leave.controller;

import com.workforcepro.leave.dto.LeaveRequestDto;
import com.workforcepro.leave.dto.LeaveResponseDto;
import com.workforcepro.leave.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','HR','ADMIN')")
    public LeaveResponseDto applyLeave(
            @Valid @RequestBody LeaveRequestDto request,
            Authentication authentication) {

        return leaveService.applyLeave(
                request,
                authentication.getName()
        );
    }
}