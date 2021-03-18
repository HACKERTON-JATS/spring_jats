package com.example.jats.entity.comment_file;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.comment.Comment;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String path;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
