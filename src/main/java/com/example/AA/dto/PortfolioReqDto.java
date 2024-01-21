package com.example.AA.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PortfolioReqDto {
    private String gender;
    private String phoneNumber;
    private String workplace;
    private String snsAddress;
    private String introduction;
    private int likesCount;
    private String profileURL;


    @Builder
    public PortfolioReqDto(String gender, String phoneNumber, String workplace,
                           String snsAddress, String introduction, int likesCount, String profileURL) {
            this.gender = gender;
            this.phoneNumber = phoneNumber;
            this.workplace = workplace;
            this.snsAddress = snsAddress;
            this.introduction = introduction;
            this.likesCount = likesCount;
            this.profileURL = profileURL;
        }

}


