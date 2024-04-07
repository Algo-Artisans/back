package com.example.AA.dto;

import com.example.AA.entity.HairStyle;
import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;


@Getter
public class PortfolioResDto {
    private User user;
    private Long portfolioId;
    private String name;
    private String phoneNumber;
    private String workplace;
    private String snsAddress;
    private String introduction;
    private int likesCount;
    private String profileURL;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String styling1;
    private String cost1;
    private String styling2;
    private String cost2;
    private String styling3;
    private String cost3;
    private String styling4;
    private String cost4;
    private String hairName1;
    private String hairName2;
    private String hairName3;
    private int isAdvertise;


    @Builder
    @QueryProjection
    public PortfolioResDto(User user, Long portfolioId, String name, String phoneNumber, String workplace,
                           String snsAddress, String introduction, int likesCount, String profileURL,
                           String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4,
                           String styling1, String cost1, String styling2, String cost2,
                           String styling3, String cost3, String styling4, String cost4,
                           String hairName1, String hairName2, String hairName3, int isAdvertise) {
        this.user = user;
        this.portfolioId = portfolioId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.workplace = workplace;
        this.snsAddress = snsAddress;
        this.introduction = introduction;
        this.likesCount = likesCount;
        this.profileURL = profileURL;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
        this.cost1 = cost1;
        this.styling2 = styling2;
        this.cost2 = cost2;
        this.styling3 = styling3;
        this.cost3 = cost3;
        this.styling4 = styling4;
        this.cost4 = cost4;
        this.hairName1 = hairName1;
        this.hairName2 = hairName2;
        this.hairName3 = hairName3;
        this.isAdvertise = isAdvertise;
    }

}
