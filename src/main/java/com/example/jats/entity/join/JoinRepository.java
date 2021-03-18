package com.example.jats.entity.join;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinRepository extends CrudRepository<Join, Long> {
    void deleteAllByCampaign(Campaign campaign);

    Page<Join> findAllByUser(User user, Pageable pageable);
}
