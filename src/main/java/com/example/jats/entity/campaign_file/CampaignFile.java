package com.example.jats.entity.campaign_file;

import com.example.jats.entity.campaign.Campaign;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign_file_tbl")
public class CampaignFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String path;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
}
