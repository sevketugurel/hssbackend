package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByEmail(String email);

    List<Staff> findByActive(Boolean active);

    List<Staff> findByDepartment(String department);

    List<Staff> findByPosition(String position);

    List<Staff> findByFullNameContainingIgnoreCase(String fullName);

    @Query("SELECT s FROM Staff s WHERE s.hireDate >= :startDate AND s.hireDate <= :endDate")
    List<Staff> findByHireDateBetween(@Param("startDate") java.time.LocalDate startDate, 
                                     @Param("endDate") java.time.LocalDate endDate);

    @Query("SELECT s FROM Staff s WHERE s.terminationDate IS NULL AND s.active = true")
    List<Staff> findActiveStaff();

    @Query("SELECT s FROM Staff s WHERE s.terminationDate IS NOT NULL")
    List<Staff> findTerminatedStaff();

    @Query("SELECT s FROM Staff s WHERE s.department = :department AND s.active = true")
    List<Staff> findActiveStaffByDepartment(@Param("department") String department);

    boolean existsByEmail(String email);
}
