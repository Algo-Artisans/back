package com.example.AA.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@Data
public class LikeResDto {
    private Long portfolioId;

    @Builder
    public LikeResDto(Long portfolioId) {
        this.portfolioId= portfolioId;
    }
}