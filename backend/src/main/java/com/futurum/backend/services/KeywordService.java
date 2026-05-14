package com.futurum.backend.services;

import com.futurum.backend.dto.KeywordDto;
import com.futurum.backend.mappers.KeywordMapper;
import com.futurum.backend.model.Keyword;
import com.futurum.backend.repositories.KeywordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordMapper keywordMapper;

    public List<KeywordDto> getKeywords() {
        return keywordRepository.findAll()
                .stream().map(keywordMapper::toDto)
                .toList();
    }

    @Transactional
    public KeywordDto addKeyword(KeywordDto keywordDto) {
        Keyword keyword = new Keyword();
        keyword.setKeywordName(keywordDto.getKeywordName());

        return keywordMapper.toDto(keywordRepository.save(keyword));
    }

    @Transactional
    public KeywordDto updateKeyword(
            Integer keywordId,
            KeywordDto keywordDto
    ) {

        Keyword keyword = new Keyword(
                keywordId,
                keywordDto.getKeywordName()
        );

        return keywordMapper.toDto(keywordRepository.save(keyword));
    }

    @Transactional
    public void deleteKeyword(Integer keywordId) {
        keywordRepository.deleteById(keywordId);
    }
}
