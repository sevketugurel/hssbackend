package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findByFirebaseUid(String firebaseUid);

    List<UserAccount> findByIsActive(Boolean isActive);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.staff.staffId = :staffId")
    Optional<UserAccount> findByStaffId(@Param("staffId") Long staffId);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.lastLogin >= :startDate AND ua.lastLogin <= :endDate")
    List<UserAccount> findByLastLoginBetween(@Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.loginAttempts >= :maxAttempts")
    List<UserAccount> findLockedAccounts(@Param("maxAttempts") Integer maxAttempts);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.lockedUntil IS NOT NULL AND ua.lockedUntil > :now")
    List<UserAccount> findCurrentlyLockedAccounts(@Param("now") LocalDateTime now);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.isActive = true AND ua.lastLogin < :date")
    List<UserAccount> findInactiveUsers(@Param("date") LocalDateTime date);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByFirebaseUid(String firebaseUid);
}
