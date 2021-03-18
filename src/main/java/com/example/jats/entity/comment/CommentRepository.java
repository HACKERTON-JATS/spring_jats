package com.example.jats.entity.comment;

import com.example.jats.entity.campaign.Campaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    void deleteAllByCampaign(Campaign campaign);
}
