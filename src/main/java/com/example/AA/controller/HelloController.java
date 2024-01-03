package com.example.AA.controller;

import com.example.AA.dto.HelloReqDto;
import com.example.AA.dto.HelloResDto;
import com.example.AA.entity.Hello;
import com.example.AA.repository.HelloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/receiveImage")
    public ResponseEntity<String> receiveImage(@RequestPart("image") MultipartFile image) {
        try {
            // 이미지 파일 이름 확인
            String imageName = image.getOriginalFilename();

            // 성공적으로 이미지를 받았다는 응답 전송
            return ResponseEntity.ok().body("Image received successfully. Image Name: " + imageName);
        } catch (Exception e) {
            // 에러가 발생했을 경우 에러 응답 전송
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
