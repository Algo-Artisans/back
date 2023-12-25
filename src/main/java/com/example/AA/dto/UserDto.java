package com.example.AA.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {

    private String name;
    private String picture;

    @Builder
    public UserDto(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }
}
