package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pathology_findings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PathologyFinding extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pathology_id")
    private Long pathologyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(name = "report", nullable = false, columnDefinition = "TEXT")
    private String report;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "pathologist_name", length = 100)
    private String pathologistName;

    @Column(name = "findings_summary", columnDefinition = "TEXT")
    private String findingsSummary;

    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;
}
