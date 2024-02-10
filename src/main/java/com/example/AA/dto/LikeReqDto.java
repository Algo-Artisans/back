package com.example.AA.dto;

import lombok.*;
@Getter
@NoArgsConstructor
@Setter
@Data
public class LikeReqDto {

    private Long portfolioId;

    @Builder
    public LikeReqDto(Long portfolioId) {
        this.portfolioId=portfolioId;
    }
}