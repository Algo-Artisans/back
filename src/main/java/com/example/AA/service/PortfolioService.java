package com.example.AA.service;

import com.example.AA.dto.*;
import com.example.AA.entity.*;
import com.example.AA.entity.enumtype.HairName;
import com.example.AA.global.jwt.JwtTokenProvider;
import com.example.AA.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioSearchRepository portfolioSearchRepository;
    private final LikeRepository likeRepository;
    private final AdvertisementRepository advertisementRepository;
    private final PortfolioHairStyleRepository portfolioHairStyleRepository;
    private final WorkImageRepository workImageRepository;

    // 내 포트폴리오 조회
    public PortfolioResDto getPortfolio(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findPortfolioByUser(user);
        log.info("portfolio is :" + portfolio.getPortfolioId());

        // PortfolioResDto 객체 생성
        PortfolioResDto portfolioResDto = new PortfolioResDto(portfolio);

        return portfolioResDto;
    }



    // 디자이너 포트폴리오 전체 조회
    public List<PortfolioResDto> getPortfolios(HttpServletRequest httpRequest) { // accesstoken
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        List<PortfolioResDto> portfolioListResDto = new ArrayList<>();
        for (Portfolio portfolio : portfolioList) {
            PortfolioResDto portfolioResDto = PortfolioResDto.builder()
                    .portfolio(portfolio)
                    .build();
            portfolioListResDto.add(portfolioResDto);
        }

        return portfolioListResDto;
    }

    // 디자이너 포트폴리오 키워드 필터링 + 헤어스타일링에 어울리는 디자이너 추천
    public List<PortfolioResDto> searchPortfolio(HttpServletRequest httpRequest, String s) {
        List<PortfolioResDto> portfolioListResDto = new ArrayList<>(); // 변수명 수정

        HairName hairName = HairName.containsTitle(s);
        if (hairName != null) {
            portfolioListResDto = portfolioSearchRepository.searchHairname(hairName);
        }

        return portfolioListResDto;
    }



    // 디자이너 포트폴리오 드롭다운 조회
//    public PortfolioDropDownResDto dropdownPortfolio(HttpServletRequest httpRequest, String s) {
//        return PortfolioDropDownResDto;
//    }

    // 내 포트폴리오 삭제
    public void deletePortfolio(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findPortfolioByUser(user);

        List<PortfolioHairStyle> portfolioHairStyles = portfolioHairStyleRepository.findByPortfolio(portfolio);
        for (PortfolioHairStyle portfolioHairStyle : portfolioHairStyles) {
            portfolioHairStyleRepository.delete(portfolioHairStyle);
        }

        List<WorkImage> workImages = workImageRepository.findByPortfolio(portfolio);
        for (WorkImage workImage : workImages) {
            workImageRepository.delete(workImage);
        }
        portfolioRepository.delete(portfolio); // 포트폴리오 삭제
    }

    // 내가 받은 좋아요 조회
    public PortfolioLikesResDto getPortfolioLikes(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findPortfolioByUser(user);

        // 사용자가 받은 좋아요 조회
        List<Like> likesReceived = likeRepository.findLikesByPortfolio(portfolio);

        // 좋아요 누른 사용자 조회
        List<OAuthUserResDto> usersLiked = new ArrayList<>();
        for (Like like : likesReceived) {
            User likedUser = like.getUser();
            OAuthUserResDto userDto = OAuthUserResDto.builder()
                    .user(likedUser)
                    .build();
            usersLiked.add(userDto);
        }
        return new PortfolioLikesResDto(usersLiked.size(), usersLiked);
    }


    // 내 광고 등록
    public AdvertisementResDto createAdvertisement(HttpServletRequest httpRequest, AdvertisementReqDto advertisementReqDto) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest); // 현재 사용자 정보 가져오기
        Portfolio portfolio = portfolioRepository.findPortfolioByUser(user); // 사용자의 포트폴리오 가져오기

        // Advertisement 엔티티 생성 및 저장
        Advertisement advertisement = Advertisement.builder()
                .portfolio(portfolio)
                .applied(true) // 광고 신청 여부를 true로 설정
                .build();
        advertisementRepository.save(advertisement);

        // 광고 등록 결과 반환
        return AdvertisementResDto.builder()
                .advertisementId(advertisement.getAdvertisementId())
                .portfolio(portfolio)
                .applied(true)
                .build();
    }

    // 내 광고 삭제
    public void deleteAdvertisement(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest); // 현재 사용자 정보 가져오기
        Portfolio portfolio = portfolioRepository.findPortfolioByUser(user); // 사용자의 포트폴리오 가져오기
        Advertisement advertisement = advertisementRepository.findByPortfolio(portfolio); // 사용자의 포트폴리오에 해당하는 광고 가져오기

        if (advertisement != null) {
            advertisementRepository.delete(advertisement); // 광고 삭제
        }
    }


    // 디자이너 좋아요
    public LikeResDto likeHairstylist(HttpServletRequest httpRequest, LikeReqDto likeReqDto) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findPortfolioByPortfolioId(likeReqDto.getPortfolioId());

        Like newlike = Like.builder()
                .user(user)
                .portfolio(portfolio)
                .createdAt(LocalDateTime.now())
                .build();
        likeRepository.save(newlike);


        portfolio.setLikesCount(portfolio.getLikesCount() + 1);
        portfolioRepository.save(portfolio);

        // 좋아요 결과 반환
        return LikeResDto.builder()
                .portfolioId(portfolio.getPortfolioId())
                .build();
    }

    // 내가 누른 디자이너 좋아요 확인
    public List<LikeResDto> getLikeHairstylists(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);

        // 사용자가 좋아요한 포트폴리오 가져오기
        List<Like> likedPortfolios = likeRepository.findLikesByUser(user);

        // 좋아요한 포트폴리오를 LikeResDto로 매핑하여 반환
        return likedPortfolios.stream()
                .map(like -> LikeResDto.builder().portfolioId(like.getPortfolio().getPortfolioId()).build())
                .collect(Collectors.toList());
    }
}