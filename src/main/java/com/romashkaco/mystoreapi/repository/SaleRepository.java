package com.romashkaco.mystoreapi.repository;

import com.romashkaco.mystoreapi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByProductId(Long productId);
    @Query("SELECT COALESCE(SUM(s.quantity), 0) FROM Sale s WHERE s.product.id = :productId")
    Integer sumQuantityByProductId(@Param("productId") Long productId);
}
