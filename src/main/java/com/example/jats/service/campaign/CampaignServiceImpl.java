package com.example.jats.service.campaign;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.campaign.CampaignRepository;
import com.example.jats.entity.campaign_file.CampaignFileRepository;
import com.example.jats.entity.comment.CommentRepository;
import com.example.jats.entity.participate.Participate;
import com.example.jats.entity.participate.ParticipateRepository;
import com.example.jats.entity.good.GoodRepository;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.entity.user.enums.Region;
import com.example.jats.exceptions.AlreadyParticipateException;
import com.example.jats.exceptions.CampaignNotFoundException;
import com.example.jats.exceptions.InvalidAccessException;
import com.example.jats.exceptions.UserNotFoundException;
import com.example.jats.payload.request.CampaignRequest;
import com.example.jats.payload.response.CampaignContentResponse;
import com.example.jats.payload.response.CampaignListResponse;
import com.example.jats.payload.response.CampaignRegionResponse;
import com.example.jats.security.auth.AuthenticationFacade;
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
    private final ParticipateRepository participateRepository;
    private final CampaignFileRepository campaignFileRepository;
    private final CommentRepository commentRepository;
    private final GoodRepository goodRepository;

    @Override
    @Transactional
    public Long createCampaign(CampaignRequest request) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Campaign campaign =  campaignRepository.save(request.toEntity(user));
        participateRepository.save(
                Participate.builder()
                        .campaign(campaign)
                        .user(user)
                        .build());
        return campaign.getId();
    }

    @Override
    public CampaignListResponse getCampaignList(Pageable pageable, Region region) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Page<Campaign> campaignList = campaignRepository.
                findAllByIsAcceptedTrueAndRegionAndEndAtAfterOrderByLikeCntDesc
                        (region == null ? user.getRegion() : region, LocalDateTime.now(), pageable);

        List<CampaignRegionResponse> campaignRegionResponses = new ArrayList<>();

        for(Campaign campaign : campaignList) {

            // ?????? ????????? ???????????? ???????????? ??????
            boolean isLiked = Lists.transform(campaign.getGoods(),
                    liked -> liked.getUser()).stream().anyMatch(liker -> liker.getId().equals(user.getId()));

            campaignRegionResponses.add(
                    CampaignRegionResponse.builder()
                            .title(campaign.getTitle())
                            .createdAt(campaign.getCreatedAt())
                            .id(campaign.getId())
                            .endAt(campaign.getEndAt())
                            .isLiked(campaign.getIsAccepted())
                            .fileName(campaign.getCampaignFiles().get(0).getFileName())
                            .path(campaign.getCampaignFiles().get(0).getPath())
                            .likeCnt(campaign.getLikeCnt())
                            .isLiked(isLiked)
                            .build()
            );
        }
        return CampaignListResponse.builder()
                .totalPages(campaignList.getTotalPages())
                .totalElements(campaignList.getTotalElements())
                .campaignRegionResponses(campaignRegionResponses)
                .build();
    }

    @Override
    public void participateCampaign(Long campaignId) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(InvalidAccessException::new);

        Campaign campaign = campaignRepository.findByIdAndIsAcceptedTrueAndEndAtAfter(campaignId, LocalDateTime.now())
                .orElseThrow(CampaignNotFoundException::new);

        if (user.getParticipates().stream().anyMatch(participate -> participate.getUser().equals(user)))
            throw new AlreadyParticipateException();

        participateRepository.save(
                Participate.builder()
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

        if (!userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new).equals(campaign.getWriter())) {
            throw new InvalidAccessException();
        }

        participateRepository.deleteAllByCampaign(campaign);
        campaignFileRepository.deleteAllByCampaign(campaign);
        commentRepository.deleteAllByCampaign(campaign);
        goodRepository.deleteAllByCampaign(campaign);
        campaignRepository.delete(campaign);

        return campaignId;

    }

    @Override
    public void updateCampaign(Long campaignId, CampaignRequest request) {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(CampaignNotFoundException::new);

        if(!userRepository.findById(authenticationFacade.getUserId())
            .orElseThrow(UserNotFoundException::new).equals(campaign.getWriter())) {
            throw new InvalidAccessException();
        }

        campaignRepository.save(campaign.updateCampaign(request));
    }

    @Override
    public CampaignContentResponse getCampaign(Long campaignId) {
        if(!authenticationFacade.isLogin())
            throw new InvalidAccessException();

        Campaign campaign = campaignRepository.findByIdAndIsAcceptedTrueAndEndAtAfter(campaignId, LocalDateTime.now())
                .orElseThrow(CampaignNotFoundException::new);

        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(UserNotFoundException::new);

        return CampaignContentResponse.builder()
                .isMine(campaign.getWriter().equals(user))
                .content(campaign.getContent())
                .likeCnt(campaign.getLikeCnt())
                .createdAt(campaign.getCreatedAt())
                .endAt(campaign.getEndAt())
                .isLiked(campaign.getGoods().stream().anyMatch(good -> good.getUser().equals(user)))
                .path(Lists.transform(campaign.getCampaignFiles(), files -> files.getPath()))
                .fileName(Lists.transform(campaign.getCampaignFiles(), files -> files.getFileName()))
                .title(campaign.getTitle())
                .isAttendance(campaign.getParticipates().stream().anyMatch(participate -> participate.getUser().equals(user)))
                .build();
    }

}
