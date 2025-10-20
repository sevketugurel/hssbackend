package com.hss.hss_backend.service;

import com.hss.hss_backend.dto.request.BreedCreateRequest;
import com.hss.hss_backend.dto.request.BreedUpdateRequest;
import com.hss.hss_backend.dto.response.BreedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BreedService {
    
    BreedResponse createBreed(BreedCreateRequest request);
    
    BreedResponse getBreedById(Long id);
    
    Page<BreedResponse> getAllBreeds(Pageable pageable);
    
    List<BreedResponse> getBreedsBySpeciesId(Long speciesId);
    
    List<BreedResponse> searchBreedsByName(String name);
    
    BreedResponse updateBreed(Long id, BreedUpdateRequest request);
    
    void deleteBreed(Long id);
}
