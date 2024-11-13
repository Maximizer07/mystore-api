package com.romashkaco.mystoreapi.repository;

import com.romashkaco.mystoreapi.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findAllByProductId(Long productId);

    @Query("SELECT SUM(s.quantity) FROM Supply s WHERE s.product.id = :productId")
    Integer sumQuantityByProductId(@Param("productId") Long productId);
}
