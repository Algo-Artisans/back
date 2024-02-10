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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PortfolioController {

    private final OAuthUserService oAuthUserService;
    private final PortfolioService portfolioService;


    @Operation(summary = "내 포트폴리오 등록")
    @PostMapping("/myPortfolio")
    public ResponseEntity<PortfolioResDto> postPortfolio(HttpServletRequest httpRequest, @RequestBody PortfolioReqDto portfolioReqDto) {
        log.info("postPortfolio");
        return ResponseEntity.ok(oAuthUserService.createPortfolio(httpRequest, portfolioReqDto));
    }

    @Operation(summary = "내 포트폴리오 조회")
    @GetMapping("/myPortfolio")
    public ResponseEntity<PortfolioResDto> getPortfolio(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(portfolioService.getPortfolio(httpRequest));
    }

    @Operation(summary = "디자이너 포트폴리오 전체 조회")
    @GetMapping("/portfolios")
    public ResponseEntity<List<PortfolioResDto>> getPortfolios(HttpServletRequest httpRequest){
        List<PortfolioResDto> portfolioListResDto = portfolioService.getPortfolios(httpRequest);
        return ResponseEntity.ok(portfolioListResDto);
    }


    @Operation(summary = "디자이너 포트폴리오 키워드 필터링")
    @GetMapping("/search/portfolios")
    public ResponseEntity<List<PortfolioResDto>> searchPortfolio(HttpServletRequest httpRequest,@RequestParam(name = "hairstyle") String s){
        log.info("searchPortfolio");
        return ResponseEntity.ok(portfolioService.searchPortfolio(httpRequest,s));
    }

    //@Operation(summary = "디자이너 포트폴리오 드롭다운 조회")
//    @GetMapping("/dropdown/portfilios")
//    public ResponseEntity<PortfolioDropDownResDto> dropdownPortfolio(HttpServletRequest httpRequest,@RequestParam(name = "dropdown") String s){
//        return ResponseEntity.ok(portfolioService.dropdownPortfolio(httpRequest,s));
//    }

    @Operation(summary = "내 포트폴리오 삭제")
    @DeleteMapping("/myPortfolio")
    public ResponseEntity<?> deletePortfolio(HttpServletRequest httpRequest) {
        portfolioService.deletePortfolio(httpRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "내가 받은 좋아요 조회")
    @GetMapping("/myPortfolioLikes")
    public ResponseEntity<PortfolioLikesResDto> getPortfolioLikes(HttpServletRequest httpRequest){
        return ResponseEntity.ok(portfolioService.getPortfolioLikes(httpRequest));
    }

    @Operation(summary = "내 광고 등록")
    @PostMapping("/myAdvertisement")
    public ResponseEntity<AdvertisementResDto> postAdvertisement(HttpServletRequest httpRequest, @RequestBody AdvertisementReqDto advertisementReqDto) {
        return ResponseEntity.ok(portfolioService.createAdvertisement(httpRequest, advertisementReqDto));
    }

    @Operation(summary = "내 광고 삭제")
    @DeleteMapping("/myAdvertisement")
    public ResponseEntity<?> deleteAdvertisement(HttpServletRequest httpRequest) {
        portfolioService.deleteAdvertisement(httpRequest);
        return ResponseEntity.ok().build();
    }
}
