package com.example.jats.controller;

import com.example.jats.payload.response.UserResponse;
import com.example.jats.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserResponse getUserRegion() {
        return userService.getUserRegion();
    }

}
