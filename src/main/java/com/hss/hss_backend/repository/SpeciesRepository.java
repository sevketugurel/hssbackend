package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {

    Optional<Species> findByName(String name);

    List<Species> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM Species s WHERE s.name ILIKE %:name%")
    List<Species> findByNameLike(@Param("name") String name);

    boolean existsByName(String name);
}
