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
@Table(name = "equipment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Equipment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long equipmentId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "model", length = 100)
    private String model;

    @Column(name = "serial_no", length = 100, unique = true)
    private String serialNo;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "warranty_expiry")
    private LocalDate warrantyExpiry;

    @Column(name = "location", length = 100)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private Status status = Status.ACTIVE;

    @Column(name = "last_maintenance_date")
    private LocalDate lastMaintenanceDate;

    @Column(name = "next_maintenance_date")
    private LocalDate nextMaintenanceDate;

    @Column(name = "purchase_cost", precision = 10, scale = 2)
    private BigDecimal purchaseCost;

    @Column(name = "current_value", precision = 10, scale = 2)
    private BigDecimal currentValue;

    @Column(name = "supplier", length = 100)
    private String supplier;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Maintenance> maintenances;

    public enum Status {
        ACTIVE, MAINTENANCE, RETIRED, BROKEN
    }
}
