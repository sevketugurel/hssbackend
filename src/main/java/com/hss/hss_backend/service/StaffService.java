package com.hss.hss_backend.service;

import com.hss.hss_backend.dto.request.StaffCreateRequest;
import com.hss.hss_backend.dto.request.StaffUpdateRequest;
import com.hss.hss_backend.dto.response.StaffResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService {
    
    StaffResponse createStaff(StaffCreateRequest request);
    
    StaffResponse getStaffById(Long id);
    
    Page<StaffResponse> getAllStaff(Pageable pageable);
    
    List<StaffResponse> getActiveStaff();
    
    List<StaffResponse> getStaffByDepartment(String department);
    
    StaffResponse updateStaff(Long id, StaffUpdateRequest request);
    
    void deleteStaff(Long id);
}
