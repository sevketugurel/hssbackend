package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Maintenance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private Long maintenanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Column(name = "maintenance_date", nullable = false)
    private LocalDate maintenanceDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "maintenance_type", length = 50)
    @Builder.Default
    private MaintenanceType maintenanceType = MaintenanceType.ROUTINE;

    @Column(name = "performed_by", length = 100)
    private String performedBy;

    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "next_maintenance_date")
    private LocalDate nextMaintenanceDate;

    public enum MaintenanceType {
        ROUTINE, REPAIR, CALIBRATION, INSPECTION
    }
}
