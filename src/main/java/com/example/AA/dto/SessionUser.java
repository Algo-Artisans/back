package com.example.AA.dto;

import com.example.AA.entity.User;

import lombok.Getter;

import java.io.Serializable;

// 카카오 로그인 확인용 session 생성
@Getter
public class SessionUser implements Serializable {
    private String nickname;
    private String image;

    public SessionUser(User user) {
        this.nickname = user.getNickname();
        this.image = user.getImage();
    }
}
