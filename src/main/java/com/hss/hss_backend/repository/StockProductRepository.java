package com.hss.hss_backend.repository;

import com.hss.hss_backend.entity.StockProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockProductRepository extends JpaRepository<StockProduct, Long> {

    List<StockProduct> findByCategory(StockProduct.Category category);

    List<StockProduct> findByIsActive(Boolean isActive);

    Optional<StockProduct> findByBarcode(String barcode);

    List<StockProduct> findByNameContainingIgnoreCase(String name);

    @Query("SELECT sp FROM StockProduct sp WHERE sp.currentStock <= sp.minStock AND sp.isActive = true")
    List<StockProduct> findLowStockProducts();

    @Query("SELECT sp FROM StockProduct sp WHERE sp.expirationDate <= :date AND sp.isActive = true")
    List<StockProduct> findExpiringProducts(@Param("date") LocalDate date);

    @Query("SELECT sp FROM StockProduct sp WHERE sp.expirationDate BETWEEN :startDate AND :endDate AND sp.isActive = true")
    List<StockProduct> findProductsExpiringBetween(@Param("startDate") LocalDate startDate, 
                                                  @Param("endDate") LocalDate endDate);

    @Query("SELECT sp FROM StockProduct sp WHERE sp.supplier = :supplier AND sp.isActive = true")
    List<StockProduct> findBySupplier(@Param("supplier") String supplier);

    @Query("SELECT sp FROM StockProduct sp WHERE sp.location = :location AND sp.isActive = true")
    List<StockProduct> findByLocation(@Param("location") String location);

    @Query("SELECT sp FROM StockProduct sp WHERE sp.currentStock > 0 AND sp.isActive = true")
    List<StockProduct> findInStockProducts();

    @Query("SELECT sp FROM StockProduct sp WHERE sp.currentStock = 0 AND sp.isActive = true")
    List<StockProduct> findOutOfStockProducts();

    boolean existsByBarcode(String barcode);
}
