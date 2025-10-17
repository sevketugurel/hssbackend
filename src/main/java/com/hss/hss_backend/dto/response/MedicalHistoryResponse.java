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
public class MedicalHistoryResponse {

    private Long historyId;
    private Long animalId;
    private String animalName;
    private String diagnosis;
    private LocalDate date;
    private String treatment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
