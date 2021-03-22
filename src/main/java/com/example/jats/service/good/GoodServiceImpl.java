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
import java.time.LocalDateTime;

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

        Campaign campaign = campaignRepository.findByIdAndIsAcceptedTrueAndEndAtAfter(campaignId, LocalDateTime.now())
                .orElseThrow(CampaignNotFoundException::new);


        int changeNum = campaign.getGoods().stream().filter(good -> good.getUser().equals(user))
                .findFirst()
                .map(Good::getId)
                    .map(good -> {
                        goodRepository.deleteById(good);
                        return -1;
                    })
                .orElseGet(() -> {
                    goodRepository.save(
                            Good.builder()
                                    .user(user)
                                    .campaign(campaign)
                                    .build());
                    return 1;
                });

        campaignRepository.save(campaign.changeLikeCnt(changeNum));

    }
}
