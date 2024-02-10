package com.example.AA.dto;

import com.example.AA.entity.HairStyle;
import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import com.example.AA.entity.WorkImage;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;



@Getter
public class PortfolioResDto {
    private User user;
    private Long portfolioId;
    private String gender;
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
    private String hairStyle1;
    private String hairStyle2;
    private String hairStyle3;

    // DesignerDTO를 Designer 엔티티로 변환하는 메서드
    @Builder
    @QueryProjection
    public PortfolioResDto(User user, Portfolio portfolio, WorkImage workImage,
                           HairStyle hairStyle1, HairStyle hairStyle2, HairStyle hairStyle3) {
        this.user = user;
        this.portfolioId = portfolio.getPortfolioId();
        this.gender = portfolio.getGender();
        this.phoneNumber = portfolio.getPhoneNumber();
        this.workplace = portfolio.getWorkplace();
        this.snsAddress = portfolio.getSnsAddress();
        this.introduction = portfolio.getIntroduction();
        this.likesCount = portfolio.getLikesCount();
        this.profileURL = portfolio.getProfileURL();
        this.imageUrl1 = workImage.getImageUrl1();
        this.imageUrl2 = workImage.getImageUrl2();
        this.imageUrl3 = workImage.getImageUrl3();
        this.imageUrl4 = workImage.getImageUrl4();
        this.hairStyle1 = String.valueOf(hairStyle1.getHairName());
        this.hairStyle2 = String.valueOf(hairStyle2.getHairName());
        this.hairStyle3 = String.valueOf(hairStyle3.getHairName());

    }

}
