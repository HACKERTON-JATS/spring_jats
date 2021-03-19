package com.example.jats.payload.response;

import com.example.jats.entity.user.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponse {
    private Region region;
}
