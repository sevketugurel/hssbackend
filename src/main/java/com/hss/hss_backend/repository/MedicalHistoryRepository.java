package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

    List<MedicalHistory> findByAnimalAnimalId(Long animalId);

    List<MedicalHistory> findByAnimalAnimalIdOrderByDateDesc(Long animalId);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.animal.animalId = :animalId AND mh.date BETWEEN :startDate AND :endDate")
    List<MedicalHistory> findByAnimalIdAndDateBetween(@Param("animalId") Long animalId, 
                                                     @Param("startDate") LocalDate startDate, 
                                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.diagnosis ILIKE %:diagnosis%")
    List<MedicalHistory> findByDiagnosisContaining(@Param("diagnosis") String diagnosis);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.animal.animalId = :animalId AND mh.diagnosis ILIKE %:diagnosis%")
    List<MedicalHistory> findByAnimalIdAndDiagnosisContaining(@Param("animalId") Long animalId, 
                                                             @Param("diagnosis") String diagnosis);
}
