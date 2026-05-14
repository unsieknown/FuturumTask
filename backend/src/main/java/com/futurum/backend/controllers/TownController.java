package com.futurum.backend.controllers;

import com.futurum.backend.dto.TownDto;
import com.futurum.backend.services.TownService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/town")
public class TownController {

    private final TownService townService;

    @GetMapping
    public ResponseEntity<List<TownDto>> getTowns() {
        List<TownDto> towns = townService.getTowns();
        return ResponseEntity.ok(towns);
    }

    @GetMapping("/{townId}")
    public ResponseEntity<TownDto> getTownById(@PathVariable UUID townId) {
        return townService.getTownDtoById(townId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TownDto> createTown(@RequestBody @Valid TownDto townDto) {
        TownDto town = townService.createTown(townDto);
        return new ResponseEntity<>(town, HttpStatus.CREATED);
    }

    @PutMapping("/{townId}")
    public ResponseEntity<TownDto> updateTown(@PathVariable UUID townId, @RequestBody @Valid TownDto townDto) {
        TownDto town = townService.updateTown(townId, townDto);
        return ResponseEntity.ok(town);
    }

    @DeleteMapping("/{townId}")
    public ResponseEntity<Void> deleteTown(@PathVariable UUID townId) {
        townService.deleteTown(townId);
        return ResponseEntity.noContent().build();
    }
}
