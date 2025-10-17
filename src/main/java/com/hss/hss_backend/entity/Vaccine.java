package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "vaccine")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Vaccine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private Long vaccineId;

    @Column(name = "vaccine_name", nullable = false, length = 100)
    private String vaccineName;

    @Column(name = "administration_route", length = 50)
    private String administrationRoute;

    @Column(name = "protection_period")
    private Duration protectionPeriod;

    @Column(name = "age_requirement_months")
    private Integer ageRequirementMonths;

    @Column(name = "booster_required")
    @Builder.Default
    private Boolean boosterRequired = false;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "vaccine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VaccinationRecord> vaccinationRecords;
}
