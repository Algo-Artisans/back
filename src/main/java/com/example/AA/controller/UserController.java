package com.example.AA.controller;


import com.example.AA.dto.*;
import com.example.AA.global.jwt.OAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final OAuthUserService oAuthUserService;

    @GetMapping("/information")
    public OAuthUserResDto getUserInfo(HttpServletRequest httpRequest) {
        return oAuthUserService.getUser(httpRequest);
    }

    @PostMapping("/face-shape")
    public FaceShapeResDto updateFaceShape(HttpServletRequest httpRequest, @RequestBody FaceShapeReqDto faceShapeReqDto) {
        oAuthUserService.updateFaceShape(httpRequest,faceShapeReqDto);
        return new FaceShapeResDto(faceShapeReqDto.getFaceShapeBest(), faceShapeReqDto.getFaceShapeWorst());
    }

}
