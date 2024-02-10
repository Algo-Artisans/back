package com.example.AA.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PortfolioLikesResDto {
    private int totalLikes;
    private List<OAuthUserResDto> usersLiked;

    @Builder
    public PortfolioLikesResDto(int totalLikes, List<OAuthUserResDto> usersLiked) {
        this.totalLikes = totalLikes;
        this.usersLiked = usersLiked;
    }
}
