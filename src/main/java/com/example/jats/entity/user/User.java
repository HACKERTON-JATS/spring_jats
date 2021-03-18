package com.example.jats.entity.user;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.comment.Comment;
import com.example.jats.entity.join.Join;
import com.example.jats.entity.like.Like;
import com.example.jats.entity.user.enums.Region;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String password;

    private String name;

    private Region region;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Like> likes;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Campaign> campaigns;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Join> joins;

}
