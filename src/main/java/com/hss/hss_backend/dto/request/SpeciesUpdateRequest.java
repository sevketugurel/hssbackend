package com.hss.hss_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesUpdateRequest {

    @NotBlank(message = "Species name is required")
    @Size(max = 100, message = "Species name must not exceed 100 characters")
    private String name;
}
