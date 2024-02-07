package com.example.AA.controller;


import com.example.AA.global.jwt.OAuthUserService;
import com.example.AA.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final OAuthUserService oAuthUserService;
    private final PortfolioService portfolioService;

    // 고객 정보 보기
    @GetMapping("/information")
    public OAuthUserResDto getUserInfo(HttpServletRequest httpRequest) {
        return oAuthUserService.getUser(httpRequest);
    }

    // 얼굴형 AI 등록
    @PostMapping("/face-shape")
    public FaceShapeResDto updateFaceShape(HttpServletRequest httpRequest, @RequestBody FaceShapeReqDto faceShapeReqDto) {
        oAuthUserService.updateFaceShape(httpRequest,faceShapeReqDto);
        return new FaceShapeResDto(faceShapeReqDto.getFaceShapeBest(), faceShapeReqDto.getFaceShapeWorst());
    }

    // 헤어스타일링에 어울리는 디자이너 추천
//    @GetMapping("/recommend/hairstylists")
//    public ResponseEntity<List<PortfolioResDto>> recommendHairStylists(HttpServletRequest httpRequest, @RequestParam(name = "hairstyle") String s){
//        return ResponseEntity.ok(portfolioService.searchPortfolio(httpRequest,s));
//    }



    // 디자이너 좋아요
    @PostMapping("/Like/hairstylist")
    public ResponseEntity<LikeResDto> likeHairstylist(HttpServletRequest httpRequest, @RequestBody LikeReqDto likeReqDto) {
        return ResponseEntity.ok(portfolioService.likeHairstylist(httpRequest,likeReqDto));
    }

    // 내가 누른 디자이너 좋아요 확인
    @GetMapping("/Like/hairstylists")
    public ResponseEntity<List<LikeResDto>> getLikeHairstylists(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(portfolioService.getLikeHairstylists(httpRequest));
    }


}
