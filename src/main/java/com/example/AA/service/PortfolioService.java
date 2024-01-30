package com.example.AA.service;

import com.example.AA.dto.PortfolioResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    // 내 포트폴리오 조회
    public PortfolioResDto getPortfolio(HttpServletRequest httpRequest){
        return PortfolioResDto;
    }

    // 디자이너 포트폴리오 전체 조회
    public List<PortfolioResDto> getPortfolios(){
        List<PortfolioResDto> portfolioListResDto;
        return portfolioListResDto;
    }

    // 디자이너 포트폴리오 키워드 필터링
    public PortfolioSearchResDto searchPortfolio(HttpServletRequest httpRequest, String s){
        return PortfolioSearchResDto;
    }

    // 디자이너 포트폴리오 드롭다운 조회
    public PortfolioDropDownResDto dropdownPortfolio(HttpServletRequest httpRequest, String s){
        return PortfolioDropDownResDto;
    }

    // 내 포트폴리오 삭제
    public void deletePortfolio(HttpServletRequest httpRequest){
        return;
    }

    // 내가 받은 좋아요 조회
    public PortfolioLikesResDto getPortfolioLikes(HttpServletRequest httpRequest){
        return PortfolioLikesResDto;
    }

    // 내 광고 등록
    public AdvertisementResDto postAdvertisement(HttpServletRequest httpRequest, AdvertisementReqDto advertisementReqDto){
        return AdvertisementResDto;
    }

    // 내 광고 삭제
    public void deleteAdvertisement(HttpServletRequest httpRequest){
        return;
    }

    // 헤어스타일링에 어울리는 디자이너 추천
    public PortfolioSearchResDto recommendHairStylists(HttpServletRequest httpRequest, String s){
        return PortfolioSearchResDto;
    }

    // 디자이너 좋아요
    public LikeResDto likeHairstylist(HttpServletRequest httpRequest, LikeReqDto likeReqDto) {
        return LikeResDto;
    }

    // 내가 누른 디자이너 좋아요 확인
    public List<LikeResDto> getLikeHairstylists(HttpServletRequest httpRequest) {
        List<LikeResDto> LikeListResDto;
        return LikeListResDto;
    }
