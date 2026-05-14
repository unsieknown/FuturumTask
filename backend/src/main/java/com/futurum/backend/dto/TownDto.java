package com.futurum.backend.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TownDto {

    private UUID townId;
    private String townName;
    private String zipCode;
    private String district;
}
