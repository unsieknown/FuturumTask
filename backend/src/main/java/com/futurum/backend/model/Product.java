package com.futurum.backend.model;

import jakarta.persistence.*;
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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    private String productName;

    @Column(name = "price", precision = 6, scale = 2)
    private BigDecimal price;

    private Integer quantity;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Set<Campaign> campaigns = new HashSet<>();

    @PreRemove
    public void removeProductFromCampaigns() {
        for (Campaign campaign : campaigns)
            campaign.getProducts().remove(this);
    }
}
