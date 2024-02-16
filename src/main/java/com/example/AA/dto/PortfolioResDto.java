package com.example.AA.dto;

import com.example.AA.entity.*;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    public PortfolioResDto(Portfolio portfolio) {
        WorkImage workImage = portfolio.getWorkImage();
        List<PortfolioHairStyle> portfolioHairStyles = portfolio.getPortfolioHairStyles();
        // List<HairStyle> hairStyles = portfolioHairStyles.stream() //여기서 null이 받아짐
        //     .map(PortfolioHairStyle::getHairStyle)
        //     .collect(Collectors.toList());
        List<HairStyle> hairStyles = new ArrayList<>();
        System.out.println("PortfolioHairStyle: " + portfolioHairStyles); //왜 null??
        for (PortfolioHairStyle portfolioHairStyle : portfolioHairStyles) {
            hairStyles.add(portfolioHairStyle.getHairStyle());
        }


        this.user = portfolio.getUser();
        this.portfolioId = portfolio.getPortfolioId();
        this.gender = portfolio.getGender();
        this.phoneNumber = portfolio.getPhoneNumber();
        this.workplace = portfolio.getWorkplace();
        this.snsAddress = portfolio.getSnsAddress();
        this.introduction = portfolio.getIntroduction();
        this.likesCount = portfolio.getLikesCount();
        this.profileURL = portfolio.getProfileURL();
        if (workImage != null) {
            this.imageUrl1 = workImage.getImageUrl1();
            this.imageUrl2 = workImage.getImageUrl2();
            this.imageUrl3 = workImage.getImageUrl3();
            this.imageUrl4 = workImage.getImageUrl4();
        }
        this.hairStyle1 = String.valueOf(hairStyles.get(0).getHairName());
        this.hairStyle2 = String.valueOf(hairStyles.get(1).getHairName());
        this.hairStyle3 = String.valueOf(hairStyles.get(2).getHairName());
    }
}
