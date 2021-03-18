package com.example.jats.service.auth;

import com.example.jats.payload.request.SignInRequest;
import com.example.jats.payload.request.SignUpRequest;
import com.example.jats.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(SignInRequest request);

    void signUp(SignUpRequest request);
}
