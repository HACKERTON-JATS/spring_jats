package com.example.jats.service.user;

import com.example.jats.payload.response.CampaignListResponse;
import org.springframework.data.domain.Pageable;


public interface UserCampaignService {
    CampaignListResponse getUserCampaign(Pageable pageable);
}
