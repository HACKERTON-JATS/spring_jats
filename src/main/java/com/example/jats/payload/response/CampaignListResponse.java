package com.example.jats.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CampaignListResponse extends PageResponse {
    private List<CampaignRegionResponse> campaignRegionResponses;

    @Builder
    public CampaignListResponse(Long totalElements, Integer totalPages, List<CampaignRegionResponse> campaignRegionResponses) {
        super(totalElements, totalPages);
        this.campaignRegionResponses = campaignRegionResponses;
    }
}
