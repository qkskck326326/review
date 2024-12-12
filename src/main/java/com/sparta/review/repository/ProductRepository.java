package com.sparta.review.repository;

import com.sparta.review.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT p.reviewCount FROM ProductEntity p WHERE p.id = :id")
    Optional<Long> findReviewCountById(@Param("id") Long productId);
}
