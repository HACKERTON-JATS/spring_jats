package com.example.jats.payload.request;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignRequest {

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd`T`hh:mm:SS")
    private LocalDateTime endAt;

    private String content;

    private Region region;

    public Campaign toEntity(User user) {
        return Campaign.builder()
                .region(region)
                .content(content)
                .createdAt(LocalDateTime.now())
                .isAccepted(false)
                .title(title)
                .user(user)
                .endAt(endAt)
                .likeCnt(0L)
                .build();
    }
}
