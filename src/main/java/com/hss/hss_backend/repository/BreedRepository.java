package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    List<Breed> findBySpeciesSpeciesId(Long speciesId);

    Optional<Breed> findByNameAndSpeciesSpeciesId(String name, Long speciesId);

    List<Breed> findByNameContainingIgnoreCase(String name);

    @Query("SELECT b FROM Breed b WHERE b.species.speciesId = :speciesId AND b.name ILIKE %:name%")
    List<Breed> findBySpeciesIdAndNameLike(@Param("speciesId") Long speciesId, @Param("name") String name);

    boolean existsByNameAndSpeciesSpeciesId(String name, Long speciesId);
}
