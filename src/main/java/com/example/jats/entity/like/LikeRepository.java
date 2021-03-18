package com.example.jats.entity.like;

import com.example.jats.entity.campaign.Campaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long> {
    void deleteAllByCampaign(Campaign campaign);
}
