package com.example.AA.global.jwt;

import com.example.AA.dto.*;
import com.example.AA.entity.*;
import com.example.AA.entity.enumtype.FaceShape;
import com.example.AA.entity.enumtype.HairName;
import com.example.AA.entity.enumtype.Role;
import com.example.AA.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final JwtTokenProvider jwtTokenProvider;
    private final PortfolioRepository portfolioRepository;
    private final HairStyleRepository hairStyleRepository;
    private final PortfolioHairStyleRepository portfolioHairStyleRepository;
    //회원가입 처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser method !");
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); //객체생성
        OAuth2User oAuth2User = delegate.loadUser(userRequest);// Oath2 정보를 가져옴
        OAuthAttributesResDto attributes = OAuthAttributesResDto.ofKakao(oAuth2User.getAttributes()); //회원정보 JSON 정제해서 반환

        log.info("attributes: " + attributes);
        log.info("attributes.getKakaoId: " + attributes.getKakaoId());
        User user = saveOrUpdate(attributes);
        user.setRole(String.valueOf(Role.USER));
        httpSession.setAttribute("user", new SessionUser(user));


        Collection<? extends GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey()));

        return new DefaultOAuth2User(
                authorities,
                attributes.getAttributes(),
                "id"
        );
    }

    public void selectRole(HttpServletRequest httpRequest, String role) {
        try {
            // 여기에 사용자의 역할을 업데이트하는 로직을 추가
            User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
            System.out.println("Picked User: " + user);
            user.setRole(role);
            userRepository.save(user);

            Role updatedRole = user.getRole();
            System.out.println("Updated User Role: " + updatedRole);

        } catch (Exception e) {
            e.printStackTrace();
            // 업데이트 실패 시 예외를 다시 던짐
            throw new RuntimeException("Failed to update role: " + e.getMessage());
        }
    }


    //인증된 유저 DTO 반환
    private User saveOrUpdate(OAuthAttributesResDto attributes) {
        log.info("saveOrUpdate method !");
        Optional<User> User = userRepository.findUserByKakaoId(attributes.getKakaoId());
        if (User.isPresent()) {
            User user = User.get();
            log.info("user: " + user);
            log.info("user is");
            return user;
        } else {
            log.info("user isn't");
            User newUser = userRepository.save(attributes.toEntity());
            return newUser;
        }
    }


    public OAuthUserResDto getUser(HttpServletRequest httpRequest) {
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        return user != null ? OAuthUserResDto.builder().user(user).build() : null;
    }

    public void updateNicknameGender(HttpServletRequest httpRequest, NicknameGenderReqDto nicknameGenderReqDto){
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
        log.info("updateNicknameGender:" + user.getNickname());
        user.updateNickname(nicknameGenderReqDto.getNickname());
        user.updateGender(nicknameGenderReqDto.getGender());
        userRepository.save(user);
    }

    public void updateFaceShape(HttpServletRequest httpRequest, FaceShapeReqDto faceShapeReqDto) {
        try {
            // FaceShape 열거형에 대응되는 값을 찾아 업데이트
            for (FaceShape value : FaceShape.values()) {
                if (value.getValue().equalsIgnoreCase(faceShapeReqDto.getFaceShapeBest())) {
                    User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
                    user.updateFaceShapeBest(String.valueOf(value));
                    userRepository.save(user);

                }
            }
            for (FaceShape value : FaceShape.values()) {
                if (value.getValue().equalsIgnoreCase(faceShapeReqDto.getFaceShapeWorst())) {
                    User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
                    user.updateFaceShapeWorst(String.valueOf(value));
                    userRepository.save(user);
                    return;  // 업데이트 후 종료
                }
            }
            // FaceShape에 대응되는 값이 없으면 IllegalArgumentException 발생
            throw new IllegalArgumentException("유효하지 않은 얼굴형 값입니다.");
        } catch (Exception e) {
            throw new RuntimeException("얼굴형 처리 중 에러가 발생했습니다.");
        }
    }

    public PortfolioResDto createPortfolio(HttpServletRequest httpRequest, PortfolioReqDto portfolioReqDto) {
        // 클라이언트 정보를 토큰에서 추출
        User user = jwtTokenProvider.getUserInfoByToken(httpRequest);

        // 디자이너인 경우에만 포트폴리오 생성
        if (user.isDesigner()) {
            // 포트폴리오 생성
            Portfolio portfolio = Portfolio.builder()
                    .user(user)
                    .name(portfolioReqDto.getName())
                    .phoneNumber(portfolioReqDto.getPhoneNumber())
                    .workplace(portfolioReqDto.getWorkplace())
                    .snsAddress(portfolioReqDto.getSnsAddress())
                    .introduction(portfolioReqDto.getIntroduction())
                    .likesCount(0)
                    .styling1(portfolioReqDto.getStyling1())
                    .cost1(portfolioReqDto.getCost1())
                    .styling2(portfolioReqDto.getStyling2())
                    .cost2(portfolioReqDto.getCost2())
                    .styling3(portfolioReqDto.getStyling3())
                    .cost3(portfolioReqDto.getCost3())
                    .styling4(portfolioReqDto.getStyling4())
                    .cost4(portfolioReqDto.getCost4())
                    .profileURL(portfolioReqDto.getProfileURL())
                    .certificateURL(portfolioReqDto.getCertificateURL())
                    .imageUrl1(portfolioReqDto.getImageUrl1())
                    .imageUrl2(portfolioReqDto.getImageUrl2())
                    .imageUrl3(portfolioReqDto.getImageUrl3())
                    .imageUrl4(portfolioReqDto.getImageUrl4())
                    .isAdvertise(0)
                    .build();



            // Save PortfolioHairStyles
            List<PortfolioHairStyle> portfolioHairStyles = new ArrayList<>();

            // HairName1에 해당하는 HairStyle 찾기
            HairStyle hairStyle1 = hairStyleRepository.findByHairName(portfolioReqDto.getHairName1());
            if (hairStyle1 != null) {
                PortfolioHairStyle portfolioHairStyle1 = PortfolioHairStyle.builder()
                        .portfolio(portfolio)
                        .hairStyle(hairStyle1)
                        .build();
                portfolioHairStyles.add(portfolioHairStyle1);
            }


            HairStyle hairStyle2 = hairStyleRepository.findByHairName(portfolioReqDto.getHairName2());;
            if (hairStyle2 != null) {
                PortfolioHairStyle portfolioHairStyle2 = PortfolioHairStyle.builder()
                        .portfolio(portfolio)
                        .hairStyle(hairStyle2)
                        .build();
                portfolioHairStyles.add(portfolioHairStyle2);
            }


            // HairName3에 해당하는 HairStyle 찾기
            HairStyle hairStyle3 = hairStyleRepository.findByHairName(portfolioReqDto.getHairName3());;
            if (hairStyle3 != null) {
                PortfolioHairStyle portfolioHairStyle3 = PortfolioHairStyle.builder()
                        .portfolio(portfolio)
                        .hairStyle(hairStyle3)
                        .build();
                portfolioHairStyles.add(portfolioHairStyle3);
            }


            // PortfolioHairStyles 저장
            portfolioHairStyles.forEach(portfolioHairStyleRepository::save);


            // Portfolio 저장
            portfolioRepository.save(portfolio);


            // 저장된 포트폴리오 정보를 조회하여 PortfolioResDto 객체 생성
            Portfolio savedPortfolio = portfolioRepository.findPortfolioByUser(user);
            log.info(String.valueOf(savedPortfolio));

            PortfolioResDto portfolioResDto = PortfolioResDto.builder()
                    .user(user)
                    .portfolioId(portfolio.getPortfolioId())
                    .name(portfolioReqDto.getName())
                    .phoneNumber(portfolioReqDto.getPhoneNumber())
                    .workplace(portfolioReqDto.getWorkplace())
                    .snsAddress(portfolioReqDto.getSnsAddress())
                    .introduction(portfolioReqDto.getIntroduction())
                    .likesCount(portfolio.getLikesCount())
                    .profileURL(portfolio.getProfileURL())
                    .imageUrl1(portfolio.getImageUrl1())
                    .imageUrl2(portfolio.getImageUrl2())
                    .imageUrl3(portfolio.getImageUrl3())
                    .imageUrl4(portfolio.getImageUrl4())
                    .cost1(portfolioReqDto.getCost1())
                    .styling2(portfolioReqDto.getStyling2())
                    .cost2(portfolioReqDto.getCost2())
                    .styling3(portfolioReqDto.getStyling3())
                    .cost3(portfolioReqDto.getCost3())
                    .styling4(portfolioReqDto.getStyling4())
                    .cost4(portfolioReqDto.getCost4())
                    .hairName1(portfolioReqDto.getHairName1())
                    .hairName2(portfolioReqDto.getHairName2())
                    .hairName3(portfolioReqDto.getHairName3())
                    .isAdvertise(portfolio.getIsAdvertise())
                    .build();

            return portfolioResDto;

        } else {
            throw new RuntimeException("디자이너가 아닌 사용자는 포트폴리오를 생성할 수 없습니다.");
        }
    }

}