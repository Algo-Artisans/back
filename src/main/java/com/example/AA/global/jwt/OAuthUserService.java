package com.example.AA.global.jwt;

import com.example.AA.dto.*;
import com.example.AA.entity.*;
import com.example.AA.entity.enumtype.FaceShape;
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
    private final WorkImageRepository workImageRepository;
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
            System.out.println("Updated User: " + user);
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

    public void updateFaceShape(HttpServletRequest httpRequest, FaceShapeReqDto faceShapeReqDto) {
        try {
            // FaceShape 열거형에 대응되는 값을 찾아 업데이트
            for (FaceShape value : FaceShape.values()) {
                if (value.getValue().equalsIgnoreCase(faceShapeReqDto.getFaceShapeBest())) {
                    User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
                    user.updateFaceShapeBest(value);
                    userRepository.save(user);
                    return;  // 업데이트 후 종료
                }
            }
            for (FaceShape value : FaceShape.values()) {
                if (value.getValue().equalsIgnoreCase(faceShapeReqDto.getFaceShapeWorst())) {
                    User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
                    user.updateFaceShapeWorst(value);
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
                    .gender(portfolioReqDto.getGender())
                    .phoneNumber(portfolioReqDto.getPhoneNumber())
                    .workplace(portfolioReqDto.getWorkplace())
                    .snsAddress(portfolioReqDto.getSnsAddress())
                    .introduction(portfolioReqDto.getIntroduction())
                    .likesCount(portfolioReqDto.getLikesCount())
                    .profileURL(portfolioReqDto.getProfileURL())
                    .build();

            WorkImage workImage = WorkImage.builder()
                    .portfolio(portfolio)
                    .imageUrl1(portfolioReqDto.getImageUrl1())
                    .imageUrl2(portfolioReqDto.getImageUrl2())
                    .imageUrl3(portfolioReqDto.getImageUrl3())
                    .imageUrl4(portfolioReqDto.getImageUrl4())
                    .build();

            // Save HairStyles
            List<HairStyle> hairStyles = new ArrayList<>();
            for (String hairName : Arrays.asList(portfolioReqDto.getHairName1(), portfolioReqDto.getHairName2(), portfolioReqDto.getHairName3())) {

                HairStyle hairStyle = HairStyle.builder()
                        //.faceShape()
                        .hairName(hairName)
                        .build();
                hairStyles.add(hairStyle);
                hairStyleRepository.save(hairStyle);
            }

            // Save PortfolioHairStyles
            for (HairStyle hairStyle : hairStyles) {
                PortfolioHairStyle portfolioHairStyle = new PortfolioHairStyle(portfolio, hairStyle);
                portfolioHairStyleRepository.save(portfolioHairStyle);
            }


            // 포트폴리오 저장
            portfolioRepository.save(portfolio);
            workImageRepository.save(workImage);


            // 저장된 포트폴리오 정보를 조회하여 PortfolioResDto 객체 생성
            Portfolio savedPortfolio = portfolioRepository.findPortfolioByUser(user)
                    .orElseThrow(() -> new RuntimeException("포트폴리오를 찾을 수 없습니다."));

            WorkImage savedWorkImage = workImageRepository.findWorkImageByPortfolio(portfolio)
                    .orElseThrow(() -> new RuntimeException("작업물을 찾을 수 없습니다."));

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
                    .portfolio(savedPortfolio)
                    .workImage(savedWorkImage)
                    .hairStyle1(hairStyle1)
                    .hairStyle2(hairStyle2)
                    .hairStyle3(hairStyle3)
                    .build();
        } else {
            throw new RuntimeException("디자이너가 아닌 사용자는 포트폴리오를 생성할 수 없습니다.");
        }
    }

}