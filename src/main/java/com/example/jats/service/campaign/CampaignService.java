package com.example.jats.service.campaign;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.user.enums.Region;
import com.example.jats.payload.request.CampaignRequest;
import com.example.jats.payload.response.CampaignContentResponse;
import com.example.jats.payload.response.CampaignListResponse;
import org.springframework.data.domain.Pageable;

public interface CampaignService {
    Long createCampaign(CampaignRequest request);

    CampaignListResponse getCampaignList(Pageable pageable, Region region);

    void participateCampaign(Long campaignId);

    Long deleteCampaign(Long campaignId);

    void updateCampaign(Long campaignId, CampaignRequest request);

    CampaignContentResponse getCampaign(Long campaignId);
}
