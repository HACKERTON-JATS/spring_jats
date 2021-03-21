package com.example.jats.domain;

import com.example.jats.JatsApplication;
import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.campaign.CampaignRepository;
import com.example.jats.entity.good.Good;
import com.example.jats.entity.good.GoodRepository;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.entity.user.enums.Region;
import com.example.jats.exceptions.CampaignNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JatsApplication.class)
@ActiveProfiles({"test"})
public class GoodControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private GoodRepository goodRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        userRepository.save(
                User.builder()
                        .region(Region.KANGWON)
                        .name("hong2")
                        .password(passwordEncoder.encode("pwd2"))
                        .id("id2")
                        .build()
        );

    }

    @AfterEach
    public void cleanUp() {
        goodRepository.deleteAll();
        campaignRepository.deleteAll();
    }


    @Test
    @WithMockUser(username = "id2", password = "pwd2")
    void createGood() throws Exception {
        Campaign campaign = createCampaign("content", "title1", true);

        mockMvc.perform(patch("/like/"+campaign.getId()))
                .andExpect(status().isOk());

        Campaign campaign1 = campaignRepository.findById(campaign.getId())
                .orElseThrow(CampaignNotFoundException::new);

        Assertions.assertEquals(campaign1.getLikeCnt(), 1);
    }


    @Test
    @WithMockUser(username = "id2", password = "pwd2")
    void cancelGood() throws Exception {
        Campaign campaign = createCampaign("content", "title1", true);
        goodRepository.save(
                Good.builder()
                        .campaign(campaign)
                        .user(userRepository.findById("id2").get())
                        .build()
        );
        campaignRepository.save(campaign.changeLikeCnt(1));

        mockMvc.perform(patch("/like/"+campaign.getId()))
                .andExpect(status().isOk());

        Campaign campaign1 = campaignRepository.findById(campaign.getId())
                .orElseThrow(CampaignNotFoundException::new);

        Assertions.assertEquals(campaign1.getLikeCnt(), 0);
    }

    public Campaign createCampaign(String content, String title, boolean isAccepted) {
        return campaignRepository.save(Campaign.builder()
                .content(content)
                .likeCnt(0L)
                .writer(userRepository.findById("id2").get())
                .region(Region.NORTHCHUNG)
                .title(title)
                .createdAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusDays(1))
                .isAccepted(isAccepted)
                .build());
    }

}
