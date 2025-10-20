package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Breed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    // Basic query methods
    List<Breed> findBySpeciesSpeciesId(Long speciesId);
    
    List<Breed> findByNameContainingIgnoreCase(String name);
    
    Optional<Breed> findByNameAndSpeciesSpeciesId(String name, Long speciesId);
    
    // Custom queries with @Query
    @Query("SELECT b FROM Breed b WHERE b.name LIKE %:name%")
    List<Breed> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT b FROM Breed b WHERE b.species.name = :speciesName")
    List<Breed> findBySpeciesName(@Param("speciesName") String speciesName);
    
    @Query("SELECT b FROM Breed b WHERE b.species.speciesId = :speciesId ORDER BY b.name ASC")
    List<Breed> findBySpeciesIdOrderByName(@Param("speciesId") Long speciesId);
    
    // Statistics queries
    @Query("SELECT b.name, COUNT(a) FROM Breed b LEFT JOIN b.animals a WHERE b.species.speciesId = :speciesId GROUP BY b.breedId, b.name ORDER BY COUNT(a) DESC")
    List<Object[]> getBreedWithAnimalCountForSpecies(@Param("speciesId") Long speciesId);
    
    @Query("SELECT b.species.name, b.name, COUNT(a) FROM Breed b LEFT JOIN b.animals a GROUP BY b.species.speciesId, b.species.name, b.breedId, b.name ORDER BY COUNT(a) DESC")
    List<Object[]> getAllBreedsWithAnimalCount();
    
    // Pagination support
    Page<Breed> findBySpeciesSpeciesId(Long speciesId, Pageable pageable);
    
    Page<Breed> findByNameContainingIgnoreCase(String name, Pageable pageable);
}