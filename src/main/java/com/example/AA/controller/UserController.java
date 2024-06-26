package com.example.AA.controller;


import com.example.AA.dto.*;
import com.example.AA.global.jwt.OAuthUserService;
import com.example.AA.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    private final OAuthUserService oAuthUserService;
    private final PortfolioService portfolioService;

    @Operation(summary = "내 정보 조회")
    @GetMapping("/information")
    public OAuthUserResDto getUserInfo(HttpServletRequest httpRequest) {
        return oAuthUserService.getUser(httpRequest);
    }

    @Operation(summary = "닉네임 및 성별 등록")
    @PostMapping("/nicknameGender")
    public NicknameGenderResDto updateNicknameGender(HttpServletRequest httpRequest, @RequestBody NicknameGenderReqDto nicknameGenderReqDto){
        log.info("updateNicknameGender:" + httpRequest);
        oAuthUserService.updateNicknameGender(httpRequest, nicknameGenderReqDto);
        return new NicknameGenderResDto(nicknameGenderReqDto.getNickname(), nicknameGenderReqDto.getGender());
    }

    @Operation(summary = "얼굴형 AI 등록")
    @PostMapping("/face-shape")
    public FaceShapeResDto updateFaceShape(HttpServletRequest httpRequest, @RequestBody FaceShapeReqDto faceShapeReqDto) {
        oAuthUserService.updateFaceShape(httpRequest,faceShapeReqDto);
        return new FaceShapeResDto(faceShapeReqDto.getFaceShapeBest(), faceShapeReqDto.getFaceShapeWorst());
    }

    @Operation(summary = "디자이너 좋아요")
    @PostMapping("/like/hairstylist")
    public ResponseEntity<LikeResDto> likeHairstylist(HttpServletRequest httpRequest, @RequestBody LikeReqDto likeReqDto) {
        return ResponseEntity.ok(portfolioService.likeHairstylist(httpRequest,likeReqDto));
    }

    @Operation(summary = "내가 누른 디자이너 좋아요 확인")
    @GetMapping("/like/hairstylists")
    public ResponseEntity<List<PortfolioResDto>> getLikeHairstylists(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(portfolioService.getLikeHairstylists(httpRequest));
    }


}
