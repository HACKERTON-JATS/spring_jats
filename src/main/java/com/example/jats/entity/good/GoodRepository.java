package com.example.jats.entity.good;

import com.example.jats.entity.campaign.Campaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodRepository extends CrudRepository<Good, Long> {
    void deleteAllByCampaign(Campaign campaign);
}
