package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.StaffRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRoleRepository extends JpaRepository<StaffRole, Long> {
    
    List<StaffRole> findByStaffStaffId(Long staffId);
    
    List<StaffRole> findByRoleRoleId(Long roleId);
    
    void deleteByStaffStaffIdAndRoleRoleId(Long staffId, Long roleId);
}
