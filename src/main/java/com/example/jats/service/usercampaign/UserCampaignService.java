package com.example.jats.service.usercampaign;

import com.example.jats.payload.response.CampaignBasicListResponse;
import org.springframework.data.domain.Pageable;


public interface UserCampaignService {
    CampaignBasicListResponse getUserCampaign(Pageable pageable);
}
