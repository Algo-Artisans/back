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

    public PortfolioSearchResDto searchPortfolio(HttpServletRequest httpRequest, String s){
        return PortfolioSearchResDto;
    }

    public PortfolioDropDownResDto dropdownPortfolio(HttpServletRequest httpRequest, String s){
        return PortfolioDropDownResDto;
    }

    public void deletePortfolio(HttpServletRequest httpRequest){
        return;
    }

    public PortfolioLikesResDto getPortfolioLikes(HttpServletRequest httpRequest){
        return PortfolioLikesResDto;
    }

    public AdvertisementResDto postAdvertisement(HttpServletRequest httpRequest, AdvertisementReqDto advertisementReqDto){
        return AdvertisementResDto;
    }

    public void deleteAdvertisement(HttpServletRequest httpRequest){
        return;
    }

    public PortfolioSearchResDto recommendHairStylists(HttpServletRequest httpRequest, String s){
        return PortfolioSearchResDto;
    }

    public LikeResDto likeHairstylist(HttpServletRequest httpRequest, LikeReqDto likeReqDto) {
        return LikeResDto;
    }

    public List<LikeResDto> getLikeHairstylists(HttpServletRequest httpRequest) {
        List<LikeResDto> LikeListResDto;
        return LikeListResDto;
    }
