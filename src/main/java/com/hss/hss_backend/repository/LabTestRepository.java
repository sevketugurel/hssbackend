package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    List<LabTest> findByAnimalAnimalId(Long animalId);

    List<LabTest> findByStatus(LabTest.Status status);

    List<LabTest> findByTestNameContainingIgnoreCase(String testName);

    @Query("SELECT lt FROM LabTest lt WHERE lt.animal.animalId = :animalId AND lt.date BETWEEN :startDate AND :endDate")
    List<LabTest> findByAnimalIdAndDateBetween(@Param("animalId") Long animalId, 
                                              @Param("startDate") LocalDate startDate, 
                                              @Param("endDate") LocalDate endDate);

    @Query("SELECT lt FROM LabTest lt WHERE lt.status = :status AND lt.date < :date")
    List<LabTest> findOverdueTests(@Param("status") LabTest.Status status, @Param("date") LocalDate date);

    @Query("SELECT lt FROM LabTest lt WHERE lt.animal.animalId = :animalId AND lt.status = :status")
    List<LabTest> findByAnimalIdAndStatus(@Param("animalId") Long animalId, @Param("status") LabTest.Status status);
}
