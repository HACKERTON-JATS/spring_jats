package com.example.jats.controller;

import com.example.jats.payload.response.CampaignListResponse;
import com.example.jats.service.campaign.CampaignService;
import com.example.jats.service.user.UserCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campaign")
public class JoinCampaignController {

    private final UserCampaignService userCampaignService;

    @GetMapping("/list")
    public CampaignListResponse getJoinCampaign(Pageable pageable) {
        return userCampaignService.getUserCampaign(pageable);
    }
}
