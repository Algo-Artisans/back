package com.example.AA.controller;

import com.example.AA.dto.HelloReqDto;
import com.example.AA.dto.HelloResDto;
import com.example.AA.entity.Hello;
import com.example.AA.repository.HelloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final HelloRepository helloRepository;
    @PostMapping("/hello")
    public HelloResDto hello(HttpServletRequest httpRequest, @RequestBody HelloReqDto helloReqDto){
        Hello hello = Hello.builder()
                .text(helloReqDto.getText())
                        .helloId(helloReqDto.getHelloId())
                                .build();
        helloRepository.save(hello);
        return new HelloResDto(helloReqDto.getHelloId(),helloReqDto.getText());
    }
}
