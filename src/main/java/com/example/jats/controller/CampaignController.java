package com.example.jats.controller;

import com.example.jats.entity.user.enums.Region;
import com.example.jats.payload.request.CampaignRequest;
import com.example.jats.payload.response.CampaignListResponse;
import com.example.jats.service.campaign.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping
    public CampaignListResponse getCampaignList(Pageable pageable, @RequestParam @Nullable Region region) {
        return campaignService.getCampaignList(pageable, region);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCampaign(@RequestBody CampaignRequest request) {
        return campaignService.createCampaign(request);
    }

    @PatchMapping("/{campaignId}")
    public void participateCampaign(@PathVariable Long campaignId) {
        campaignService.participateCampaign(campaignId);
    }

    @DeleteMapping("/{campaignId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Long deleteCampaign(@PathVariable Long campaignId) {
        return campaignService.deleteCampaign(campaignId);
    }

    @PutMapping("/{campaignId}")
    public void updateCampaign(@PathVariable Long campaignId, @RequestBody CampaignRequest campaignRequest) {
        campaignService.updateCampaign(campaignId, campaignRequest);
    }
}
