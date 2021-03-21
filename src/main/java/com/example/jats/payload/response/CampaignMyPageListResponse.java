package com.example.jats.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CampaignMyPageListResponse extends PageResponse {
    private List<CampaignMypageResponse> campaignMypageResponse;

    @Builder
    public CampaignMyPageListResponse(Long totalElements, Integer totalPages, List<CampaignMypageResponse> campaignMypageRespons) {
        super(totalElements, totalPages);
        this.campaignMypageResponse = campaignMypageRespons;
    }
}
