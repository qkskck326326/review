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

    @GetMapping("/{productId}/reviews?cursor={cursor}&size={size}")
    public Map<String, Object> getReviews(
            @PathVariable int cursor, @PathVariable long productId, @PathVariable int size) {

        return reviewService.getReviews(productId, cursor, size);
    }

    @PostMapping("/{productId}/reviews")
    public String addReview(@PathVariable long productId,
                            @RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.addReview(productId, reviewRequestDto);
    }

}
