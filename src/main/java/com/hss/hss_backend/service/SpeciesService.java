package com.hss.hss_backend.service;

import com.hss.hss_backend.dto.request.SpeciesCreateRequest;
import com.hss.hss_backend.dto.request.SpeciesUpdateRequest;
import com.hss.hss_backend.dto.response.SpeciesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpeciesService {
    
    SpeciesResponse createSpecies(SpeciesCreateRequest request);
    
    SpeciesResponse getSpeciesById(Long id);
    
    Page<SpeciesResponse> getAllSpecies(Pageable pageable);
    
    List<SpeciesResponse> getAllSpeciesList();
    
    List<SpeciesResponse> searchSpeciesByName(String name);
    
    SpeciesResponse updateSpecies(Long id, SpeciesUpdateRequest request);
    
    void deleteSpecies(Long id);
}
