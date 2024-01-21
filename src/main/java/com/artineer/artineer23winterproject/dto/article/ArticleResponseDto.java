package com.artineer.artineer23winterproject.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponseDto {

    private Long id;

    private String title;

    private String content;

    private String author;

    private String localDateTime;
}
