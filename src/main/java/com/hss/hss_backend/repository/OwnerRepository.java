package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByEmail(String email);

    Optional<Owner> findByPhone(String phone);

    List<Owner> findByFirstNameContainingIgnoreCase(String firstName);

    List<Owner> findByLastNameContainingIgnoreCase(String lastName);

    @Query("SELECT o FROM Owner o WHERE o.firstName ILIKE %:name% OR o.lastName ILIKE %:name%")
    List<Owner> findByNameContaining(@Param("name") String name);

    @Query("SELECT o FROM Owner o WHERE o.email ILIKE %:email% OR o.phone ILIKE %:phone%")
    List<Owner> findByEmailOrPhone(@Param("email") String email, @Param("phone") String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
