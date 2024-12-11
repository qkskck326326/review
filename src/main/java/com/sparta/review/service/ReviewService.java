package com.sparta.review.service;

import com.sparta.review.dto.ReviewRequestDto;
import com.sparta.review.dto.ReviewResponseDto;
import com.sparta.review.entity.ProductEntity;
import com.sparta.review.entity.ReviewEntity;
import com.sparta.review.repository.ProductRepository;
import com.sparta.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public Map<String, Object> getReviews(Long productId, int cursor, int size) {
        // 페이징 계산 및 계산 결과에 따른 조회
        Pageable pageable = PageRequest.of(cursor / size, size);
        Page<ReviewEntity> reviewPage = reviewRepository.findByProductId(productId, pageable);

        // 조회값 리스트로 변환
        List<ReviewResponseDto> reviewDtos = reviewPage.getContent().stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getUserId(),
                        review.getScore(),
                        review.getContent(),
                        review.getImageUrl(),
                        review.getCreatedAt()
                ))
                .collect(Collectors.toList());

        // 점수평균 계산
        int totalCount = productRepository.getReviewCountByProductId(productId);
        double averageScore = reviewDtos.stream().mapToInt(ReviewResponseDto::getScore).average().orElse(0.0);

        // 결과 저장
        Map<String, Object> response = new HashMap<>();
        response.put("totalCount", totalCount);
        response.put("score", averageScore);
        response.put("cursor", cursor + reviewDtos.size());
        response.put("reviews", reviewDtos);

        return response;
    }

    public String addReview(long productId, ReviewRequestDto reviewRequestDto){
        ProductEntity product;
        try {
            product = reviewRepository.findById(productId)
                    .orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException e) {
            return "잘못된 상품 아이디입니다.";
        }

        ReviewEntity review = new ReviewEntity(reviewRequestDto, product);

        try {
            reviewRepository.save(review);
            product.setReviewCount(product.getReviewCount() + 1);
            productRepository.save(product);
            return "리뷰 등록이 완료되었습니다.";
        } catch (Exception e) {
            return "리뷰 등록중 오류가 발생하였습니다.";
        }

    }
}
