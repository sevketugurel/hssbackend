package main.java.com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByOwnerOwnerId(Long ownerId);

    List<Animal> findBySpeciesSpeciesId(Long speciesId);

    List<Animal> findByBreedBreedId(Long breedId);

    Optional<Animal> findByMicrochipNo(String microchipNo);

    List<Animal> findByNameContainingIgnoreCase(String name);

    @Query("SELECT a FROM Animal a WHERE a.owner.firstName ILIKE %:ownerName% OR a.owner.lastName ILIKE %:ownerName%")
    List<Animal> findByOwnerNameContaining(@Param("ownerName") String ownerName);

    @Query("SELECT a FROM Animal a WHERE a.birthDate BETWEEN :startDate AND :endDate")
    List<Animal> findByBirthDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Animal a WHERE a.species.speciesId = :speciesId AND a.breed.breedId = :breedId")
    List<Animal> findBySpeciesAndBreed(@Param("speciesId") Long speciesId, @Param("breedId") Long breedId);

    @Query("SELECT a FROM Animal a WHERE a.microchipNo IS NOT NULL AND a.microchipNo ILIKE %:microchip%")
    List<Animal> findByMicrochipContaining(@Param("microchip") String microchip);

    boolean existsByMicrochipNo(String microchipNo);
}
