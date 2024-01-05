package com.example.AA.global.jwt;

import com.example.AA.dto.FaceShapeReqDto;
import com.example.AA.dto.OAuthUserResDto;
import com.example.AA.entity.FaceShape;
import com.example.AA.entity.Role;
import com.example.AA.entity.User;
import com.example.AA.dto.OAuthAttributesResDto;
import com.example.AA.dto.SessionUser;
import com.example.AA.repository.UserRepository;
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
import org.springframework.web.bind.annotation.RequestBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final JwtTokenProvider jwtTokenProvider;

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
}