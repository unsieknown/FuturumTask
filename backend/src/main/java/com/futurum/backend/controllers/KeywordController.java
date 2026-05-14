package com.futurum.backend.controllers;

import com.futurum.backend.dto.KeywordDto;
import com.futurum.backend.services.KeywordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keyword")
public class KeywordController {


    private final KeywordService keywordService;

    @GetMapping
    public ResponseEntity<List<KeywordDto>> getKeywords() {
        List<KeywordDto> keywords = keywordService.getKeywords();
        return ResponseEntity.ok(keywords);
    }

    @PostMapping
    public ResponseEntity<KeywordDto> addKeyword(@RequestBody @Valid KeywordDto keywordDto) {
        KeywordDto dto = keywordService.addKeyword(keywordDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{keywordId}")
    public ResponseEntity<KeywordDto> updateKeyword(
            @PathVariable Integer keywordId,
            @RequestBody @Valid KeywordDto keywordDto
    ) {
        KeywordDto dto = keywordService.updateKeyword(keywordId, keywordDto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{keywordId}")
    public ResponseEntity<Void> deleteKeyword(@PathVariable Integer keywordId) {
        keywordService.deleteKeyword(keywordId);
        return ResponseEntity.noContent().build();
    }
}
