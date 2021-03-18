package com.example.jats.controller;

import com.example.jats.service.good.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class GoodController {

    private final GoodService goodService;

    @PatchMapping("/{campaignId}")
    public void createLike(@PathVariable Long campaignId) {
        goodService.createLike(campaignId);
    }
}
