package com.example.jats.entity.campaign;

import com.example.jats.entity.user.enums.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Page<Campaign> findAllByIsAcceptedTrueAndRegionAndEndAtAfterOrderByLikeCntDesc(Region region, LocalDateTime endAt, Pageable pageable);

    Optional<Campaign> findByIdAndIsAcceptedTrueAndEndAtAfter(Long id, LocalDateTime endAt);
}
