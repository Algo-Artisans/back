package com.example.AA.global.jwt;

import com.example.AA.entity.User;
import com.example.AA.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//로그인 다 완료되고 우리 서버에서 쓸 수 있는 jwt 토큰을 발급
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    private OAuthUserService oAuthUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal(); // 카카오로부터 받은 유저 정보
        User user = userRepository.findUserByKakaoId(oAuth2User.getAttribute("id"))
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다.")); // 해당 id를 디비에서 조회
        String firstLogin = (user != null) ? "no" : "yes";
        String role = String.valueOf(user.getRole());

        log.info("OAuth2User: " + oAuth2User);
        String accessToken = jwtTokenProvider.createAccessToken(oAuth2User.getAttribute("id").toString(), role); //토큰발행
        String refreshToken = jwtTokenProvider.createRefreshToken(oAuth2User.getAttribute("id").toString(), role); //토큰발행
        log.info("accessToken: " + accessToken);
        log.info("refreshToken: " + refreshToken);

        // "http://localhost:3000/auth"
        // "https://morak-morak-demo.vercel.app/auth"
        String newtargetUrl;
        newtargetUrl = UriComponentsBuilder.fromUriString("https://morak-morak-demo.vercel.app/auth")
                .queryParam("accessToken", accessToken)
                .queryParam("firstLogin", firstLogin)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, newtargetUrl);
    }


}





