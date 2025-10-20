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
public class SpeciesResponse {

    private Long speciesId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
