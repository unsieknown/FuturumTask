package com.futurum.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String keywordName;

    @ManyToMany(mappedBy = "keywords", fetch = FetchType.LAZY)
    private Set<Campaign> campaigns = new HashSet<>();

    public Keyword(Integer id, String keywordName) {
        this.id = id;
        this.keywordName = keywordName;
    }

    @PreRemove
    public void removeKeywordFromCampaigns() {
        for (Campaign campaign : campaigns)
            campaign.getKeywords().remove(this);
    }
}
