package com.example.AA.dto;


import com.example.AA.entity.User;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;



@Getter
public class OAuthAttributesResDto {
    private Map<String, Object> attributes;
    private Long kakaoId;
    private String kakaoNickname;
    private String picture;

    @Builder
    public OAuthAttributesResDto(Map<String, Object> attributes, Long kakaoId, String kakaoNickname,
                                 String picture) {
        this.kakaoId = kakaoId;
        this.attributes = attributes;
        this.kakaoNickname = kakaoNickname;
        this.picture = picture;
    }

    public static OAuthAttributesResDto ofKakao(Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");

        return OAuthAttributesResDto.builder()
                .kakaoId((Long)attributes.get("id"))
                .kakaoNickname((String)profile.get("nickname"))
                .picture((String)profile.get("profile_image_url"))
                .attributes(attributes)
                .build();
    }

    public User toEntity() {
        User user = User.builder()
                .kakaoId(kakaoId)
                .kakaoNickname(kakaoNickname)
                .image(picture)
                .build();

        return user;
    }
}
