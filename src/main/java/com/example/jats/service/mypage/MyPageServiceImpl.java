package com.example.jats.service.mypage;

import com.example.jats.entity.participate.Participate;
import com.example.jats.entity.participate.ParticipateRepository;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.exceptions.UserNotFoundException;
import com.example.jats.payload.request.UpdateUserRequest;
import com.example.jats.payload.response.CampaignMyPageListResponse;
import com.example.jats.payload.response.CampaignMypageResponse;
import com.example.jats.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final ParticipateRepository participateRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CampaignMyPageListResponse getUserCampaign(Pageable pageable) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        List<CampaignMypageResponse> campaignMyPageResponses = new ArrayList<>();
        Page<Participate> participates = participateRepository.findAllByUser(user, pageable);

        for(Participate participate : participates) {
            campaignMyPageResponses.add(
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
                .campaignMypageRespons(campaignMyPageResponses)
                .build();
    }

    @Override
    public void updateUser(UpdateUserRequest request) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        userRepository.save(user.updateUser(request, passwordEncoder.encode(request.getPassword())));
    }
}
