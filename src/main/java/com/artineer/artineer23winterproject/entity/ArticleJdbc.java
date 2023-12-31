package com.artineer.artineer23winterproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class ArticleJdbc {

    private Long id;

    private String title;

    private String content;

    private String author;

    private LocalDateTime localDateTime;
}
