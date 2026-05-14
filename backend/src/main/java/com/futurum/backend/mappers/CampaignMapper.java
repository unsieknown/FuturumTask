package com.futurum.backend.mappers;

import com.futurum.backend.dto.CampaignDto;
import com.futurum.backend.model.Campaign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CampaignMapper {

    private final ProductMapper productMapper;
    private final KeywordMapper keywordMapper;
    private final TownMapper townMapper;

    public Campaign toCampaign(CampaignDto campaignDto) {
        return Campaign.builder()
                .campaignName(campaignDto.getCampaignName())
                .bidAmount(campaignDto.getBidAmount())
                .status(campaignDto.getStatus())
                .build();
    }

    public CampaignDto toDto(Campaign campaign) {

        return CampaignDto.builder().campaignId(campaign.getCampaignId())
                .campaignName(campaign.getCampaignName())
                .towns(campaign.getTowns().stream().map(townMapper::toDto).toList())
                .products(campaign.getProducts().stream().map(productMapper::toDto).toList())
                .keywords(campaign.getKeywords().stream().map(keywordMapper::toDto).toList())
                .bidAmount(campaign.getBidAmount())
                .campaignFund(campaign.getCampaignFund())
                .status(campaign.getStatus())
                .createdDate(campaign.getCreatedDate())
                .updatedDate(campaign.getUpdatedDate())
                .build();
    }
}
