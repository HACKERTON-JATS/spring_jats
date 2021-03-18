package com.example.jats.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CampaignListResponse extends PageResponse {
    private List<CampaignContentResponse> campaignContentResponses;

    @Builder
    public CampaignListResponse(Long totalElements, Integer totalPages, List<CampaignContentResponse> campaignContentResponses) {
        super(totalElements, totalPages);
        this.campaignContentResponses = campaignContentResponses;
    }
}
