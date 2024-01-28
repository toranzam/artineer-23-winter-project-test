package com.artineer.artineer23winterproject.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account manager;

    @ManyToMany
    private List<Account> members;

    private String title;

    private String shortDesc;

    @Lob
    private String fullDesc;

    private LocalDateTime localDateTime;

    private boolean published;

    public void openStudy() {
        this.published = true;
    }

    public void closeStudy() {
        this.published = false;
    }



}
