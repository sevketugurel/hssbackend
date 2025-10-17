package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByAnimalAnimalId(Long animalId);

    List<Appointment> findByVeterinarianId(Long veterinarianId);

    List<Appointment> findByStatus(Appointment.Status status);

    @Query("SELECT a FROM Appointment a WHERE a.dateTime BETWEEN :startDateTime AND :endDateTime")
    List<Appointment> findByDateTimeBetween(@Param("startDateTime") LocalDateTime startDateTime, 
                                          @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT a FROM Appointment a WHERE a.animal.animalId = :animalId AND a.dateTime BETWEEN :startDateTime AND :endDateTime")
    List<Appointment> findByAnimalIdAndDateTimeBetween(@Param("animalId") Long animalId, 
                                                      @Param("startDateTime") LocalDateTime startDateTime, 
                                                      @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT a FROM Appointment a WHERE a.veterinarianId = :veterinarianId AND a.dateTime BETWEEN :startDateTime AND :endDateTime")
    List<Appointment> findByVeterinarianIdAndDateTimeBetween(@Param("veterinarianId") Long veterinarianId, 
                                                            @Param("startDateTime") LocalDateTime startDateTime, 
                                                            @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT a FROM Appointment a WHERE a.dateTime >= :dateTime AND a.status IN :statuses")
    List<Appointment> findUpcomingAppointments(@Param("dateTime") LocalDateTime dateTime, 
                                             @Param("statuses") List<Appointment.Status> statuses);

    @Query("SELECT a FROM Appointment a WHERE a.animal.owner.ownerId = :ownerId AND a.dateTime BETWEEN :startDateTime AND :endDateTime")
    List<Appointment> findByOwnerIdAndDateTimeBetween(@Param("ownerId") Long ownerId, 
                                                     @Param("startDateTime") LocalDateTime startDateTime, 
                                                     @Param("endDateTime") LocalDateTime endDateTime);
}
