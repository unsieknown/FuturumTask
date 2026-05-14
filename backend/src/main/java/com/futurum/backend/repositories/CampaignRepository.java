package com.futurum.backend.repositories;

import com.futurum.backend.dto.CampaignDto;
import com.futurum.backend.model.Campaign;
import com.futurum.backend.model.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    @Modifying
    @Query("""
            update Campaign c
            set c.campaignName = :campaignName,
                c.status = :staus,
                c.radius = :radius,
                c.updatedDate = CURRENT TIMESTAMP
            where c.campaignId = :campaignId
            """)
    void updateCampaign(UUID campaignId, String campaignName, Integer radius, CampaignStatus status);

    @Modifying
    @Query("""
            update Campaign c
            set c.archived = true,
                c.updatedDate = CURRENT TIMESTAMP
            where c.campaignId = :campaignId
            """)
    void archiveCampaign(UUID campaignId);

    Optional<Campaign> findCampaignByCampaignIdAndArchivedFalse(UUID campaignId);

    Optional<Campaign> findCampaignsByArchivedFalse();

    boolean existsCampaignByCampaignIdAndArchivedFalse(UUID campaignId);
}
