package com.example.jats.controller;

import com.example.jats.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PatchMapping("/{campaignId}")
    public void createLike(@PathVariable Long campaignId) {
        likeService.createLike(campaignId);
    }
}
