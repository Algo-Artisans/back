package com.example.AA.controller;

import com.example.AA.dto.PortfolioReqDto;
import com.example.AA.dto.PortfolioResDto;
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
public class PortfolioController {

    private final OAuthUserService oAuthUserService;
    private final PortfolioService portfolioService;

    // 내 포트폴리오 등록
    @PostMapping("/myPortfolio")
    public ResponseEntity<PortfolioResDto> postPortfolio(HttpServletRequest httpRequest, @RequestBody PortfolioReqDto portfolioReqDto) {
         return ResponseEntity.ok(oAuthUserService.createPortfolio(httpRequest, portfolioReqDto));
    }

    // 내 포트폴리오 조회
    @GetMapping("/myPortfolio")
    public ResponseEntity<PortfolioResDto> getPortfolio(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(portfolioService.getPortfolio(httpRequest));
    }

    // 디자이너 포트폴리오 전체 조회
    @GetMapping("/portfolios")
    public ResponseEntity<List<PortfolioResDto>> getPortfolios(){
        List<PortfolioResDto> portfolioListResDto = portfolioService.getPortfolios;
        return ResponseEntity.ok(portfolioListResDto);
    }

    // 디자이너 포트폴리오 키워드 필터링
    @GetMapping("/search/portfilios")
    public ResponseEntity<PortfolioSearchResDto> searchPortfolio(HttpServletRequest httpRequest,@RequestParam(name = "hairstyle") String s){
        return ResponseEntity.ok(portfolioService.searchPortfolio(httpRequest,s));
    }

    // 디자이너 포트폴리오 드롭다운 조회
    @GetMapping("/dropdown/portfilios")
    public ResponseEntity<PortfolioDropDownResDto> dropdownPortfolio(HttpServletRequest httpRequest,@RequestParam(name = "dropdown") String s){
        return ResponseEntity.ok(portfolioService.dropdownPortfolio(httpRequest,s));
    }

    // 내 포트폴리오 삭제
    @DeleteMapping("/myPortfolio")
    public ResponseEntity<?> deletePortfolio(HttpServletRequest httpRequest){
        return ResponseEntity.ok(portfolioService.deletePortfolio(httpRequest));
    }

    // 내가 받은 좋아요 조회
    @GetMapping("/myPortfolioLikes")
    public ResponseEntity<PortfolioLikesResDto> getPortfolioLikes(HttpServletRequest httpRequest){
        return ResponseEntity.ok(portfolioService.getPortfolioLikes(httpRequest));
    }

    // 내 광고 등록
    @PostMapping("/myAdvertisement")
    public ResponseEntity<AdvertisementResDto> postAdvertisement(HttpServletRequest httpRequest, @RequestBody AdvertisementReqDto advertisementReqDto) {
        return ResponseEntity.ok(portfolioService.createAdvertisement(httpRequest, advertisementReqDto));
    }

    // 내 광고 삭제
    @DeleteMapping("/myAdvertisement")
    public ResponseEntity<?> postAdvertisement(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(portfolioService.deleteAdvertisement(httpRequest));
    }
}
