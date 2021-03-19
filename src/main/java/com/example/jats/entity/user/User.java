package com.example.jats.entity.user;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.comment.Comment;
import com.example.jats.entity.participate.Participate;
import com.example.jats.entity.good.Good;
import com.example.jats.entity.user.enums.Region;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Region region;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Good> goods;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Campaign> campaigns;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Participate> Participates;

}
