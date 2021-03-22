package com.example.jats.service.mypage;

import com.example.jats.payload.request.UpdateUserRequest;
import com.example.jats.payload.response.CampaignMyPageListResponse;
import org.springframework.data.domain.Pageable;


public interface MyPageService {
    CampaignMyPageListResponse getUserCampaign(Pageable pageable);

    void updateUser(UpdateUserRequest request);
}
