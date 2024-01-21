package com.example.AA.dto;

import com.example.AA.entity.FaceShape;
import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import com.example.AA.entity.WorkImage;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PortfolioResDto {
    private User user;
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

    // DesignerDTO를 Designer 엔티티로 변환하는 메서드
    @Builder
    public PortfolioResDto(User user, Portfolio portfolio, WorkImage workImage) {
        this.user = user;
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
    }

}
