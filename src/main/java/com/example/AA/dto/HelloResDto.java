package com.example.AA.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@Data
public class HelloResDto {

    private Long helloId;
    private String text;

    @Builder
    public HelloResDto(Long helloId, String text) {
        this.helloId= helloId;
        this.text = text;

    }
}
