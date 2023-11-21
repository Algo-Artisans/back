package com.example.docker.controller;

import com.example.docker.dto.HelloReqDto;
import com.example.docker.dto.HelloResDto;
import com.example.docker.entity.Hello;
import com.example.docker.repository.HelloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
