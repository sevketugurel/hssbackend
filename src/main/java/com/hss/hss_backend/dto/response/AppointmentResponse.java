package com.hss.hss_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private Long appointmentId;
    private Long animalId;
    private String animalName;
    private String ownerName;
    private LocalDateTime dateTime;
    private String subject;
    private Long veterinarianId;
    private String veterinarianName;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
