package com.futurum.backend.dto;

import com.futurum.backend.model.CampaignStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDto {

    private UUID campaignId;
    private String campaignName;
    private List<TownDto> towns;
    private List<ProductDto> products;
    private List<KeywordDto> keywords;
    private BigDecimal bidAmount;
    private BigDecimal campaignFund;
    private CampaignStatus status;
    private Integer radius;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
