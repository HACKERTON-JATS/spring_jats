package com.example.jats.service.campaign;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.campaign.CampaignRepository;
import com.example.jats.entity.campaign_file.CampaignFile;
import com.example.jats.entity.campaign_file.CampaignFileRepository;
import com.example.jats.entity.comment.CommentRepository;
import com.example.jats.entity.join.Join;
import com.example.jats.entity.join.JoinRepository;
import com.example.jats.entity.like.Like;
import com.example.jats.entity.like.LikeRepository;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.exceptions.AlreadyJoinException;
import com.example.jats.exceptions.CampaignNotFoundException;
import com.example.jats.exceptions.InvalidAccessException;
import com.example.jats.exceptions.UserNotFoundException;
import com.example.jats.payload.request.CampaignRequest;
import com.example.jats.payload.response.CampaignContentResponse;
import com.example.jats.payload.response.CampaignListResponse;
import com.example.jats.security.auth.AuthenticationFacade;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final JoinRepository joinRepository;
    private final CampaignFileRepository campaignFileRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public Long createCampaign(CampaignRequest request) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        return campaignRepository.save(request.toEntity(user)).getId();
    }

    @Override
    public CampaignListResponse getCampaignList(Pageable pageable) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Page<Campaign> campaignList = campaignRepository.
                findAllByIsAcceptedTrueAndRegionAndEndAtAfterOrderByLikeCntDesc(user.getRegion(), LocalDateTime.now(), pageable);

        List<CampaignContentResponse> campaignContentResponses = new ArrayList<>();

        for(Campaign campaign : campaignList) {

            // 해당 유저가 좋아요를 눌렀는지 확인
            boolean isLiked = Lists.transform(campaign.getLikes(),
                    like -> like.getUser()).stream().anyMatch(liker -> liker.getId().equals(user.getId()));

            campaignContentResponses.add(
                    CampaignContentResponse.builder()
                            .title(campaign.getTitle())
                            .content(campaign.getContent())
                            .createdAt(campaign.getCreatedAt())
                            .endAt(campaign.getEndAt())
                            .id(campaign.getId())
                            .isLiked(isLiked)
                            .fileName(Lists.transform(campaign.getCampaignFiles(), file -> file.getFileName()))
                            .path(Lists.transform(campaign.getCampaignFiles(), file -> file.getPath()))
                            .likeCnt(campaign.getLikeCnt())
                            .build()
            );
        }
        return CampaignListResponse.builder()
                .totalPages(campaignList.getTotalPages())
                .totalElements(campaignList.getTotalElements())
                .campaignContentResponses(campaignContentResponses)
                .build();
    }

    @Override
    public void joinCampaign(Long campaignId) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(InvalidAccessException::new);

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(CampaignNotFoundException::new);

        if (user.getJoins().stream().anyMatch(join -> join.getUser().equals(user)))
            throw new AlreadyJoinException();

        joinRepository.save(
                Join.builder()
                        .campaign(campaign)
                        .user(user)
                        .build()
        );
    }

    @Override
    @Transactional
    public Long deleteCampaign(Long campaignId) {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(CampaignNotFoundException::new);

        if (userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new).equals(campaign.getUser())) {
            throw new InvalidAccessException();
        }

        joinRepository.deleteAllByCampaign(campaign);
        campaignFileRepository.deleteAllByCampaign(campaign);
        commentRepository.deleteAllByCampaign(campaign);
        likeRepository.deleteAllByCampaign(campaign);
        campaignRepository.delete(campaign);

        return campaignId;

    }

}
