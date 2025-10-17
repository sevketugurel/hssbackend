package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    List<Role> findByIsSystemRole(Boolean isSystemRole);

    @Query("SELECT r FROM Role r WHERE r.name ILIKE %:name%")
    List<Role> findByNameContaining(@Param("name") String name);

    @Query("SELECT r FROM Role r WHERE :permission MEMBER OF r.permissions")
    List<Role> findByPermission(@Param("permission") String permission);

    @Query("SELECT r FROM Role r WHERE r.permissions IS NOT EMPTY")
    List<Role> findRolesWithPermissions();

    boolean existsByName(String name);
}
