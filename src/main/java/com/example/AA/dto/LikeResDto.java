package com.example.AA.dto;

import lombok.Builder;

public class LikeResDto {
    private Long portfolioId;

    @Builder
    public LikeResDto(Long portfolioId) {
        this.portfolioId= portfolioId;
    }
}