package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "medicine")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Medicine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long medicineId;

    @Column(name = "medicine_name", nullable = false, length = 100)
    private String medicineName;

    @Column(name = "active_substance", length = 100)
    private String activeSubstance;

    @Column(name = "usage_area", length = 100)
    private String usageArea;

    @Column(name = "administration_route", length = 50)
    private String administrationRoute;

    @Column(name = "dosage_form", length = 50)
    private String dosageForm;

    @Column(name = "strength", length = 50)
    private String strength;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;
}
