package com.example.AA.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@Data
public class NicknameGenderReqDto {

    private String nickname;
    private String gender;

    @Builder
    public NicknameGenderReqDto(String nickname, String fgender) {
        this.nickname= nickname;
        this.gender= gender;
    }
}
