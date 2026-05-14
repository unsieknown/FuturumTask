package com.futurum.backend.mappers;

import com.futurum.backend.dto.TownDto;
import com.futurum.backend.model.Town;
import org.springframework.stereotype.Component;

@Component
public class TownMapper {

    public Town toTown(TownDto townDto) {
        return Town.builder()
                .townName(townDto.getTownName())
                .zipCode(townDto.getZipCode())
                .district(townDto.getDistrict())
                .build();
    }

    public TownDto toDto(Town town) {
        return TownDto.builder()
                .townId(town.getTownId())
                .townName(town.getTownName())
                .zipCode(town.getZipCode())
                .district(town.getDistrict())
                .build();
    }
}
