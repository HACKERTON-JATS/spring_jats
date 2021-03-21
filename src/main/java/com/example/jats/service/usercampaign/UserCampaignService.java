package com.example.jats.service.usercampaign;

import com.example.jats.payload.response.CampaignMyPageListResponse;
import org.springframework.data.domain.Pageable;


public interface UserCampaignService {
    CampaignMyPageListResponse getUserCampaign(Pageable pageable);
}
