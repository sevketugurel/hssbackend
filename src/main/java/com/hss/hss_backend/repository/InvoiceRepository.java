package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByOwnerOwnerId(Long ownerId);

    List<Invoice> findByStatus(Invoice.Status status);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    @Query("SELECT i FROM Invoice i WHERE i.date BETWEEN :startDate AND :endDate")
    List<Invoice> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT i FROM Invoice i WHERE i.owner.ownerId = :ownerId AND i.date BETWEEN :startDate AND :endDate")
    List<Invoice> findByOwnerIdAndDateBetween(@Param("ownerId") Long ownerId, 
                                             @Param("startDate") LocalDate startDate, 
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT i FROM Invoice i WHERE i.status = :status AND i.dueDate < :date")
    List<Invoice> findOverdueInvoices(@Param("status") Invoice.Status status, @Param("date") LocalDate date);

    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.status = :status AND i.date BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalAmountByStatusAndDateRange(@Param("status") Invoice.Status status, 
                                                       @Param("startDate") LocalDate startDate, 
                                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT i FROM Invoice i WHERE i.totalAmount >= :minAmount AND i.totalAmount <= :maxAmount")
    List<Invoice> findByAmountRange(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);

    boolean existsByInvoiceNumber(String invoiceNumber);
}
