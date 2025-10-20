package com.hss.hss_backend.controller;

import com.hss.hss_backend.dto.request.StaffCreateRequest;
import com.hss.hss_backend.dto.request.StaffUpdateRequest;
import com.hss.hss_backend.dto.response.StaffResponse;
import com.hss.hss_backend.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Slf4j
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StaffResponse> createStaff(@Valid @RequestBody StaffCreateRequest request) {
        log.info("Creating staff: {}", request.getFullName());
        StaffResponse response = staffService.createStaff(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIAN') or hasRole('STAFF')")
    public ResponseEntity<StaffResponse> getStaffById(@PathVariable Long id) {
        log.info("Fetching staff with ID: {}", id);
        StaffResponse response = staffService.getStaffById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIAN') or hasRole('STAFF')")
    public ResponseEntity<Page<StaffResponse>> getAllStaff(Pageable pageable) {
        log.info("Fetching all staff with pagination");
        Page<StaffResponse> response = staffService.getAllStaff(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<StaffResponse>> getActiveStaff() {
        log.info("Fetching active staff");
        List<StaffResponse> response = staffService.getActiveStaff();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/department/{department}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<StaffResponse>> getStaffByDepartment(@PathVariable String department) {
        log.info("Fetching staff by department: {}", department);
        List<StaffResponse> response = staffService.getStaffByDepartment(department);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIAN')")
    public ResponseEntity<StaffResponse> updateStaff(@PathVariable Long id, @Valid @RequestBody StaffUpdateRequest request) {
        log.info("Updating staff with ID: {}", id);
        StaffResponse response = staffService.updateStaff(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        log.info("Deleting staff with ID: {}", id);
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
