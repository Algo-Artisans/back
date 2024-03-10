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
    private final HairStyleRepository hairStyleRepository;

    // 내 포트폴리오 조회
    public PortfolioResDto getPortfolio(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findPortfolioByUser(user);
        List<PortfolioHairStyle> portfolioHairStyles = portfolioHairStyleRepository.findPortfolioHairStyleByPortfolio(portfolio);

        HairStyle hairStyle1 = hairStyleRepository.findHairStyleByHairStyleId(portfolioHairStyles.get(0).getHairStyle().getHairStyleId())
                .orElseThrow(() -> new RuntimeException(""));
        HairStyle hairStyle2 = hairStyleRepository.findHairStyleByHairStyleId(portfolioHairStyles.get(1).getHairStyle().getHairStyleId())
                .orElseThrow(() -> new RuntimeException(""));
        HairStyle hairStyle3 = hairStyleRepository.findHairStyleByHairStyleId(portfolioHairStyles.get(2).getHairStyle().getHairStyleId())
                .orElseThrow(() -> new RuntimeException(""));

        // hairstyle 가져오는 코드
        return PortfolioResDto.builder()
                .user(user)
                .gender(portfolio.getGender())
                .phoneNumber(portfolio.getPhoneNumber())
                .workplace(portfolio.getWorkplace())
                .snsAddress(portfolio.getSnsAddress())
                .introduction(portfolio.getIntroduction())
                .likesCount(portfolio.getLikesCount())
                .profileURL(portfolio.getProfileURL())
                .imageUrl1(portfolio.getImageUrl1())
                .imageUrl2(portfolio.getImageUrl2())
                .imageUrl3(portfolio.getImageUrl3())
                .imageUrl4(portfolio.getImageUrl4())
                .hairName1(hairStyle1.getHairName())
                .hairName2(hairStyle2.getHairName())
                .hairName3(hairStyle3.getHairName())
                .isAdvertise(portfolio.getIsAdvertise())
                .build();
    }



    // 디자이너 포트폴리오 전체 조회
    public List<PortfolioResDto> getPortfolios(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        List<PortfolioResDto> portfolioListResDto = new ArrayList<>();
        for (Portfolio portfolio : portfolioList) {
            List<PortfolioHairStyle> portfolioHairStyles = portfolioHairStyleRepository.findPortfolioHairStyleByPortfolio(portfolio);

            HairStyle hairStyle1 = hairStyleRepository.findHairStyleByHairStyleId(portfolioHairStyles.get(0).getHairStyle().getHairStyleId())
                    .orElseThrow(() -> new RuntimeException(""));
            HairStyle hairStyle2 = hairStyleRepository.findHairStyleByHairStyleId(portfolioHairStyles.get(1).getHairStyle().getHairStyleId())
                    .orElseThrow(() -> new RuntimeException(""));
            HairStyle hairStyle3 = hairStyleRepository.findHairStyleByHairStyleId(portfolioHairStyles.get(2).getHairStyle().getHairStyleId())
                    .orElseThrow(() -> new RuntimeException(""));
            PortfolioResDto portfolioResDto = PortfolioResDto.builder()
                    .user(user)
                    .gender(portfolio.getGender())
                    .phoneNumber(portfolio.getPhoneNumber())
                    .workplace(portfolio.getWorkplace())
                    .snsAddress(portfolio.getSnsAddress())
                    .introduction(portfolio.getIntroduction())
                    .likesCount(portfolio.getLikesCount())
                    .profileURL(portfolio.getProfileURL())
                    .imageUrl1(portfolio.getImageUrl1())
                    .imageUrl2(portfolio.getImageUrl2())
                    .imageUrl3(portfolio.getImageUrl3())
                    .imageUrl4(portfolio.getImageUrl4())
                    .hairName1(hairStyle1.getHairName())
                    .hairName2(hairStyle2.getHairName())
                    .hairName3(hairStyle3.getHairName())
                    .isAdvertise(portfolio.getIsAdvertise())
                    .build();
            portfolioListResDto.add(portfolioResDto);
        }

        return portfolioListResDto;
    }


    // 디자이너 포트폴리오 키워드 필터링 + 헤어스타일링에 어울리는 디자이너 추천
    public List<PortfolioResDto> searchPortfolio(HttpServletRequest httpRequest, String s) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        List<PortfolioResDto> portfolioListResDto = new ArrayList<>();
        List<Portfolio> portfolioList = new ArrayList<>();
        if (HairName.containsKeyword(s)) {
            log.info("searchPortfolio containsTitle");
            portfolioList = portfolioSearchRepository.searchHairname(s);

            // Portfolio를 PortfolioResDto로 변환하여 리스트에 추가
            for (Portfolio portfolio : portfolioList) {
                portfolioListResDto.add(new PortfolioResDto(
                        portfolio.getUser(),
                        portfolio.getPortfolioId(),
                        portfolio.getGender(),
                        portfolio.getPhoneNumber(),
                        portfolio.getWorkplace(),
                        portfolio.getSnsAddress(),
                        portfolio.getIntroduction(),
                        portfolio.getLikesCount(),
                        portfolio.getProfileURL(),
                        portfolio.getImageUrl1(),
                        portfolio.getImageUrl2(),
                        portfolio.getImageUrl3(),
                        portfolio.getImageUrl4(),
                        portfolio.getPortfolioHairStyles().get(0).getHairStyle().getHairName(),
                        portfolio.getPortfolioHairStyles().get(1).getHairStyle().getHairName(),
                        portfolio.getPortfolioHairStyles().get(2).getHairStyle().getHairName(),
                        portfolio.getIsAdvertise()
                ));
            }
        }

        return portfolioListResDto;
    }




    // 디자이너 포트폴리오 드롭다운 조회
    public List<PortfolioResDto> dropdownPortfolio(HttpServletRequest httpRequest, String s) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        List<PortfolioResDto> portfolioListResDto = new ArrayList<>();
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        log.info(portfolioList.toString());

        // 최신순
        if (s.equals("최신순")) {
            log.info("최신순");
            List<Portfolio> timeSortedPortfolioList = new ArrayList<>(portfolioList);
            timeSortedPortfolioList.sort(Comparator.comparing(Portfolio::getCreatedAt).reversed());
            log.info(timeSortedPortfolioList.toString());

            for (Portfolio portfolio : timeSortedPortfolioList) {
                portfolioListResDto.add(new PortfolioResDto(
                        portfolio.getUser(),
                        portfolio.getPortfolioId(),
                        portfolio.getGender(),
                        portfolio.getPhoneNumber(),
                        portfolio.getWorkplace(),
                        portfolio.getSnsAddress(),
                        portfolio.getIntroduction(),
                        portfolio.getLikesCount(),
                        portfolio.getProfileURL(),
                        portfolio.getImageUrl1(),
                        portfolio.getImageUrl2(),
                        portfolio.getImageUrl3(),
                        portfolio.getImageUrl4(),
                        portfolio.getPortfolioHairStyles().get(0).getHairStyle().getHairName(),
                        portfolio.getPortfolioHairStyles().get(1).getHairStyle().getHairName(),
                        portfolio.getPortfolioHairStyles().get(2).getHairStyle().getHairName(),
                        portfolio.getIsAdvertise()
                ));
            }
        }
        // 광고순
        if (s.equals("광고순")) {
            log.info("광고순");
            List<Portfolio> advertisementSortedPortfolioList = portfolioRepository.findByIsAdvertise(1);
            log.info(advertisementSortedPortfolioList.toString());
            for (Portfolio portfolio : advertisementSortedPortfolioList) {
                portfolioListResDto.add(new PortfolioResDto(
                        portfolio.getUser(),
                        portfolio.getPortfolioId(),
                        portfolio.getGender(),
                        portfolio.getPhoneNumber(),
                        portfolio.getWorkplace(),
                        portfolio.getSnsAddress(),
                        portfolio.getIntroduction(),
                        portfolio.getLikesCount(),
                        portfolio.getProfileURL(),
                        portfolio.getImageUrl1(),
                        portfolio.getImageUrl2(),
                        portfolio.getImageUrl3(),
                        portfolio.getImageUrl4(),
                        portfolio.getPortfolioHairStyles().get(0).getHairStyle().getHairName(),
                        portfolio.getPortfolioHairStyles().get(1).getHairStyle().getHairName(),
                        portfolio.getPortfolioHairStyles().get(2).getHairStyle().getHairName(),
                        portfolio.getIsAdvertise()
                ));
            }
        }
        // 좋아요순
        if (s.equals("좋아요순")) {
            log.info("좋아요순");
            List<Portfolio> likeSortedPortfolioList = portfolioList.stream()
                    .sorted(Comparator.comparingInt(Portfolio::getLikesCount).reversed())
                    .collect(Collectors.toList());
            log.info(likeSortedPortfolioList.toString());
            for (Portfolio portfolio : likeSortedPortfolioList) {
                portfolioListResDto.add(new PortfolioResDto(
                        portfolio.getUser(),
                        portfolio.getPortfolioId(),
                        portfolio.getGender(),
                        portfolio.getPhoneNumber(),
                        portfolio.getWorkplace(),
                        portfolio.getSnsAddress(),
                        portfolio.getIntroduction(),
                        portfolio.getLikesCount(),
                        portfolio.getProfileURL(),
                        portfolio.getImageUrl1(),
                        portfolio.getImageUrl2(),
                        portfolio.getImageUrl3(),
                        portfolio.getImageUrl4(),
                        portfolio.getPortfolioHairStyles().get(0).getHairStyle().getHairName(),
                        portfolio.getPortfolioHairStyles().get(1).getHairStyle().getHairName(),
                        portfolio.getPortfolioHairStyles().get(2).getHairStyle().getHairName(),
                        portfolio.getIsAdvertise()
                ));
            }
        }
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