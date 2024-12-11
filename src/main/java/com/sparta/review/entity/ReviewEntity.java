package com.sparta.review.entity;

import com.sparta.review.dto.ReviewRequestDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Review")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId; // 작성자 유저 아이디

    @Column(nullable = false)
    private int score;

    @Column(nullable = false, length = 500)
    private String content;

    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private ProductEntity product;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public ReviewEntity(ReviewRequestDto requestDto, ProductEntity product) {
        this.userId = requestDto.getUserId();
        this.score = requestDto.getScore();
        this.content = requestDto.getContent();
        this.product = product;
    }
}
