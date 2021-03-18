package com.example.jats.service.like;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.campaign.CampaignRepository;
import com.example.jats.entity.like.Like;
import com.example.jats.entity.like.LikeRepository;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.exceptions.CampaignNotFoundException;
import com.example.jats.exceptions.InvalidAccessException;
import com.example.jats.exceptions.LikeNotFoundException;
import com.example.jats.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CampaignRepository campaignRepository;

    @Override
    @Transactional
    public void createLike(Long campaignId) {
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(InvalidAccessException::new);

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(CampaignNotFoundException::new);

        int changeNum = 1;
        Like like = campaign.getLikes().stream().filter(filter->filter.getUser().equals(user))
                .findFirst().orElseThrow(LikeNotFoundException::new);

        if (like.getUser().equals(user)) {
            likeRepository.save(
                    Like.builder()
                            .campaign(campaign)
                            .user(user)
                            .build());
        }else {
            changeNum = -1;
            likeRepository.delete(like);
        }
        campaignRepository.save(campaign.changeLikeCnt(changeNum));

    }
}
