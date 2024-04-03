package com.example.AA.dto;

import com.example.AA.entity.User;

import lombok.Getter;

import java.io.Serializable;

// 카카오 로그인 확인용 session 생성
@Getter
public class SessionUser implements Serializable {
    private String kakaoNickname;
    private String image;

    public SessionUser(User user) {
        this.kakaoNickname = user.getKakaoNickname();
        this.image = user.getImage();
    }
}
