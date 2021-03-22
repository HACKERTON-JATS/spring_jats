package com.example.jats.controller;

import com.example.jats.entity.user.enums.Region;
import com.example.jats.payload.response.CampaignListResponse;
import com.example.jats.service.campaign.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campaign")
public class CampaignListController {

    private final CampaignService campaignService;

    @GetMapping("/list")
    public CampaignListResponse getCampaignList(Pageable pageable,
                                                @PathVariable @Nullable Region region) {
        return campaignService.getCampaignList(pageable, region);
    }
}
