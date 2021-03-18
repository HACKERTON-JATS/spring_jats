package com.example.jats.service.user;

import com.example.jats.entity.join.Participate;
import com.example.jats.entity.join.ParticipateRepository;
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
    private final ParticipateRepository participateRepository;

    @Override
    public CampaignListResponse getUserCampaign(Pageable pageable) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        List<CampaignContentResponse> campaignContentResponseList = new ArrayList<>();
        Page<Participate> joins = participateRepository.findAllByUser(user, pageable);

        for(Participate participate : joins) {
            campaignContentResponseList.add(
                    CampaignContentResponse.builder()
                            .likeCnt(participate.getCampaign().getLikeCnt())
                            .path(Lists.transform(participate.getCampaign().getCampaignFiles(), list -> list.getPath()))
                            .fileName(Lists.transform(participate.getCampaign().getCampaignFiles(), list -> list.getFileName()))
                            .title(participate.getCampaign().getTitle())
                            .isLiked(participate.getCampaign().getGoods().stream().anyMatch(like -> like.getUser().equals(user)))
                            .endAt(participate.getCampaign().getEndAt())
                            .createdAt(participate.getCampaign().getCreatedAt())
                            .content(participate.getCampaign().getContent())
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
