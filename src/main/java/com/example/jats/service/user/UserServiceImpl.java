package com.example.jats.service.user;

import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.exceptions.UserNotFoundException;
import com.example.jats.payload.response.UserResponse;
import com.example.jats.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public UserResponse getUserRegion() {
        return userRepository.findById(authenticationFacade.getUserId())
                .map(region -> new UserResponse(region.getRegion(), region.getName(), region.getId()))
                .orElseThrow(UserNotFoundException::new);
    }
}
