package com.futurum.backend.services;

import com.futurum.backend.dto.TownDto;
import com.futurum.backend.mappers.TownMapper;
import com.futurum.backend.model.Town;
import com.futurum.backend.repositories.TownRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TownService {

    private final TownRepository townRepository;
    private final TownMapper townMapper;

    public Town getTownById(UUID townId) {
        return townRepository.findById(townId)
                .orElseThrow(() -> new RuntimeException("Town Not Found"));
    }

    public Optional<TownDto> getTownDtoById(UUID townId) {
        return townRepository.findById(townId)
                .map(townMapper::toDto);
    }

    public List<TownDto> getTowns() {
        return townRepository.findAll()
                .stream()
                .map(townMapper::toDto)
                .toList();
    }

    @Transactional
    public TownDto createTown(TownDto townDto) {
        Town town = townMapper.toTown(townDto);
        return townMapper.toDto(townRepository.save(town));
    }

    @Transactional
    public TownDto updateTown(UUID townId, TownDto townDto) {

        Town town = townMapper.toTown(townDto);
        town.setTownId(townId);
        return townMapper.toDto(townRepository.save(town));
    }

    @Transactional
    public void deleteTown(UUID townId) {
        townRepository.deleteById(townId);
    }
}
