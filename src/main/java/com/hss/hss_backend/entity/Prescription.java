package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "prescription")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Prescription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private Long prescriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "medicines", nullable = false, columnDefinition = "TEXT")
    private String medicines;

    @Column(name = "dosage", nullable = false, columnDefinition = "TEXT")
    private String dosage;

    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private Status status = Status.ACTIVE;

    public enum Status {
        ACTIVE, COMPLETED, CANCELLED
    }
}
