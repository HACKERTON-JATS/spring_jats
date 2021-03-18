package com.example.jats.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication(); // SecurityContextHolder(토큰 값을 저장해 놓은 곳)에서 토큰을 가져온다.
    }

    public String getUserId() {
        return this.getAuthentication().getName();      // 유저 이메일 가져오기
    }

    public boolean isLogin() {
        return this.getAuthentication() != null;        // 로그인 여부
    }
}
