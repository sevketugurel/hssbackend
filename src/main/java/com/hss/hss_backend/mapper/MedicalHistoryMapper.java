package com.hss.hss_backend.mapper;

import com.hss.hss_backend.dto.response.MedicalHistoryResponse;
import com.hss.hss_backend.entity.MedicalHistory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalHistoryMapper {

    public static MedicalHistoryResponse toResponse(MedicalHistory medicalHistory) {
        return MedicalHistoryResponse.builder()
                .historyId(medicalHistory.getHistoryId())
                .animalId(medicalHistory.getAnimal().getAnimalId())
                .animalName(medicalHistory.getAnimal().getName())
                .diagnosis(medicalHistory.getDiagnosis())
                .date(medicalHistory.getDate())
                .treatment(medicalHistory.getTreatment())
                .createdAt(medicalHistory.getCreatedAt())
                .updatedAt(medicalHistory.getUpdatedAt())
                .build();
    }

    public static List<MedicalHistoryResponse> toResponseList(List<MedicalHistory> medicalHistories) {
        return medicalHistories.stream()
                .map(MedicalHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
