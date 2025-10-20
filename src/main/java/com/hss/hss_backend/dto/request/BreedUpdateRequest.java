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
public class BreedUpdateRequest {

    @NotNull(message = "Species ID is required")
    private Long speciesId;

    @NotBlank(message = "Breed name is required")
    @Size(max = 100, message = "Breed name must not exceed 100 characters")
    private String name;
}
