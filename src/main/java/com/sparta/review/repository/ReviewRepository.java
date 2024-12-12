package com.sparta.review.repository;

import com.sparta.review.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<Page<ReviewEntity>> findByProductId(Long productId, Pageable pageable);
    boolean existsByUserIdAndProductId(String userId, Long productId);
}
