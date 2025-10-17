package com.hss.hss_backend.mapper;

import com.hss.hss_backend.dto.response.AppointmentResponse;
import com.hss.hss_backend.entity.Appointment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {

    public static AppointmentResponse toResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .appointmentId(appointment.getAppointmentId())
                .animalId(appointment.getAnimal().getAnimalId())
                .animalName(appointment.getAnimal().getName())
                .ownerName(appointment.getAnimal().getOwner().getFirstName() + " " + 
                          appointment.getAnimal().getOwner().getLastName())
                .dateTime(appointment.getDateTime())
                .subject(appointment.getSubject())
                .veterinarianId(appointment.getVeterinarianId())
                .veterinarianName(null) // Will be populated by service layer
                .status(appointment.getStatus().name())
                .notes(appointment.getNotes())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }

    public static List<AppointmentResponse> toResponseList(List<Appointment> appointments) {
        return appointments.stream()
                .map(AppointmentMapper::toResponse)
                .collect(Collectors.toList());
    }
}
