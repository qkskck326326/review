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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public Map<String, Object> getReviews(Long productId, int cursor, int size) {
//        // 해당 상품이 있는지 확인
//        if(productRepository.existsById(productId)) {
//            Map<String, Object> result = new HashMap<>();
//            result.put("message", "상품이 존재하지 않습니다.");
//            return result;
//        }
//        // 등록된 리뷰가 있는지 확인
//        if(reviewRepository.existsById(productId)) {
//            Map<String, Object> result = new HashMap<>();
//            result.put("message", "등록된 리뷰가 없습니다.");
//            return result;
//        }
        // 페이징 계산 및 계산 결과에 따른 조회
        Pageable pageable = PageRequest.of(cursor / size, size);
        Page<ReviewEntity> reviewPage = reviewRepository.findByProductId(productId, pageable).orElse(null);
        if (reviewPage.isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", "등록된 리뷰가 없습니다.");
            return result;
        }

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

        // 리뷰 갯수
        long totalCount = productRepository.findReviewCountById(productId).orElse(0L);
        
        // 점수평균 계산
        double averageScore = reviewDtos.stream().mapToInt(ReviewResponseDto::getScore).average().orElse(0.0);

        // 결과 저장
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("totalCount", totalCount);
        response.put("score", averageScore);
        response.put("cursor", cursor + reviewDtos.size());
        response.put("reviews", reviewDtos);

        return response;
    }

    public String addReview(long productId, ReviewRequestDto reviewRequestDto){
        String userId = reviewRequestDto.getUserId();
        if (reviewRepository.existsByUserIdAndProductId(userId, productId)){
            return "이미 해당상품에 대한 리뷰를 작성한 사용자입니다.";
        }
        ProductEntity product;
        try {
            product = productRepository.findById(productId)
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
