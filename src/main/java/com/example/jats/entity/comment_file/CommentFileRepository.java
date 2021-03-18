package com.example.jats.entity.comment_file;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentFileRepository extends CrudRepository<CommentFile, Long> {
}
