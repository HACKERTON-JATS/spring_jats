package com.example.jats.service.auth;

import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.exceptions.InvalidAccessException;
import com.example.jats.exceptions.UserAlreadyExistException;
import com.example.jats.exceptions.UserNotFoundException;
import com.example.jats.payload.request.SignInRequest;
import com.example.jats.payload.request.SignUpRequest;
import com.example.jats.payload.response.TokenResponse;
import com.example.jats.security.JwtTokenProvider;
import com.example.jats.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public TokenResponse signIn(SignInRequest request) {
        return userRepository.findById(request.getId())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(User::getId)
                .map(id -> new TokenResponse(jwtTokenProvider.generateAccessToken(id)))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void signUp(SignUpRequest request) {
        userRepository.findById(request.getId())
                .ifPresent(user -> {throw new UserNotFoundException();});
        userRepository.save(
                User.builder()
                        .id(request.getId())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .name(request.getName())
                        .region(request.getRegion())
                        .build());
    }
}
