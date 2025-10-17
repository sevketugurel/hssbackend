package com.hss.hss_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabTestResponse {

    private Long testId;
    private Long animalId;
    private String animalName;
    private String testName;
    private LocalDate date;
    private String status;
    private List<LabResultResponse> results;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
