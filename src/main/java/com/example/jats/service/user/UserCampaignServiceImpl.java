package com.example.jats.service.user;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.campaign.CampaignRepository;
import com.example.jats.entity.join.Join;
import com.example.jats.entity.join.JoinRepository;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.exceptions.UserNotFoundException;
import com.example.jats.payload.response.CampaignContentResponse;
import com.example.jats.payload.response.CampaignListResponse;
import com.example.jats.security.auth.AuthenticationFacade;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCampaignServiceImpl implements UserCampaignService {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final JoinRepository joinRepository;

    @Override
    public CampaignListResponse getUserCampaign(Pageable pageable) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        List<CampaignContentResponse> campaignContentResponseList = new ArrayList<>();
        Page<Join> joins = joinRepository.findAllByUser(user, pageable);

        for(Join join : joins) {

            campaignContentResponseList.add(
                    CampaignContentResponse.builder()
                            .likeCnt(join.getCampaign().getLikeCnt())
                            .path(Lists.transform(join.getCampaign().getCampaignFiles(), list -> list.getPath()))
                            .fileName(Lists.transform(join.getCampaign().getCampaignFiles(), list -> list.getFileName()))
                            .title(join.getCampaign().getTitle())
                            .isLiked(join.getCampaign().getLikes().stream().anyMatch(like -> like.getUser().equals(user)))
                            .endAt(join.getCampaign().getEndAt())
                            .createdAt(join.getCampaign().getCreatedAt())
                            .content(join.getCampaign().getContent())
                            .likeCnt(join.getCampaign().getLikeCnt())
                            .build()
            );
        }
        return CampaignListResponse.builder()
                .totalElements(joins.getTotalElements())
                .totalPages(joins.getTotalPages())
                .campaignContentResponses(campaignContentResponseList)
                .build();

    }
}
