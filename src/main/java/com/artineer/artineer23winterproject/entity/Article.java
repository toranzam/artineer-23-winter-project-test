package com.artineer.artineer23winterproject.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
