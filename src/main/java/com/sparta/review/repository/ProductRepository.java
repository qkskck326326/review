package com.sparta.review.repository;

import com.sparta.review.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    int getReviewCountByProductId(Long productId);
}
