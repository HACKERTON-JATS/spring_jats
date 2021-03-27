package com.example.jats.exceptions;

import com.example.jats.error.exception.BusinessException;
import com.example.jats.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Campaign Not Found Exception")
public class CampaignNotFoundException extends BusinessException {
    public CampaignNotFoundException() {
        super(ErrorCode.CAMPAIGN_NOT_FOUND);
    }
}
