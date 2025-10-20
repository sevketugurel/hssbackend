package com.hss.hss_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentUpdateRequest {

    @NotNull(message = "Date and time is required")
    private LocalDateTime dateTime;

    @Size(max = 1000, message = "Subject must not exceed 1000 characters")
    private String subject;

    private Long veterinarianId;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;
}
