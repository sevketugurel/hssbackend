package com.hss.hss_backend.mapper;

import com.hss.hss_backend.dto.response.VaccinationResponse;
import com.hss.hss_backend.entity.VaccinationRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VaccinationMapper {

    public static VaccinationResponse toResponse(VaccinationRecord vaccinationRecord) {
        return VaccinationResponse.builder()
                .vaccinationRecordId(vaccinationRecord.getVaccinationRecordId())
                .animalId(vaccinationRecord.getAnimal().getAnimalId())
                .animalName(vaccinationRecord.getAnimal().getName())
                .vaccineId(vaccinationRecord.getVaccine().getVaccineId())
                .vaccineName(vaccinationRecord.getVaccineName())
                .date(vaccinationRecord.getDate())
                .nextDueDate(vaccinationRecord.getNextDueDate())
                .batchNumber(vaccinationRecord.getBatchNumber())
                .veterinarianName(vaccinationRecord.getVeterinarianName())
                .notes(vaccinationRecord.getNotes())
                .createdAt(vaccinationRecord.getCreatedAt())
                .updatedAt(vaccinationRecord.getUpdatedAt())
                .build();
    }

    public static List<VaccinationResponse> toResponseList(List<VaccinationRecord> vaccinationRecords) {
        return vaccinationRecords.stream()
                .map(VaccinationMapper::toResponse)
                .collect(Collectors.toList());
    }
}
