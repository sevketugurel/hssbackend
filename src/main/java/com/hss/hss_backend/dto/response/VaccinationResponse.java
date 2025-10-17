package com.hss.hss_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationResponse {

    private Long vaccinationRecordId;
    private Long animalId;
    private String animalName;
    private Long vaccineId;
    private String vaccineName;
    private LocalDate date;
    private LocalDate nextDueDate;
    private String batchNumber;
    private String veterinarianName;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
