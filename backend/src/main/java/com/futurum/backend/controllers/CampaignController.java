package com.futurum.backend.controllers;

import com.futurum.backend.dto.CampaignDto;
import com.futurum.backend.services.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping
    public ResponseEntity<List<CampaignDto>> getCampaigns() {
        List<CampaignDto> campaigns = campaignService.getCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<CampaignDto> getCampaignById(@PathVariable UUID campaignId) {
        return campaignService.getCampaignDtoById(campaignId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CampaignDto> createCampaign(@RequestBody CampaignDto campaignDto) {
        CampaignDto campaign = campaignService.createCampaign(campaignDto);
        return new ResponseEntity<>(campaign, HttpStatus.CREATED);
    }

    @PatchMapping("/{campaignId}/add/product/{productId}")
    public ResponseEntity<CampaignDto> addProductToCampaign(@PathVariable UUID campaignId, @PathVariable UUID productId) {
        CampaignDto campaign = campaignService.addProductToCampaign(campaignId, productId);
        return ResponseEntity.ok(campaign);
    }

    @PatchMapping("/{campaignId}/remove/product/{productId}")
    public ResponseEntity<CampaignDto> removeProductFromCampaign(@PathVariable UUID campaignId, @PathVariable UUID productId) {
        CampaignDto campaign = campaignService.removeProductFromCampaign(campaignId, productId);
        return ResponseEntity.ok(campaign);
    }

    @PatchMapping("/{campaignId}/add/keyword/{keywordId}")
    public ResponseEntity<CampaignDto> addKeywordToCampaign(@PathVariable UUID campaignId, @PathVariable Integer keywordId) {
        CampaignDto campaign = campaignService.addKeywordToCampaign(campaignId, keywordId);
        return ResponseEntity.ok(campaign);
    }

    @PatchMapping("/{campaignId}/remove/keyword/{keywordId}")
    public ResponseEntity<CampaignDto> removeKeywordFromCampaign(@PathVariable UUID campaignId, @PathVariable Integer keywordId) {
        CampaignDto campaign = campaignService.removeKeywordFromCampaign(campaignId, keywordId);
        return ResponseEntity.ok(campaign);
    }

    @PatchMapping("/{campaignId}/add/town/{townId}")
    public ResponseEntity<CampaignDto> addTownToCampaign(@PathVariable UUID campaignId, @PathVariable UUID townId) {
        CampaignDto campaign = campaignService.addTownToCampaign(campaignId, townId);
        return ResponseEntity.ok(campaign);
    }

    @PatchMapping("/{campaignId}/remove/town/{townId}")
    public ResponseEntity<CampaignDto> removeTownFromCampaign(@PathVariable UUID campaignId, @PathVariable UUID townId) {
        CampaignDto campaign = campaignService.removeTownFromCampaign(campaignId, townId);
        return ResponseEntity.ok(campaign);
    }

    @PutMapping("/{campaignId}")
    public ResponseEntity<CampaignDto> updateCampaign(@PathVariable UUID campaignId, @RequestBody CampaignDto campaignDto) {
        CampaignDto campaign = campaignService.updateCampaign(campaignId, campaignDto);
        return ResponseEntity.ok(campaign);
    }

    @PatchMapping("/{campaignId}/finances")
    public ResponseEntity<CampaignDto> updateFinances(
            @PathVariable UUID campaignId,
            @RequestParam BigDecimal bid,
            @RequestParam BigDecimal fund) {
        CampaignDto campaign = campaignService.updateCampaignFinances(campaignId, bid, fund);
        return ResponseEntity.ok(campaign);
    }

    @DeleteMapping("/{campaignId}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable UUID campaignId) {
        campaignService.deleteCampaign(campaignId);
        return ResponseEntity.noContent().build();
    }
}
