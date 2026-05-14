package com.futurum.backend.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeywordDto {

    private Integer id;

    @Pattern(regexp = "[A-Za-z]{5,}")
    private String keywordName;
}
