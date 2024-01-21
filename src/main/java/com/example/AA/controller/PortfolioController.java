package com.example.AA.controller;

import com.example.AA.dto.PortfolioReqDto;
import com.example.AA.dto.PortfolioResDto;
import com.example.AA.global.jwt.OAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PortfolioController {

    private final OAuthUserService oAuthUserService;
    @PostMapping("/portfolio")
    public PortfolioResDto postPortfolio(HttpServletRequest httpRequest, @RequestBody PortfolioReqDto portfolioReqDto) {
        return oAuthUserService.createPortfolio(httpRequest, portfolioReqDto);
    }


}
