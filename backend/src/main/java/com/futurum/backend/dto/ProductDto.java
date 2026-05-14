package com.futurum.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private UUID productId;

    @NotBlank
    @Size(min = 5)
    private String productName;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @Min(1)
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
