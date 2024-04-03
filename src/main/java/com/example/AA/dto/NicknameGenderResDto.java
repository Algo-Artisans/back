package com.example.AA.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@Data
public class NicknameGenderResDto {
    private String nickname;
    private String gender;

    @Builder
    public NicknameGenderResDto(String nickname, String gender) {
            this.nickname= nickname;
            this.gender= gender;
    }
    
}

