package com.example.jats.controller;

import com.example.jats.payload.request.UpdateUserRequest;
import com.example.jats.payload.response.CampaignMyPageListResponse;
import com.example.jats.service.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/campaign")
    public CampaignMyPageListResponse getParticipateCampaign(Pageable pageable) {
        return myPageService.getUserCampaign(pageable);
    }

    @PutMapping
    public void updateUser(@RequestBody UpdateUserRequest request) {
        myPageService.updateUser(request);
    }
}
