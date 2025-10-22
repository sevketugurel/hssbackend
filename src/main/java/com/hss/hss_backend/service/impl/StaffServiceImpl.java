package com.hss.hss_backend.service.impl;

import com.hss.hss_backend.dto.request.StaffCreateRequest;
import com.hss.hss_backend.dto.request.StaffUpdateRequest;
import com.hss.hss_backend.dto.response.StaffResponse;
import com.hss.hss_backend.entity.Staff;
import com.hss.hss_backend.repository.StaffRepository;
import com.hss.hss_backend.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public StaffResponse createStaff(StaffCreateRequest request) {
        log.info("Creating staff with email: {}", request.getEmail());
        
        // Check if staff with same email already exists
        if (staffRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Staff with email '" + request.getEmail() + "' already exists");
        }
        
        Staff staff = Staff.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .hireDate(request.getHireDate())
                .terminationDate(request.getTerminationDate())
                .active(true) // Default to active when creating
                .position(request.getPosition())
                .department(request.getDepartment())
                .salary(request.getSalary())
                .address(request.getAddress())
                .emergencyContactName(request.getEmergencyContactName())
                .emergencyContactPhone(request.getEmergencyContactPhone())
                .build();
        
        Staff savedStaff = staffRepository.save(staff);
        log.info("Staff created successfully with ID: {}", savedStaff.getStaffId());
        
        return mapToResponse(savedStaff);
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponse getStaffById(Long id) {
        log.info("Fetching staff with ID: {}", id);
        
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + id));
        
        return mapToResponse(staff);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StaffResponse> getAllStaff(Pageable pageable) {
        log.info("Fetching all staff with pagination");
        
        Page<Staff> staffPage = staffRepository.findAll(pageable);
        
        return staffPage.map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponse> getActiveStaff() {
        log.info("Fetching active staff");
        
        List<Staff> activeStaff = staffRepository.findCurrentlyActiveStaff();
        
        return activeStaff.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponse> getStaffByDepartment(String department) {
        log.info("Fetching staff by department: {}", department);
        
        List<Staff> staffByDepartment = staffRepository.findActiveStaffByDepartment(department);
        
        return staffByDepartment.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StaffResponse updateStaff(Long id, StaffUpdateRequest request) {
        log.info("Updating staff with ID: {}", id);
        
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + id));
        
        // Check if another staff with same email exists (excluding current one)
        staffRepository.findByEmail(request.getEmail())
                .ifPresent(existingStaff -> {
                    if (!existingStaff.getStaffId().equals(id)) {
                        throw new IllegalArgumentException("Staff with email '" + request.getEmail() + "' already exists");
                    }
                });
        
        // Update fields
        staff.setFullName(request.getFullName());
        staff.setEmail(request.getEmail());
        staff.setPhone(request.getPhone());
        staff.setHireDate(request.getHireDate());
        staff.setTerminationDate(request.getTerminationDate());
        staff.setPosition(request.getPosition());
        staff.setDepartment(request.getDepartment());
        staff.setSalary(request.getSalary());
        staff.setAddress(request.getAddress());
        staff.setEmergencyContactName(request.getEmergencyContactName());
        staff.setEmergencyContactPhone(request.getEmergencyContactPhone());
        
        // If termination date is set, mark as inactive
        if (request.getTerminationDate() != null) {
            staff.setActive(false);
        }
        
        Staff updatedStaff = staffRepository.save(staff);
        
        log.info("Staff updated successfully with ID: {}", updatedStaff.getStaffId());
        
        return mapToResponse(updatedStaff);
    }

    @Override
    public void deleteStaff(Long id) {
        log.info("Deleting staff with ID: {}", id);
        
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + id));
        
        // Check if staff has associated user accounts or roles
        if (staff.getUserAccounts() != null && !staff.getUserAccounts().isEmpty()) {
            throw new IllegalStateException("Cannot delete staff with associated user accounts. Please remove user accounts first.");
        }
        
        if (staff.getStaffRoles() != null && !staff.getStaffRoles().isEmpty()) {
            throw new IllegalStateException("Cannot delete staff with associated roles. Please remove roles first.");
        }
        
        staffRepository.delete(staff);
        log.info("Staff deleted successfully with ID: {}", id);
    }

    private StaffResponse mapToResponse(Staff staff) {
        return StaffResponse.builder()
                .staffId(staff.getStaffId())
                .fullName(staff.getFullName())
                .email(staff.getEmail())
                .phone(staff.getPhone())
                .hireDate(staff.getHireDate())
                .terminationDate(staff.getTerminationDate())
                .active(staff.getActive())
                .position(staff.getPosition())
                .department(staff.getDepartment())
                .salary(staff.getSalary())
                .address(staff.getAddress())
                .emergencyContactName(staff.getEmergencyContactName())
                .emergencyContactPhone(staff.getEmergencyContactPhone())
                .createdAt(staff.getCreatedAt())
                .updatedAt(staff.getUpdatedAt())
                .build();
    }
}


