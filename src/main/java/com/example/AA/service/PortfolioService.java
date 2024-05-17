package com.example.AA.service;

import com.example.AA.dto.*;
import com.example.AA.entity.*;
import com.example.AA.entity.enumtype.HairName;
import com.example.AA.global.jwt.JwtTokenProvider;
import com.example.AA.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
    private final PortfolioHairStyleRepository portfolioHairStyleRepository;

    // 중복 제거를 위해 포트폴리오 DTO 생성 메서드 추출
    private PortfolioResDto createPortfolioResDto(Portfolio portfolio) {
        List<PortfolioHairStyle> portfolioHairStyles = portfolioHairStyleRepository.findPortfolioHairStyleByPortfolio(portfolio);
        List<HairStyle> hairStyles = portfolioHairStyles.stream().map(PortfolioHairStyle::getHairStyle).collect(Collectors.toList());

        return PortfolioResDto.builder()
                .user(portfolio.getUser())
                .portfolioId(portfolio.getPortfolioId())
                .name(portfolio.getName())
                .phoneNumber(portfolio.getPhoneNumber())
                .workplace(portfolio.getWorkplace())
                .snsAddress(portfolio.getSnsAddress())
                .introduction(portfolio.getIntroduction())
                .likesCount(portfolio.getLikesCount())
                .profileURL(portfolio.getProfileURL())
                .certificateURL(portfolio.getCertificateURL())
                .imageUrl1(portfolio.getImageUrl1())
                .imageUrl2(portfolio.getImageUrl2())
                .imageUrl3(portfolio.getImageUrl3())
                .imageUrl4(portfolio.getImageUrl4())
                .styling1(portfolio.getStyling1())
                .cost1(portfolio.getCost1())
                .styling2(portfolio.getStyling2())
                .cost2(portfolio.getCost2())
                .styling3(portfolio.getStyling3())
                .cost3(portfolio.getCost3())
                .styling4(portfolio.getStyling4())
                .cost4(portfolio.getCost4())
                .hairName1(hairStyles.get(0).getHairName())
                .hairName2(hairStyles.get(1).getHairName())
                .hairName3(hairStyles.get(2).getHairName())
                .isAdvertise(portfolio.getIsAdvertise())
                .build();
    }

    // 포트폴리오 세부 조회
    public PortfolioResDto getPortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioSearchRepository.findByPortfolioId(portfolioId);
        return createPortfolioResDto(portfolio);
    }

    // 디자이너 포트폴리오 전체 조회
    public List<PortfolioResDto> getPortfolios() {
        List<Portfolio> portfolioList = portfolioSearchRepository.findAll();
        return portfolioList.stream().map(this::createPortfolioResDto).collect(Collectors.toList());
    }


    // 디자이너 포트폴리오 키워드 필터링 + 헤어스타일링에 어울리는 디자이너 추천
    public List<PortfolioResDto> searchPortfolio(String input) {
        List<PortfolioResDto> portfolioListResDto = new ArrayList<>();

        // 입력 문자열을 쉼표를 기준으로 나누어 각각의 헤어스타일 이름으로 처리
        List<String> hairNames = Arrays.asList(input.split(","));

        // 헤어스타일 이름으로 포트폴리오 검색
        List<Portfolio> portfolioList = portfolioSearchRepository.searchHairNames(hairNames);

        for (Portfolio portfolio : portfolioList) {
            portfolioListResDto.add(createPortfolioResDto(portfolio));
        }

        return portfolioListResDto;
    }



    // 디자이너 포트폴리오 드롭다운 조회
    public List<PortfolioResDto> searchAndDropdownPortfolio(HttpServletRequest httpRequest, String input, String s) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        List<PortfolioResDto> portfolioListResDto = new ArrayList<>();
        List<Portfolio> portfolioList = new ArrayList<>();

        // 입력 문자열을 쉼표를 기준으로 나누어 각각의 헤어스타일 이름으로 처리
        List<String> hairNames = Arrays.asList(input.split(","));

        // 헤어스타일 이름으로 포트폴리오 검색
        List<Portfolio> filteredPortfolioList = portfolioSearchRepository.searchHairNames(hairNames);

        // 정렬 기준에 따른 포트폴리오 리스트 생성하기
        if (s == null || s.isEmpty() || s.equals("최신순")) {
            log.info("최신순");
            filteredPortfolioList.sort(Comparator.comparing(Portfolio::getCreatedAt).reversed());
        } else if (s.equals("추천순")) {
            log.info("추천순");
            filteredPortfolioList = filteredPortfolioList.stream()
                    .filter(portfolio -> portfolio.getIsAdvertise() != null && portfolio.getIsAdvertise() == 1)
                    .sorted(Comparator.comparing(Portfolio::getCreatedAt).reversed())
                    .collect(Collectors.toList());
        } else if (s.equals("좋아요순")) {
            log.info("좋아요순");
            filteredPortfolioList.sort(Comparator.comparing(Portfolio::getLikesCount).reversed());
        } else {
            // 기본 정렬 기준 (예: 최신순)
            log.info("기본 정렬 기준: 최신순");
            filteredPortfolioList.sort(Comparator.comparing(Portfolio::getCreatedAt).reversed());
        }

        // 포트폴리오 리스트를 DTO로 변환
        portfolioListResDto = filteredPortfolioList.stream()
                .map(this::createPortfolioResDto)
                .collect(Collectors.toList());

        return portfolioListResDto;
    }




    // 내 포트폴리오 삭제
    public void deletePortfolio(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest); // 토큰을 기반으로 사용자 정보 가져오기
        Portfolio portfolio = portfolioRepository.findPortfolioByUser(user); // 사용자에게 연관된 포트폴리오 가져오기

        List<PortfolioHairStyle> portfolioHairStyles = portfolioHairStyleRepository.findByPortfolio(portfolio);
        for (PortfolioHairStyle portfolioHairStyle : portfolioHairStyles) {
            portfolioHairStyleRepository.delete(portfolioHairStyle);
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
    public Integer createAdvertisement(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findPortfolioByUser(user);

        portfolio.setIsAdvertise(1);
        portfolioRepository.save(portfolio);
        return 1;
    }

    // 내 광고 삭제
    public Integer deleteAdvertisement(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findPortfolioByUser(user);

        portfolio.setIsAdvertise(0);
        portfolioRepository.save(portfolio);

        return 0;
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
    public List<PortfolioResDto> getLikeHairstylists(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);

        // 사용자가 좋아요한 포트폴리오 가져오기
        List<Like> likedPortfolios = likeRepository.findLikesByUser(user);

        // 좋아요한 포트폴리오의 ID 목록 가져오기
        List<Long> likedPortfolioIds = likedPortfolios.stream()
                .map(like -> like.getPortfolio().getPortfolioId())
                .collect(Collectors.toList());

        // 좋아요한 포트폴리오들의 정보를 QueryDSL을 사용하여 가져오기
        List<Portfolio> portfolios = portfolioSearchRepository.findAllByLike(likedPortfolioIds);

        // 가져온 포트폴리오 정보를 PortfolioResDto로 매핑하여 반환
        return portfolios.stream()
                .map(this::createPortfolioResDto)
                .collect(Collectors.toList());
    }


}