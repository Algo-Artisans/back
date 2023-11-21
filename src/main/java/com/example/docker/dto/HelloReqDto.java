package com.example.docker.dto;

import com.example.docker.entity.Hello;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class HelloReqDto {

    private Long helloId;
    private String text;


    @Builder
    public HelloReqDto(Long helloId,String text) {
        this.helloId= helloId;
        this.text = text;

    }
}
