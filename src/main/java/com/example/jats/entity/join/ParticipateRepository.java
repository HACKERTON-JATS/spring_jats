package com.example.jats.entity.join;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipateRepository extends CrudRepository<Participate, Long> {
    void deleteAllByCampaign(Campaign campaign);

    Page<Participate> findAllByUser(User user, Pageable pageable);
}
