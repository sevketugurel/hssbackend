package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animal")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Animal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long animalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "microchip_no", length = 50, unique = true)
    private String microchipNo;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "chronic_diseases", columnDefinition = "TEXT")
    private String chronicDiseases;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalHistory> medicalHistories;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClinicalExamination> clinicalExaminations;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LabTest> labTests;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RadiologicalImaging> radiologicalImagings;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VaccinationRecord> vaccinationRecords;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PathologyFinding> pathologyFindings;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }
}
