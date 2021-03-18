package com.example.jats.entity.campaign;

import com.example.jats.entity.user.enums.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CampaignRepository extends CrudRepository<Campaign, Long> {

    Page<Campaign> findAllByIsAcceptedTrueAndRegionAndEndAtAfterOrderByLikeCntDesc(Region region, LocalDateTime endAt, Pageable pageable);
}
