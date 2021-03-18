package com.example.jats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Campaign Not Found Exception")
public class CampaignNotFoundException extends RuntimeException{
    public CampaignNotFoundException() {
        super("CAMPAIGN_NOT_FOUND");
    }
}
