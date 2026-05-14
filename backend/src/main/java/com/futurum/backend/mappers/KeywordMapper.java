package com.futurum.backend.mappers;

import com.futurum.backend.dto.KeywordDto;
import com.futurum.backend.model.Keyword;
import org.springframework.stereotype.Component;

@Component
public class KeywordMapper {

    public KeywordDto toDto(Keyword keyword) {
        return KeywordDto.builder()
                .id(keyword.getId())
                .keywordName(keyword.getKeywordName())
                .build();
    }
}
