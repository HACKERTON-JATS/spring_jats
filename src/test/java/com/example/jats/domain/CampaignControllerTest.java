package com.example.jats.domain;

import com.example.jats.JatsApplication;
import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.campaign.CampaignRepository;
import com.example.jats.entity.campaign_file.CampaignFile;
import com.example.jats.entity.campaign_file.CampaignFileRepository;
import com.example.jats.entity.participate.ParticipateRepository;
import com.example.jats.entity.good.GoodRepository;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.entity.user.enums.Region;
import com.example.jats.exceptions.CampaignNotFoundException;
import com.example.jats.payload.request.CampaignRequest;
import com.example.jats.payload.response.CampaignContentResponse;
import com.example.jats.payload.response.CampaignListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JatsApplication.class)
@ActiveProfiles({"test"})
public class CampaignControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampaignFileRepository campaignFileRepository;

    @Autowired
    private ParticipateRepository participateRepository;

    @Autowired
    private GoodRepository goodRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        User user = userRepository.save(
                User.builder()
                        .region(Region.NORTHCHUNG)
                        .name("hong")
                        .password(passwordEncoder.encode("pwd"))
                        .id("id")
                        .build()
        );
        userRepository.save(
                User.builder()
                        .region(Region.KANGWON)
                        .name("hong2")
                        .password(passwordEncoder.encode("pwd2"))
                        .id("id2")
                        .build()
        );

        campaignRepository.save(new
                CampaignRequest("must not be watched", LocalDateTime.now(), "content", Region.KANGWON).toEntity(user));
        campaignRepository.save(new
                CampaignRequest("must not be watched", LocalDateTime.now(), "content", Region.KANGWON).toEntity(user));
        campaignRepository.save(new
                CampaignRequest("must not be watched", LocalDateTime.now(), "content", Region.KYEONGKI).toEntity(user));

        createCampaignFile(createCampaign("content", "watched", true), "fileName1");
        createCampaignFile(createCampaign("content", "watched", true), "fileName2");
        createCampaignFile(createCampaign("content", "watched", true), "fileName3");
    }

    @AfterEach
    public void cleanUp() {
        campaignFileRepository.deleteAll();
        participateRepository.deleteAll();
        goodRepository.deleteAll();
        campaignRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "id",password = "pwd")
    void createCampaignTest() throws Exception {
        CampaignRequest request = new CampaignRequest("title1", LocalDateTime.now(), "content", Region.KYEONGKI);

        MvcResult result = mockMvc.perform(post("/campaign")
                .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();

        Long response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Long.class);

        Campaign campaign = campaignRepository.findById(response)
                .orElseThrow(CampaignNotFoundException::new);

        Assert.assertEquals(campaign.getTitle(), "title1");
        Assert.assertEquals(campaign.getContent(), "content");
    }

    @Test
    @WithMockUser(username = "id3",password = "pwd")
    void createCampaignTokenFail() throws Exception {
        CampaignRequest request = new CampaignRequest("title1", LocalDateTime.now(), "content", Region.KYEONGKI);

        mockMvc.perform(post("/campaign")
                .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "id",password = "pwd")
    void getCampaignTest() throws Exception {
        Campaign campaign = createCampaign("content22", "title22", true);

        MvcResult result = mockMvc.perform(get("/campaign/"+campaign.getId()))
                .andExpect(status().isOk())
                .andReturn();

        CampaignContentResponse response = new ObjectMapper().registerModule(new JavaTimeModule())
                .readValue(result.getResponse().getContentAsString(), CampaignContentResponse.class);

        Assertions.assertEquals(response.getTitle(), "title22");
        Assertions.assertEquals(response.getContent(), "content22");
    }

    @Test
    @WithMockUser(username = "id",password = "pwd")
    void getCampaignTest_NOT_FOUND() throws Exception {
        Campaign campaign = createCampaign("title22", "content22", false);

        MvcResult result = mockMvc.perform(get("/campaign/"+campaign.getId()))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "id", password = "pwd")
    void participateCampaignTest() throws Exception {
        Campaign campaign = createCampaign("watched11", "watched", true);

        mockMvc.perform(patch("/campaign/"+campaign.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(patch("/campaign/"+campaign.getId()))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "id", password = "pwd")
    void participateCampaignTest_NOTFOUND() throws Exception {
        Campaign campaign = createCampaign("watched11", "watched", false);

        mockMvc.perform(patch("/campaign/"+campaign.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "id", password = "pwd")
    void deleteCampaignTest() throws Exception {
        Campaign campaign = createCampaign("watched11", "watched", true);

        mockMvc.perform(delete("/campaign/"+campaign.getId()))
                .andExpect(status().isNoContent());

        Assertions.assertEquals(campaignRepository.findAll().size(), 6); // 7 - 1 = 6
    }

    @Test
    @WithMockUser(username = "id2", password = "pwd")
    void deleteCampaignFailTest() throws Exception {
        Campaign campaign = createCampaign("watched11", "watched", true);

        mockMvc.perform(delete("/campaign/"+campaign.getId()))
                .andExpect(status().isUnauthorized());

        Assertions.assertEquals(campaignRepository.findAll().size(), 7);
    }

    public Campaign createCampaign(String content, String title, boolean isAccepted) {
        return campaignRepository.save(Campaign.builder()
                .content(content)
                .likeCnt(0L)
                .writer(userRepository.findById("id").get())
                .region(Region.NORTHCHUNG)
                .title(title)
                .createdAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusDays(1))
                .isAccepted(isAccepted)
                .build());
    }

    public void createCampaignFile(Campaign campaign, String fileName) {
        campaignFileRepository.save(
                CampaignFile.builder()
                        .path("C://daf")
                        .fileName(fileName)
                        .campaign(campaign)
                        .build()
        );
    }
}
