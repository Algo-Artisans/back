package com.example.AA.config;

import com.example.AA.dto.UserDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public UserDto toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return UserDto.builder()
                .name((String)attributes.get("name"))
                .picture((String)attributes.get("picture"))
                .build();
    }
}
