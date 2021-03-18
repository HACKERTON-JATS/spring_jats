package com.example.jats.service.good;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.campaign.CampaignRepository;
import com.example.jats.entity.good.Good;
import com.example.jats.entity.good.GoodRepository;
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
public class GoodServiceImpl implements GoodService {

    private final GoodRepository goodRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CampaignRepository campaignRepository;

    @Override
    @Transactional
    public void createLike(Long campaignId) {
        if(!authenticationFacade.isLogin())
            throw new InvalidAccessException();
        User user = userRepository.findById(authenticationFacade.getUserId())
                .orElseThrow(InvalidAccessException::new);

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(CampaignNotFoundException::new);

        int changeNum = 1;
        if (!campaign.getGoods().stream().anyMatch(good -> good.getUser().equals(user))) {
            goodRepository.save(
                Good.builder()
                        .campaign(campaign)
                        .user(user)
                        .build()
            );
        }else {
            changeNum = -1;
            goodRepository.delete(campaign.getGoods().stream().filter(good -> good.getUser().equals(user)).
                    findFirst().orElseThrow(LikeNotFoundException::new));
        }
        campaignRepository.save(campaign.changeLikeCnt(changeNum));

    }
}