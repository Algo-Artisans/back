package com.example.AA.controller;


import com.example.AA.dto.*;
import com.example.AA.entity.FaceShape;
import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import com.example.AA.global.jwt.OAuthUserService;
import com.example.AA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
