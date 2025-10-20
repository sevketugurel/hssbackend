package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {

    // Basic query methods
    Optional<Species> findByName(String name);
    
    List<Species> findByNameContainingIgnoreCase(String name);
    
    // Custom queries with @Query
    @Query("SELECT s FROM Species s WHERE s.name LIKE %:name%")
    List<Species> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT s FROM Species s ORDER BY s.name ASC")
    List<Species> findAllOrderByName();
    
    // Statistics queries
    @Query("SELECT s.name, COUNT(a) FROM Species s LEFT JOIN s.animals a GROUP BY s.speciesId, s.name ORDER BY COUNT(a) DESC")
    List<Object[]> getSpeciesWithAnimalCount();
    
    @Query("SELECT COUNT(s) FROM Species s")
    Long getTotalSpeciesCount();
    
    // Pagination support
    Page<Species> findByNameContainingIgnoreCase(String name, Pageable pageable);
}