package com.example.jats.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CampaignBasicListResponse extends PageResponse {
    private List<CampaignBasicResponse> campaignBasicResponses;

    @Builder
    public CampaignBasicListResponse(Long totalElements, Integer totalPages, List<CampaignBasicResponse> campaignBasicResponses) {
        super(totalElements, totalPages);
        this.campaignBasicResponses = campaignBasicResponses;
    }
}
