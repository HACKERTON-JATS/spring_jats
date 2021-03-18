package com.example.jats.service.campaign;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.payload.request.CampaignRequest;
import com.example.jats.payload.response.CampaignListResponse;
import org.springframework.data.domain.Pageable;

public interface CampaignService {
    Long createCampaign(CampaignRequest request);

    CampaignListResponse getCampaignList(Pageable pageable);

    void joinCampaign(Long campaignId);

    Long deleteCampaign(Long campaignId);
}
