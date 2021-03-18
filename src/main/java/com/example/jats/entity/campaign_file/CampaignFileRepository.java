package com.example.jats.entity.campaign_file;

import com.example.jats.entity.campaign.Campaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignFileRepository extends CrudRepository<CampaignFile, Long> {
    void deleteAllByCampaign(Campaign campaign);
}
