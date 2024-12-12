package com.sparta.review.controller;

import com.sparta.review.dto.ReviewRequestDto;
import com.sparta.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}/reviews")
    public Map<String, Object> getReviews(
            @PathVariable Long productId,
            @RequestParam int cursor,
            @RequestParam int size) {

        return reviewService.getReviews(productId, cursor, size);
    }

    @PostMapping("/{productId}/reviews")
    public String addReview(@PathVariable long productId,
                            @RequestBody ReviewRequestDto reviewRequestDto) {
        System.out.println("productId = " + productId);
        return reviewService.addReview(productId, reviewRequestDto);
    }

}
