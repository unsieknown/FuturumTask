package com.futurum.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID campaignId;

    private String campaignName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "campaigns_towns",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "town_id")
    )
    private Set<Town> towns = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "campaigns_products",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "campaigns_keywords",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<Keyword> keywords = new HashSet<>();

    @NotNull
    @Min(value = 1)
    private BigDecimal bidAmount;

    @NotNull
    @Min(value = 1)
    private BigDecimal campaignFund;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CampaignStatus status = CampaignStatus.OFF;

    private Integer radius;

    private boolean archived = false;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void addKeyword(Keyword keyword) {
        this.keywords.add(keyword);
    }

    public void removeKeyword(Keyword keyword) {
        this.keywords.remove(keyword);
    }

    public void addTown(Town town) {
        this.towns.add(town);
    }

    public void removeTown(Town town) {
        this.towns.remove(town);
    }
}
