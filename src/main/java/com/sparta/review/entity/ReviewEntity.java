package com.sparta.review.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "review")
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

    @Column(nullable = false)
    private String createdAt;
}
