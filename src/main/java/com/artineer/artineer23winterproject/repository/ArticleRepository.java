package com.artineer.artineer23winterproject.repository;

import com.artineer.artineer23winterproject.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
