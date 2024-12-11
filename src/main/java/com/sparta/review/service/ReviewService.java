package com.sparta.review.service;

import com.sparta.review.dto.RevivewResponseDto;
import com.sparta.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Map<String, Map<String, RevivewResponseDto[]>> getReviews(Long productId, int cursor, int size){

    }
}
