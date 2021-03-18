package com.example.jats.entity.campaign;

import com.example.jats.entity.campaign_file.CampaignFile;
import com.example.jats.entity.comment.Comment;
import com.example.jats.entity.join.Participate;
import com.example.jats.entity.good.Good;
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
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd`T`hh:mm:SS")
    @Column(nullable = false)
    private LocalDateTime endAt;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign")
    private List<Comment> comments;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign")
    private List<Good> goods;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign")
    private List<CampaignFile> campaignFiles;

    @JsonBackReference
    @OneToMany(mappedBy = "campaign")
    private List<Participate> Participates;

    public Campaign changeLikeCnt(int num) {
        this.likeCnt += num;
        return this;
    }
}
