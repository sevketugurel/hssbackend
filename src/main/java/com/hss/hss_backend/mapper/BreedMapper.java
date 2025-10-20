package com.hss.hss_backend.mapper;

import com.hss.hss_backend.dto.request.BreedCreateRequest;
import com.hss.hss_backend.dto.request.BreedUpdateRequest;
import com.hss.hss_backend.dto.response.BreedResponse;
import com.hss.hss_backend.entity.Breed;
import com.hss.hss_backend.entity.Species;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BreedMapper {

    public static Breed toEntity(BreedCreateRequest request, Species species) {
        return Breed.builder()
                .species(species)
                .name(request.getName())
                .build();
    }

    public static void updateEntity(Breed breed, BreedUpdateRequest request) {
        if (request.getName() != null) {
            breed.setName(request.getName());
        }
        if (request.getSpeciesId() != null) {
            // Species will be set by the service after validation
        }
    }

    public static BreedResponse toResponse(Breed breed) {
        return BreedResponse.builder()
                .breedId(breed.getBreedId())
                .speciesId(breed.getSpecies().getSpeciesId())
                .speciesName(breed.getSpecies().getName())
                .name(breed.getName())
                .createdAt(breed.getCreatedAt())
                .updatedAt(breed.getUpdatedAt())
                .build();
    }

    public static List<BreedResponse> toResponseList(List<Breed> breeds) {
        return breeds.stream()
                .map(BreedMapper::toResponse)
                .collect(Collectors.toList());
    }
}
