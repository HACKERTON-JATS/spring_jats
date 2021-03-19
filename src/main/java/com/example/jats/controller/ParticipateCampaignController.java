package com.example.jats.controller;

import com.example.jats.payload.response.CampaignBasicListResponse;
import com.example.jats.service.usercampaign.UserCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campaign")
public class ParticipateCampaignController {

    private final UserCampaignService userCampaignService;

    @GetMapping("/list")
    public CampaignBasicListResponse getParticipateCampaign(Pageable pageable) {
        return userCampaignService.getUserCampaign(pageable);
    }
}
