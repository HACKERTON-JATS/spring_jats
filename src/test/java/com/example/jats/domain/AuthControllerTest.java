package com.example.jats.domain;

import com.example.jats.JatsApplication;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.UserRepository;
import com.example.jats.entity.user.enums.Region;
import com.example.jats.payload.request.SignInRequest;
import com.example.jats.payload.response.TokenResponse;
import com.example.jats.security.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JatsApplication.class)
@ActiveProfiles({"test"})
public class AuthControllerTest {

    @Value("${auth.jwt.secret}")
    private String secret;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    String accessToken;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        User user = userRepository.save(
                User.builder()
                        .region(Region.KANGWON)
                        .name("hong")
                        .password(passwordEncoder.encode("pwd"))
                        .id("id")
                        .build()
        );
        accessToken = jwtTokenProvider.generateAccessToken(user.getId());
    }

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void signInTest() throws Exception {
        SignInRequest request = new SignInRequest("id","pwd");

        MvcResult result = mockMvc.perform(post("/auth")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();
        Assertions.assertEquals(jwtTokenProvider.validateToken
                (new ObjectMapper().readValue(result.getResponse().getContentAsString(), TokenResponse.class).getAccessToken()), true);

    }

    @Test
    void signInFailTest() throws Exception {
        SignInRequest request = new SignInRequest("id","pwdaa");

        mockMvc.perform(post("/auth")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}
