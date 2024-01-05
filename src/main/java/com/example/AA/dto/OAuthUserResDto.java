package com.example.AA.dto;

import com.example.AA.entity.FaceShape;
import com.example.AA.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
public class OAuthUserResDto {
    private String nickname;
    private String picture;
    private String role;
    private FaceShape faceShapeBest;
    private FaceShape faceShapeWorst;

    @Builder
    public OAuthUserResDto(User user){
        this.nickname = user.getNickname();
        this.picture = user.getImage();
        this.role = String.valueOf(user.getRole());
        this.faceShapeBest = user.getFaceShapeBest();
        this.faceShapeWorst = user.getFaceShapeWorst();
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }
}

