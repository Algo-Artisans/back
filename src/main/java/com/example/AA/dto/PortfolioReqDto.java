package com.example.AA.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PortfolioReqDto {
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
    private String hairName1;
    private String hairName2;
    private String hairName3;


    @Builder
    public PortfolioReqDto(String gender, String phoneNumber, String workplace,
                           String snsAddress, String introduction, int likesCount, String profileURL,
                           String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4,
                           String hairName1, String hairName2, String hairName3) {

            this.gender = gender;
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
            this.hairName1 = hairName1;
            this.hairName2 = hairName2;
            this.hairName3 = hairName3;
        }

}


