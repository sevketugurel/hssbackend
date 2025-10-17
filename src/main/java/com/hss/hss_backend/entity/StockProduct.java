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
@Table(name = "stock_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StockProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "barcode", length = 50, unique = true)
    private String barcode;

    @Column(name = "lot_no", length = 50)
    private String lotNo;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "min_stock")
    @Builder.Default
    private Integer minStock = 0;

    @Column(name = "max_stock")
    @Builder.Default
    private Integer maxStock = 0;

    @Column(name = "current_stock")
    @Builder.Default
    private Integer currentStock = 0;

    @Column(name = "unit_cost", precision = 10, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 50)
    @Builder.Default
    private Category category = Category.GENERAL;

    @Column(name = "supplier", length = 100)
    private String supplier;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @OneToMany(mappedBy = "stockProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StockTransaction> stockTransactions;

    public enum Category {
        MEDICINE, VACCINE, SUPPLY, EQUIPMENT, GENERAL
    }
}
