package com.futurum.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "town_id")
    private UUID townId;

    @Column(name = "town_name", nullable = false)
    private String townName;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "district", nullable = false)
    private String district;

    @ManyToMany(mappedBy = "towns", fetch = FetchType.LAZY)
    private Set<Campaign> campaigns = new HashSet<>();

    @PreRemove
    public void removeTownFromCampaigns() {
        for (Campaign campaign : campaigns)
            campaign.getTowns().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Town town = (Town) o;
        return getTownId() != null && getTownId().equals(town.getTownId());
    }

    @Override
    public int hashCode() {
        return getTownId().hashCode();
    }
}
