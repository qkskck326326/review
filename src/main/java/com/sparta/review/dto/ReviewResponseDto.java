package com.sparta.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReviewResponseDto {
    private long id;
    private String userId;
    private int score;
    private String content;
    private String imageUrl;
    private String createdAt;

    public ReviewResponseDto(Long id, String userId, int score, String content, String imageUrl, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt.toString();
    }
}
