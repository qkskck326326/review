package com.sparta.review.controller;

import com.sparta.review.dto.RevivewResponseDto;
import com.sparta.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}/reviews?cursor={cursor}&size={size}")
    public Map<String, Map<String, RevivewResponseDto[]>> getReviews(
            @PathVariable Long productId,
            @RequestParam(value = "cursor") int cursor,
            @RequestParam(value = "size") int size) {

        return reviewService.getReviews(productId, cursor, size);
    }

}
