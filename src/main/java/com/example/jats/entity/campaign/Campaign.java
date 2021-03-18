package com.example.jats.entity.campaign;

import com.example.jats.entity.campaign_file.CampaignFile;
import com.example.jats.entity.comment_file.CommentFile;
import com.example.jats.entity.comment.Comment;
import com.example.jats.entity.join.Join;
import com.example.jats.entity.like.Like;
import com.example.jats.entity.user.User;
import com.example.jats.entity.user.enums.Region;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign_tbl")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isAccepted;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(nullable = false)
    private Long likeCnt;

    @DateTimeFormat(pattern = "yyyy-MM-dd`T`hh:mm:SS")
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd`T`hh:mm:SS")
    private LocalDateTime endAt;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign")
    private List<Comment> comments;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign")
    private List<Like> likes;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign")
    private List<CampaignFile> campaignFiles;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign")
    private List<Join> joins;

    public Campaign changeLikeCnt(int num) {
        this.likeCnt += num;
        return this;
    }
}
