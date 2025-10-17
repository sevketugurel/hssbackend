package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "invoice_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InvoiceItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_item_id")
    private Long invoiceItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "quantity", nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "vat_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal vatRate = BigDecimal.ZERO;

    @Column(name = "vat_amount", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal vatAmount = BigDecimal.ZERO;

    @Column(name = "line_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal lineTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", length = 50)
    @Builder.Default
    private ItemType itemType = ItemType.SERVICE;

    public enum ItemType {
        SERVICE, MEDICINE, VACCINE, CONSULTATION, SURGERY, LAB_TEST, OTHER
    }
}
