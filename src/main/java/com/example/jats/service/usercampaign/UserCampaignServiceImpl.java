package com.example.jats.service.usercampaign;

import com.example.jats.entity.participate.Participate;
import com.example.jats.entity.participate.ParticipateRepository;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.exceptions.InvalidAccessException;
import com.example.jats.exceptions.UserNotFoundException;
import com.example.jats.payload.response.CampaignMyPageListResponse;
import com.example.jats.payload.response.CampaignMypageResponse;
import com.example.jats.security.auth.AuthenticationFacade;
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
    public CampaignMyPageListResponse getUserCampaign(Pageable pageable) {
        if(!authenticationFacade.isLogin())
            throw new InvalidAccessException();
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        List<CampaignMypageResponse> campaignMypageResponses = new ArrayList<>();
        Page<Participate> participates = participateRepository.findAllByUser(user, pageable);

        for(Participate participate : participates) {
            campaignMypageResponses.add(
                    CampaignMypageResponse.builder()
                            .likeCnt(participate.getCampaign().getLikeCnt())
                            .title(participate.getCampaign().getTitle())
                            .isLiked(participate.getCampaign().getGoods().stream().anyMatch(like -> like.getUser().equals(user)))
                            .endAt(participate.getCampaign().getEndAt())
                            .createdAt(participate.getCampaign().getCreatedAt())
                            .likeCnt(participate.getCampaign().getLikeCnt())
                            .id(participate.getCampaign().getId())
                            .build()
            );
        }

        return CampaignMyPageListResponse.builder()
                .totalElements(participates.getTotalElements())
                .totalPages(participates.getTotalPages())
                .campaignMypageRespons(campaignMypageResponses)
                .build();

    }
}
