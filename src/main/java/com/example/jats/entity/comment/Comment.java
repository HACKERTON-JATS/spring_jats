package com.example.jats.entity.comment;

import com.example.jats.entity.campaign.Campaign;
import com.example.jats.entity.comment_file.CommentFile;
import com.example.jats.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment_tbl")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd`T`hh:mm:SS")
    private LocalDateTime createdAt;

    @JsonManagedReference
    @JoinColumn(name = "campaign_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Campaign campaign;

    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonBackReference
    @OneToOne(mappedBy = "comment")
    private CommentFile commentFile;

}
