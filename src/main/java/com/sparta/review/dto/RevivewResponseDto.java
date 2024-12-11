package com.sparta.review.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RevivewResponseDto {
    private long id;
    private int score;
    private String content;
    private String imageUrl;
    private String createdAt;

}
