package com.example.jats.controller;

import com.example.jats.payload.request.SignInRequest;
import com.example.jats.payload.request.SignUpRequest;
import com.example.jats.payload.response.TokenResponse;
import com.example.jats.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse signIn(@RequestBody SignInRequest request) {
        return authService.signIn(request);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody SignUpRequest request) {
        authService.signUp(request);
    }
}
