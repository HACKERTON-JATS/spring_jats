package com.example.jats.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CampaignListResponse extends PageResponse {
    private List<CampaignContentResponse> campaignContentResponses;

    @Builder
    public CampaignListResponse(Long totalElements, Integer totalPages, List<CampaignContentResponse> campaignContentResponses) {
        super(totalElements, totalPages);
        this.campaignContentResponses = campaignContentResponses;
    }
}
