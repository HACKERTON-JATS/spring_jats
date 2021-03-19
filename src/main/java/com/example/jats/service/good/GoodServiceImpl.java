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


        int changeNum = campaign.getGoods().stream().filter(good -> good.getUser().equals(user))
                .findFirst()
                .map(good -> {
                    goodRepository.delete(good);    // 이미 존재하면 지움
                    return -1;                      // -1만큼 바꿈
                })
                .orElseGet(() -> {                  // null이라면
                    goodRepository.save(            // good 테이블에 저장
                            Good.builder()
                                    .user(user)
                                    .campaign(campaign)
                                    .build());
                    return 1;
                    }
                );

        campaignRepository.save(campaign.changeLikeCnt(changeNum));

    }
}
