package com.futurum.backend.services;

import com.futurum.backend.dto.CampaignDto;
import com.futurum.backend.mappers.CampaignMapper;
import com.futurum.backend.model.*;
import com.futurum.backend.repositories.CampaignRepository;
import com.futurum.backend.repositories.EmeraldAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    private final ProductService productService;
    private final KeywordService keywordService;
    private final TownService townService;
    private final EmeraldAccountRepository emeraldAccountRepository;

    private Campaign getCampaignById(UUID campaignId) {
        return campaignRepository.findCampaignByCampaignIdAndArchivedFalse(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign Not Found"));
    }

    public List<CampaignDto> getCampaigns() {
        return campaignRepository.findCampaignsByArchivedFalse()
                .stream()
                .map(campaignMapper::toDto)
                .toList();
    }

    public Optional<CampaignDto> getCampaignDtoById(UUID campaignId) {
        return campaignRepository.findCampaignByCampaignIdAndArchivedFalse(campaignId)
                .map(campaignMapper::toDto);
    }

    @Transactional
    public CampaignDto createCampaign(CampaignDto campaignDto) {

        EmeraldAccount emeraldAccount = emeraldAccountRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Emerald Account Not Found"));

        if (emeraldAccount.getBalance().compareTo(campaignDto.getCampaignFund()) < 0)
            throw new RuntimeException("Insufficient Funds");

        if (campaignDto.getBidAmount().compareTo(campaignDto.getCampaignFund()) > 0)
            throw new RuntimeException("Bid amount cannot be greater than campaign fund");

        emeraldAccount.setBalance(emeraldAccount.getBalance().subtract(campaignDto.getCampaignFund()));
        emeraldAccountRepository.save(emeraldAccount);

        Campaign campaign = campaignMapper.toCampaign(campaignDto);
        return campaignMapper.toDto(campaignRepository.saveAndFlush(campaign));
    }

    @Transactional
    public CampaignDto addProductToCampaign(UUID campaignId, UUID productId) {

        Product product = productService.getProductById(productId);
        Campaign campaign = getCampaignById(campaignId);

        if (!campaign.getProducts().contains(product)) {
            campaign.addProduct(product);
            campaign = campaignRepository.saveAndFlush(campaign);
        }

        return campaignMapper.toDto(campaign);
    }

    @Transactional
    public CampaignDto removeProductFromCampaign(UUID campaignId, UUID productId) {

        Product product = productService.getProductById(productId);
        Campaign campaign = getCampaignById(campaignId);

        if (campaign.getProducts().contains(product)) {
            campaign.removeProduct(product);
            campaign = campaignRepository.saveAndFlush(campaign);
        }

        return campaignMapper.toDto(campaign);
    }

    @Transactional
    public CampaignDto addKeywordToCampaign(UUID campaignId, Integer keywordId) {

        Keyword keyword = keywordService.getKeywordById(keywordId);
        Campaign campaign = getCampaignById(campaignId);

        if (!campaign.getKeywords().contains(keyword)) {
            campaign.addKeyword(keyword);
            campaign = campaignRepository.saveAndFlush(campaign);
        }

        return campaignMapper.toDto(campaign);
    }

    @Transactional
    public CampaignDto removeKeywordFromCampaign(UUID campaignId, Integer keywordId) {

        Keyword keyword = keywordService.getKeywordById(keywordId);
        Campaign campaign = getCampaignById(campaignId);

        if (campaign.getKeywords().contains(keyword)) {
            campaign.removeKeyword(keyword);
            campaign = campaignRepository.saveAndFlush(campaign);
        }

        return campaignMapper.toDto(campaign);
    }

    @Transactional
    public CampaignDto addTownToCampaign(UUID campaignId, UUID townId) {

        Town town = townService.getTownById(townId);
        Campaign campaign = getCampaignById(campaignId);

        if (!campaign.getTowns().contains(town)) {
            campaign.addTown(town);
            campaign = campaignRepository.saveAndFlush(campaign);
        }

        return campaignMapper.toDto(campaign);
    }

    @Transactional
    public CampaignDto removeTownFromCampaign(UUID campaignId, UUID townId) {

        Town town = townService.getTownById(townId);
        Campaign campaign = getCampaignById(campaignId);

        if (campaign.getTowns().contains(town)) {
            campaign.removeTown(town);
            campaign = campaignRepository.saveAndFlush(campaign);
        }

        return campaignMapper.toDto(campaign);
    }

    @Transactional
    public CampaignDto updateCampaign(UUID campaignId, CampaignDto campaignDto) {

        if (!campaignRepository.existsCampaignByCampaignIdAndArchivedFalse((campaignId)))
            throw new RuntimeException("Campaign Not Found Or Archived");

        campaignRepository.updateCampaign(
                campaignId,
                campaignDto.getCampaignName(),
                campaignDto.getRadius(),
                campaignDto.getStatus()
        );
        return campaignMapper.toDto(getCampaignById(campaignId));
    }

    @Transactional
    public CampaignDto updateCampaignFinances(UUID campaignId, BigDecimal newBid, BigDecimal newFund) {

        Campaign campaign = getCampaignById(campaignId);
        EmeraldAccount account = emeraldAccountRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Emerald Account Not Found"));

        if (newBid.compareTo(newFund) > 0)
            throw new RuntimeException("Bid amount cannot be grater than campaign fund");

        BigDecimal difference = newFund.subtract(campaign.getCampaignFund());

        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            if (account.getBalance().compareTo(difference) < 0)
                throw new RuntimeException("Insufficient Funds to increase campaign budget");
            account.setBalance(account.getBalance().subtract(difference));
        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
            account.setBalance(account.getBalance().add(difference.abs()));
        }

        campaign.setBidAmount(newBid);
        campaign.setCampaignFund(newFund);

        emeraldAccountRepository.save(account);
        return campaignMapper.toDto(campaignRepository.saveAndFlush(campaign));
    }

    @Transactional
    public void deleteCampaign(UUID campaignId) {

        Campaign campaign = getCampaignById(campaignId);
        EmeraldAccount account = emeraldAccountRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Emerald Account Not Found"));

        account.setBalance(account.getBalance().add(campaign.getCampaignFund()));
        emeraldAccountRepository.save(account);

        campaignRepository.archiveCampaign(campaignId);
    }
}
