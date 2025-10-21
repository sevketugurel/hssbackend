package com.hss.hss_backend.service.impl;

import com.hss.hss_backend.dto.request.SpeciesCreateRequest;
import com.hss.hss_backend.dto.request.SpeciesUpdateRequest;
import com.hss.hss_backend.dto.response.SpeciesResponse;
import com.hss.hss_backend.entity.Species;
import com.hss.hss_backend.repository.SpeciesRepository;
import com.hss.hss_backend.service.SpeciesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SpeciesServiceImpl implements SpeciesService {

    private final SpeciesRepository speciesRepository;

    @Override
    public SpeciesResponse createSpecies(SpeciesCreateRequest request) {
        log.info("Creating species with name: {}", request.getName());
        
        // Check if species with same name already exists
        if (speciesRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Species with name '" + request.getName() + "' already exists");
        }
        
        Species species = Species.builder()
                .name(request.getName())
                .build();
        
        Species savedSpecies = speciesRepository.save(species);
        log.info("Species created successfully with ID: {}", savedSpecies.getSpeciesId());
        
        return mapToResponse(savedSpecies);
    }

    @Override
    @Transactional(readOnly = true)
    public SpeciesResponse getSpeciesById(Long id) {
        log.info("Fetching species with ID: {}", id);
        
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Species not found with ID: " + id));
        
        return mapToResponse(species);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpeciesResponse> getAllSpecies(Pageable pageable) {
        log.info("Fetching all species with pagination");
        
        Page<Species> speciesPage = speciesRepository.findAll(pageable);
        
        return speciesPage.map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpeciesResponse> getAllSpeciesList() {
        log.info("Fetching all species as list");
        
        List<Species> species = speciesRepository.findAllOrderByName();
        
        return species.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpeciesResponse> searchSpeciesByName(String name) {
        log.info("Searching species by name: {}", name);
        
        List<Species> species = speciesRepository.findByNameContainingIgnoreCase(name);
        
        return species.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SpeciesResponse updateSpecies(Long id, SpeciesUpdateRequest request) {
        log.info("Updating species with ID: {}", id);
        
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Species not found with ID: " + id));
        
        // Check if another species with same name exists (excluding current one)
        speciesRepository.findByName(request.getName())
                .ifPresent(existingSpecies -> {
                    if (!existingSpecies.getSpeciesId().equals(id)) {
                        throw new IllegalArgumentException("Species with name '" + request.getName() + "' already exists");
                    }
                });
        
        species.setName(request.getName());
        Species updatedSpecies = speciesRepository.save(species);
        
        log.info("Species updated successfully with ID: {}", updatedSpecies.getSpeciesId());
        
        return mapToResponse(updatedSpecies);
    }

    @Override
    public void deleteSpecies(Long id) {
        log.info("Deleting species with ID: {}", id);
        
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Species not found with ID: " + id));
        
        // Check if species has associated animals or breeds
        if (species.getAnimals() != null && !species.getAnimals().isEmpty()) {
            throw new IllegalStateException("Cannot delete species with associated animals. Please remove animals first.");
        }
        
        if (species.getBreeds() != null && !species.getBreeds().isEmpty()) {
            throw new IllegalStateException("Cannot delete species with associated breeds. Please remove breeds first.");
        }
        
        speciesRepository.delete(species);
        log.info("Species deleted successfully with ID: {}", id);
    }

    private SpeciesResponse mapToResponse(Species species) {
        return SpeciesResponse.builder()
                .speciesId(species.getSpeciesId())
                .name(species.getName())
                .createdAt(species.getCreatedAt())
                .updatedAt(species.getUpdatedAt())
                .build();
    }
}

