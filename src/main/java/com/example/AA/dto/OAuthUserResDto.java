package com.example.AA.dto;

import com.example.AA.entity.enumtype.FaceShape;
import com.example.AA.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
public class OAuthUserResDto {
    private String kakaoNickname;
    private String picture;
    private String role;
    private String gender;
    private String nickname;
    private String faceShapeBest;
    private String faceShapeWorst;
    private String kakaoId;


    @Builder
    public OAuthUserResDto(User user){
        this.kakaoNickname = user.getKakaoNickname();
        this.picture = user.getImage();
        this.role = String.valueOf(user.getRole());
        this.gender = user.getGender();
        this.nickname = user.getNickname();
        this.faceShapeBest = user.getFaceShapeBest();
        this.faceShapeWorst = user.getFaceShapeWorst();
        this.kakaoId = String.valueOf(user.getKakaoId());
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }
}

