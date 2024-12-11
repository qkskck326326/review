package com.sparta.review.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewRequestDto {
    private String userId;
    private int score;
    private String content;
}
